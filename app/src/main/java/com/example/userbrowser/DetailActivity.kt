package com.example.userbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.userbrowser.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        observeViewModel()

    }

    private fun observeViewModel() {
        val username = intent.getStringExtra("username")
        viewModel.getUserDetail(username)
        viewModel.isLoading.observe(this) {
            setProgressBar(it)
        }
        viewModel.userDetail.observe(this) {
            loadUserDetail(it!!)
        }
    }

    private fun loadUserDetail(userDetail: ResponseDetail) {
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .placeholder(R.color.github_white)
            .into(binding.civUserDetailAvatar)

        binding.tvDetailUsername.text = userDetail.login
        binding.tvDetailFullName.text = userDetail.name
        binding.tvDetailPublicRepos.text =
            StringBuilder(getString(R.string.repos))
                .append(" ")
                .append(userDetail.publicRepos.toString())
    }

    private fun setProgressBar(loading: Boolean?) {
        if (loading == true) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.civUserDetailAvatar.visibility = View.VISIBLE
            binding.tvDetailUsername.visibility = View.VISIBLE
            binding.tvDetailFullName.visibility = View.VISIBLE
            binding.tvDetailPublicRepos.visibility = View.VISIBLE
        }
    }
}