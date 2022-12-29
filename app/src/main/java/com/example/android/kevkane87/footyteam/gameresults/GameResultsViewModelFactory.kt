package com.example.android.kevkane87.footyteam.gameresults


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class GameResultsViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameResultsViewModel::class.java)) {
            return GameResultsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}