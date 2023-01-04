package com.example.android.kevkane87.footyteam.database

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


//DAO functions for accessing database
    @Dao
    interface GameResultDAO  {

        @Query("SELECT * FROM GameResult")
        fun pagingSource(): PagingSource<Int, GameResult>

        @Query("SELECT * FROM GameResult")
        fun getAllResults(): Flow<List<GameResult>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun saveGameResult(game: GameResult)

        @Query("DELETE FROM GameResult where id = :gameId")
        suspend fun deleteGameById(gameId: Int)

    }