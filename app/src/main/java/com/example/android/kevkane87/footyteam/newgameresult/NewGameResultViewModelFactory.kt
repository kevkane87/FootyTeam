package com.example.android.kevkane87.footyteam.newgameresult


import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider



class NewGameResultViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewGameResultViewModel::class.java)) {
            return NewGameResultViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}