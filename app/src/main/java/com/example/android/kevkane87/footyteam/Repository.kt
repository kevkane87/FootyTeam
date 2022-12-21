package com.example.android.kevkane87.footyteam

import com.example.android.kevkane87.footyteam.database.GameResultDatabase

class Repository (private val database: GameResultDatabase){

    fun gameResultPagingSource() = database.gameResultDao.pagingSource()

}