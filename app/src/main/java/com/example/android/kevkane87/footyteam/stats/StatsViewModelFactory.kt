package com.example.android.kevkane87.footyteam.stats

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.kevkane87.footyteam.newgameresult.NewGameResultViewModel

class StatsViewModelFactory (val app: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatsViewModel::class.java)) {
            return StatsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}