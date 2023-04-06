package com.example.userbrowser.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.R
import com.example.userbrowser.databinding.ActivityFavoriteBinding
import com.example.userbrowser.helper.ViewModelFactory
import com.example.userbrowser.ui.UserAdapter
import com.example.userbrowser.ui.detail.DetailActivity
import com.example.userbrowser.ui.setting.SettingActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter

    private val viewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite)
        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val actionFavorite = menu?.findItem(R.id.action_favorite)
        actionFavorite?.isVisible = false

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_setting) {
            Intent(this@FavoriteActivity, SettingActivity::class.java).also {
                startActivity(it)
            }
        }

        return true
    }
}