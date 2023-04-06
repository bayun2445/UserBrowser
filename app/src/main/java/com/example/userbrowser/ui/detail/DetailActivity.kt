package com.example.userbrowser.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.example.userbrowser.R
import com.example.userbrowser.api.ResponseDetail
import com.example.userbrowser.database.User
import com.example.userbrowser.databinding.ActivityDetailBinding
import com.example.userbrowser.helper.ViewModelFactory
import com.example.userbrowser.ui.setting.SettingActivity
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentUser: User
    private var isFavoriteDetail = false

    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpFollowersFollowing.adapter = SectionPagerAdapter(this)

        observeViewModel()
        setFabFavorite()
    }

    private fun setFabFavorite() {
        binding.fabFavorite.apply {
            viewModel.setFavorite(isFavoriteDetail)

            setOnClickListener {
                isFavoriteDetail = !isFavoriteDetail
                viewModel.setFavorite(isFavoriteDetail)

                if (isFavoriteDetail) {
                    viewModel.addToFavorite(currentUser)
                } else {
                    viewModel.removeFromFavorite(currentUser)
                }
            }
        }
    }

    private fun setFabFavoriteIcon(isFav: Boolean?) {
        binding.fabFavorite.apply {
            if (isFav!!) {
                setImageResource(R.drawable.ic_favorite)
            } else {
                setImageResource(R.drawable.ic_favorite_border)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val actionFavorite = menu?.findItem(R.id.action_favorite)
        actionFavorite?.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_setting) {
            Intent(this@DetailActivity, SettingActivity::class.java).also {
                startActivity(it)
            }
        }

        return true
    }

    private fun observeViewModel() {
        val username = intent.getStringExtra("username")

        viewModel.apply {
            getUserData(username)

            isLoading.observe(this@DetailActivity) {
                setProgressBar(it)
            }

            userDetail.observe(this@DetailActivity) {
                loadUserDetail(it!!)
            }

            checkUserIsFavorite(username!!).observe(this@DetailActivity) {
                val isFavorite = it != null
                setFavorite(isFavorite)
            }

            isFavorite.observe(this@DetailActivity) {
                setFabFavoriteIcon(it)
                isFavoriteDetail = it ?: false
            }
        }
    }

    private fun loadUserDetail(userDetail: ResponseDetail) {
        currentUser = User(
            userDetail.login!!,
            userDetail.avatarUrl
        )

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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}