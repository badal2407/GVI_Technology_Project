package com.das_develop.gvi_technology_project

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.das_develop.gvi_technology_project.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.openProgressBar.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            // Start DashboardActivity
            val intent = Intent(this, StartingScreen::class.java)
            startActivity(intent)
            binding.openProgressBar.visibility = View.GONE
            // Finish SplashActivity so that it can't be returned to
            finish()
        }, 3000) // 3000 milliseconds = 3 seconds
    }
}