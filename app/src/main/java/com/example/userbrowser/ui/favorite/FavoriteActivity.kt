package com.example.userbrowser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.R
import com.example.userbrowser.databinding.ActivityFavoriteBinding
import com.example.userbrowser.helper.ViewModelFactory
import com.example.userbrowser.ui.UserAdapter
import com.example.userbrowser.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)

        viewModel = obtainViewModel(this@FavoriteActivity)

        loadFavoriteUserData()
    }

    private fun loadFavoriteUserData() {
        viewModel.getAllFavoriteUser().observe(this) { listFavorite ->
            if (!listFavorite.isNullOrEmpty()) {
                adapter = UserAdapter()
                adapter.setListUser(listFavorite)
                adapter.setClicked(object : UserAdapter.ItemCLicked {
                    override fun click(position: Int) {
                        Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                            it.putExtra("isFavorite", true)
                            it.putExtra("username", listFavorite[position].username)
                            startActivity(it)
                        }
                    }
                })
                binding.rvFavoriteUsers.adapter = adapter
                binding.rvFavoriteUsers.setHasFixedSize(true)
            } else {
                binding.tvFavoriteNotFound.visibility = View.VISIBLE
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this@FavoriteActivity, factory)[FavoriteViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val actionFavorite = menu?.findItem(R.id.action_favorite)
        actionFavorite?.isVisible = false

        return true
    }
}