package com.example.userbrowser.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userbrowser.UserItem
import com.example.userbrowser.databinding.FragmentFollowingBinding
import com.example.userbrowser.ui.UserAdapter

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = activity.let {
            ViewModelProvider(it!!)[DetailViewModel::class.java]
        }
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollowing.layoutManager = LinearLayoutManager(requireActivity())

        viewModel.followings.observe(requireActivity()) {
            if (it!!.isNotEmpty()) {
                loadFollowings(it)
            } else {
                binding.apply {
                    tvFollowingNotFound.visibility = View.VISIBLE
                    rvFollowing.visibility = View.GONE
                }
            }
        }

        viewModel.isLoading.observe(requireActivity()) {
            if (!it!!) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun loadFollowings(listFollowings: List<UserItem?>) {
        val adapter = UserAdapter(listFollowings)
        adapter.setClicked(object: UserAdapter.ItemCLicked {
            override fun click(position: Int) {
                Log.d(TAG, "pos: $position clicked")
            }
        })

        binding.rvFollowing.adapter = adapter
    }

    companion object {
        private const val TAG = "Following Fragment"
    }
}