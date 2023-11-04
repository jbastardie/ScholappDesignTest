package com.example.scholappdesigntest

import android.R.attr.animation
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.scholappdesigntest.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        createClouds()
    }


    private fun createCloud(): ImageView {
        return ImageView(baseContext).apply {
            setImageDrawable(getDrawable(R.drawable.clouds_weather_svgrepo_com))
            layoutParams = LinearLayout.LayoutParams(
                Random.nextInt(100, 250),
                Random.nextInt(100, 250)
            )
            alpha = Random.nextDouble(0.4, 0.8).toFloat()
            translationX = Random.nextDouble(200.0, 600.0).toFloat()
        }
    }
    private fun animateCloud(cloud: ImageView) {
        ObjectAnimator.ofFloat(cloud, "translationX", -1000f).apply {
            duration = Random.nextLong(50000, 70000)
            repeatCount = ValueAnimator.INFINITE
            interpolator= LinearInterpolator()
            start()
        }
    }

    private fun createClouds() {
        for (x in 0 until 10) {
            val cloud = createCloud()
            val set = ConstraintSet()
            val parentLayout = findViewById<ConstraintLayout>(R.id.container)
            cloud.id = View.generateViewId()
            parentLayout.addView(cloud, 0)
            set.clone(parentLayout)
            set.connect(cloud.id, ConstraintSet.TOP, parentLayout.id, ConstraintSet.TOP, 0)
            set.connect(cloud.id, ConstraintSet.END, parentLayout.id, ConstraintSet.END, 0)
            set.connect(cloud.id, ConstraintSet.BOTTOM, parentLayout.id, ConstraintSet.BOTTOM, 0)
            set.setVerticalBias(cloud.id, Random.nextDouble(0.0, 1.0).toFloat())
            set.applyTo(parentLayout)
            animateCloud(cloud)
        }
    }
}