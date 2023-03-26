package com.example.userbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.userbrowser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.vpFollowersFollowing.adapter = SectionPagerAdapter(this)

        observeViewModel()

        supportActionBar?.elevation = 0f
    }

    private fun observeViewModel() {
        val username = intent.getStringExtra("username")

        viewModel.apply {
            getUserData(username)
            isLoading.observe(this@DetailActivity) {
                setProgressBar(it)
            }
            viewModel.userDetail.observe(this@DetailActivity) {
                loadUserDetail(it!!)
            }
        }
    }

    private fun loadUserDetail(userDetail: ResponseDetail) {
        Glide.with(this)
            .load(userDetail.avatarUrl)
            .placeholder(R.color.github_white)
            .into(binding.civUserDetailAvatar)

        binding.apply {
            tvDetailUsername.text = userDetail.login
            tvDetailFullName.text = userDetail.name
            tvDetailPublicRepos.text =
                StringBuilder(getString(R.string.repos))
                    .append(" ")
                    .append(userDetail.publicRepos.toString())
        }

        val follsCount = intArrayOf(
            userDetail.followers ?: 0,
            userDetail.following ?: 0,
        )

        TabLayoutMediator(
            binding.tabFollowersFollowing,
            binding.vpFollowersFollowing
        ) { tab, position ->
            tab.text = "${resources.getString(TAB_TITLES[position])} (${follsCount[position]})"
        }.attach()
    }

    private fun setProgressBar(loading: Boolean?) {
        if (loading == true) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                civUserDetailAvatar.visibility = View.VISIBLE
                tvDetailUsername.visibility = View.VISIBLE
                tvDetailFullName.visibility = View.VISIBLE
                tvDetailPublicRepos.visibility = View.VISIBLE
            }
        }
    }
}