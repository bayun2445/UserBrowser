package com.example.userbrowser.ui.setting

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.userbrowser.databinding.ActivitySettingBinding
import com.example.userbrowser.helper.SettingPreference
import com.example.userbrowser.helper.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(dataStore)
        val viewModel by viewModels<SettingViewModel> {
            ViewModelFactory.getInstance(pref = pref)
        }

        viewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDarkMode.isChecked = false
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveThemeSettings(isChecked)
        }
    }
}