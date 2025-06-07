package com.artmcar.control

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.artmcar.control.databinding.ActivityFullscreenBinding

class FullscreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}