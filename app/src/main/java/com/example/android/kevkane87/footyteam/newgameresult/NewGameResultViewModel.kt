package com.example.android.kevkane87.footyteam.newgameresult

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.kevkane87.footyteam.Repository
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.database.GameResultDatabase
import kotlinx.coroutines.launch

class NewGameResultViewModel(
    application: Application,
) : ViewModel() {

    private val repository = Repository(GameResultDatabase.getDatabase(application))

    fun saveResult(gameResult: GameResult) {

        viewModelScope.launch {
            repository.saveBet(gameResult)
        }
    }
}