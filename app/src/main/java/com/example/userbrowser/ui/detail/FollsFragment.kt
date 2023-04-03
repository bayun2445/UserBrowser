package com.example.userbrowser.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.api.UserItem
import com.example.userbrowser.databinding.FragmentFollsBinding
import com.example.userbrowser.ui.UserAdapter

class FollsFragment(private val section: String) : Fragment() {
    private var _binding: FragmentFollsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        _binding = FragmentFollsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFolls.layoutManager = LinearLayoutManager(requireActivity())
        
        when(section) {
            "follower" -> {
                viewModel.followers.observe(requireActivity()) {
                    if (it!!.isNotEmpty()) {
                        loadFolls(it)
                    } else {
                        binding.apply {
                            tvFollsNotFound.visibility = View.VISIBLE
                            rvFolls.visibility = View.GONE
                        }
                    }
                }
            }
            
            "following" -> {
                viewModel.followings.observe(requireActivity()) {
                    if (it!!.isNotEmpty()) {
                        loadFolls(it)
                    } else {
                        binding.apply {
                            tvFollsNotFound.visibility = View.VISIBLE
                            rvFolls.visibility = View.GONE
                        }
                    }
                }
            }
        }

        viewModel.isLoading.observe(requireActivity()) {
            if (!it!!) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadFolls(listFollss: List<UserItem?>) {
        val adapter = UserAdapter(listFollss)
        adapter.setClicked(object: UserAdapter.ItemCLicked {
            override fun click(position: Int) {
                Log.d(TAG, "pos: $position clicked")
            }
        })

        binding.rvFolls.adapter = adapter
    }



    companion object {
        private const val TAG = "Folls Fragment"
    }
}