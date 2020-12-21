package com.example.dota_match_tracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.dota_match_tracker.R
import com.example.dota_match_tracker.databinding.ActivityFailBinding

class FailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityFailBinding>(this, R.layout.activity_fail)
        binding.buttonTryAgain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}