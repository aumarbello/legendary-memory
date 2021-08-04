package com.aumarbello.showcase.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aumarbello.showcase.databinding.ActivityCarListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarListBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}