package com.example.android.kevkane87.footyteam.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameResult::class], version = 1)
abstract class GameResultDatabase : RoomDatabase() {

    abstract val gameResultDao: GameResultDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: GameResultDatabase? = null

        fun getDatabase(context: Context): GameResultDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameResultDatabase::class.java,
                    "results_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}