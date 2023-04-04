package com.example.userbrowser.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userbrowser.ui.favorite.FavoriteViewModel
import com.example.userbrowser.ui.detail.DetailViewModel
import com.example.userbrowser.ui.setting.SettingViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application? = null,
    private val pref: SettingPreference? = null
): ViewModelProvider.NewInstanceFactory() {


    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application? = null, pref: SettingPreference? = null): ViewModelFactory {
            synchronized(ViewModelFactory::class.java) {
                INSTANCE = ViewModelFactory(application, pref)
            }

            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(application = mApplication!!) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(application = mApplication!!) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(pref = pref!!) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class ${modelClass.name}")
    }
}