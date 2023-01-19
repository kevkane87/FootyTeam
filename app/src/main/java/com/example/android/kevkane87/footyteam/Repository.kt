package com.example.android.kevkane87.footyteam

import androidx.lifecycle.LiveData
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.database.GameResultDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext


class Repository(private val database: GameResultDatabase) {

    fun gameResultPagingSource() = database.gameResultDao.pagingSource()

    fun getAllResults(): Flow<List<GameResult>> = database.gameResultDao.getAllResults()


    suspend fun saveBet(game: GameResult) =
        withContext(Dispatchers.IO) {
            try {
                database.gameResultDao.saveGameResult(game)
            } catch (_: Exception) {
            }
        }

    suspend fun deleteGame(id: Int) =
        withContext(Dispatchers.IO) {
            try {
                database.gameResultDao.deleteGameById(id)
            } catch (_: Exception) {

            }
        }
}