package com.example.userbrowser.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.userbrowser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}