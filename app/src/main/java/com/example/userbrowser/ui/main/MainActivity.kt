package com.example.userbrowser.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.R
import com.example.userbrowser.api.UserItem
import com.example.userbrowser.database.User
import com.example.userbrowser.databinding.ActivityMainBinding
import com.example.userbrowser.helper.SettingPreference
import com.example.userbrowser.helper.ViewModelFactory
import com.example.userbrowser.ui.detail.DetailActivity
import com.example.userbrowser.ui.UserAdapter
import com.example.userbrowser.ui.favorite.FavoriteActivity
import com.example.userbrowser.ui.setting.SettingActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(dataStore)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(pref = pref))[MainViewModel::class.java]

        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        observeViewModel()

        binding.searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_favorite -> {
                Intent(this@MainActivity, FavoriteActivity::class.java).also {
                    startActivity(it)
                }

                return true
            }
            R.id.action_setting -> {
                Intent(this@MainActivity, SettingActivity::class.java).also {
                    startActivity(it)
                }

                return true
            }

            else -> return true
        }
    }

    private fun observeViewModel() {
        viewModel.listUser.observe(this) {
            if (it!!.isEmpty()) {
                binding.apply {
                    tvNotFound.visibility = View.VISIBLE
                    rvUsers.visibility = View.INVISIBLE
                }
            } else {
                binding.tvNotFound.visibility = View.INVISIBLE
                loadUserData(it)
            }
        }

        viewModel.isLoading.observe(this) {
            setProgressBar(it)
        }

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setProgressBar(loading: Boolean?) {
        if (loading == true) {
            binding.apply {
                rvUsers.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
                tvNotFound.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.INVISIBLE
                rvUsers.visibility = View.VISIBLE
            }
        }
    }

    private fun loadUserData(listUser: List<UserItem?>?) {
        val newListUser = ArrayList<User>()
        listUser!!.map {
            val user = User(
                it!!.login!!,
                it.avatarUrl
            )
            newListUser.add(user)
        }

        val adapter = UserAdapter()
        adapter.setListUser(newListUser)

        //User item click listener
        adapter.setClicked(object : UserAdapter.ItemCLicked {
            override fun click(position: Int) {
                val username = listUser[position]?.login
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra("username", username)
                    startActivity(it)
                }
            }
        })

        binding.rvUsers.apply {
            this.adapter = adapter
            setHasFixedSize(true)
        }
    }
}