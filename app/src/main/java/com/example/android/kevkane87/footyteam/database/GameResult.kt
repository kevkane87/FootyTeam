package com.example.android.kevkane87.footyteam.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameResult(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "home_team") val homeTeam: String?,
    @ColumnInfo(name = "away_team") val awayTeam: String?,
    @ColumnInfo(name = "home_goals") val homeGoals: Int?,
    @ColumnInfo(name = "away_goals") val awayGoals: Int?,
)