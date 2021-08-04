package com.aumarbello.showcase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aumarbello.showcase.databinding.ActivityCarDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarDetailsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityCarDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}