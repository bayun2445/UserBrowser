package com.example.userbrowser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.databinding.FragmentFollowerBinding

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollower.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.followers.observe(requireActivity()) {
            if (it!!.isNotEmpty()) {
                loadFollowers(it)
            } else {
                binding.apply {
                    tvFollowerNotFound.visibility = View.VISIBLE
                    rvFollower.visibility = View.GONE
                }
            }
        }

        viewModel.isLoading.observe(requireActivity()) {
            if (!it!!) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadFollowers(listFollowers: List<UserItem?>) {
        val adapter = UserAdapter(listFollowers)
        adapter.setClicked(object: UserAdapter.ItemCLicked {
            override fun click(position: Int) {
                Log.d(TAG, "pos: $position clicked")
            }
        })

        binding.rvFollower.adapter = adapter
    }



    companion object {
        private const val TAG = "Follower Fragment"
    }
}