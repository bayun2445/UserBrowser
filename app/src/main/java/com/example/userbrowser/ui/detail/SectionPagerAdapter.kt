package com.example.userbrowser.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = FollsFragment()
        val bundle = Bundle()
        val key = "section"

        when(position) {
            0 -> bundle.putString(key, "follower")
            1 -> bundle.putString(key, "following")
        }

        fragment.arguments = bundle

        return fragment
    }

}