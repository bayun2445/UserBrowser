package com.example.userbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

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

    private fun observeViewModel() {
        viewModel.listUser.observe(this) {
            loadUserData(it)
        }

        viewModel.isLoading.observe(this) {
            setProgressBar(it)
        }
    }

    private fun setProgressBar(loading: Boolean?) {
        if (loading == true) {
            binding.rvUsers.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }

    private fun loadUserData(listUser: List<UserItem?>?) {
        val adapter = UserAdapter(listUser)

        //User item click listener
        adapter.setClicked(object : UserAdapter.ItemCLicked {
            override fun click(position: Int) {
                //TODO: pass to detail activity
            }
        })
        binding.rvUsers.adapter = adapter
    }
}