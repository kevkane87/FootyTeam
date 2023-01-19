package com.example.android.kevkane87.footyteam.stats

import android.app.Application
import androidx.lifecycle.*
import com.example.android.kevkane87.footyteam.Repository
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.database.GameResultDatabase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.roundToInt

class StatsViewModel(
    application: Application,
) : ViewModel()  {

    var myTeam: String = ""
    var games = 0
    var wins = 0
    var draws = 0
    var losses = 0
    var winPercentage = 0.0
    var drawPercentage = 0.0
    var lossPercentage = 0.0
    var goalsFor = 0
    var goalsAgainst = 0
    var avGoalsFor = 0.0
    var avGoalsAgainst = 0.0
    var biggestWin = ""
    var biggestLoss = ""
    var form = ""

    private val repository = Repository(GameResultDatabase.getDatabase(application))

    val allResults = repository.getAllResults().asLiveData()

    var size = allResults.value?.size


    fun calculateStats(toDisplay: String){

        games = 0
        wins = 0
        draws = 0
        losses = 0
        goalsFor = 0
        goalsAgainst = 0
        biggestWin = ""
        biggestLoss = ""
        var biggestWinMargin = 0
        var biggestLossMargin = 0
        var biggestWinGoalsScored = 0
        var biggestLossGoalsConceded = 0
        val builder = StringBuilder()


        allResults.value?.forEach(){

            //for home games
            if (it.homeTeam == myTeam && toDisplay != "Away") {
                games++
                goalsFor += it.homeGoals!!
                goalsAgainst += it.awayGoals!!

                when (winLossDraw(it.homeGoals, it.awayGoals)){
                    "Win" -> {
                        wins++
                        builder.insert(0,"W")

                        if (winMargin(it.homeGoals, it.awayGoals) > biggestWinMargin){
                            biggestWinMargin = winMargin(it.homeGoals, it.awayGoals)
                            biggestWin = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestWinGoalsScored = it.homeGoals
                        } else if (winMargin(it.homeGoals, it.awayGoals) == biggestWinMargin && it.homeGoals > biggestWinGoalsScored ){
                            biggestWinMargin = winMargin(it.homeGoals, it.awayGoals)
                            biggestWin = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestWinGoalsScored = it.homeGoals
                        }
                    }
                    "Draw" -> {
                        draws++
                        builder.insert(0,"D")
                    }

                    "Loss" ->{
                        losses++
                        builder.insert(0,"L")

                        if (winMargin(it.awayGoals, it.homeGoals) > biggestLossMargin){
                            biggestLossMargin = winMargin(it.awayGoals, it.homeGoals)
                            biggestLoss = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestLossGoalsConceded = it.awayGoals
                        } else if (winMargin(it.awayGoals, it.homeGoals) == biggestLossMargin && it.awayGoals > biggestLossGoalsConceded ){
                            biggestLossMargin = winMargin(it.awayGoals, it.homeGoals)
                            biggestLoss = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestLossGoalsConceded = it.awayGoals
                        }
                    }
                }
            }

            //for away games
            else if (it.awayTeam == myTeam && toDisplay != "Home"){
                games++
                goalsFor += it.awayGoals!!
                goalsAgainst += it.homeGoals!!

                when (winLossDraw(it.awayGoals, it.homeGoals)){
                    "Win" -> {
                        wins++
                        builder.insert(0,"W")

                        if (winMargin(it.awayGoals, it.homeGoals) > biggestWinMargin){
                            biggestWinMargin = winMargin(it.awayGoals, it.homeGoals)
                            biggestWin = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestWinGoalsScored = it.awayGoals
                        } else if (winMargin(it.awayGoals, it.homeGoals) == biggestWinMargin && it.awayGoals > biggestWinGoalsScored ){
                            biggestWinMargin = winMargin(it.awayGoals, it.homeGoals)
                            biggestWin = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestWinGoalsScored = it.awayGoals
                        }
                    }
                    "Draw" -> {
                        draws++
                        builder.insert(0,"D")
                    }

                    "Loss" ->{
                        losses++
                        builder.insert(0,"L")

                        if (winMargin(it.homeGoals, it.awayGoals) > biggestLossMargin){
                            biggestLossMargin = winMargin(it.homeGoals, it.awayGoals)
                            biggestLoss = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestLossGoalsConceded = it.homeGoals
                        } else if (winMargin(it.homeGoals, it.awayGoals)== biggestLossMargin && it.homeGoals > biggestLossGoalsConceded ){
                            biggestLossMargin = winMargin(it.homeGoals, it.awayGoals)
                            biggestLoss = it.homeGoals.toString() + " - " + it.awayGoals.toString()
                            biggestLossGoalsConceded = it.homeGoals
                        }
                    }
                }
            }
        }

        if (games > 0) {
            form = builder.toString()

            winPercentage = (wins.toDouble() / games.toDouble() * 10000).roundToInt() / 100.0
            drawPercentage = (draws.toDouble() / games.toDouble() * 10000).roundToInt() / 100.0
            lossPercentage = (losses.toDouble() / games.toDouble() * 10000).roundToInt() / 100.0
            avGoalsFor = (goalsFor.toDouble() / games.toDouble() * 100).roundToInt() / 100.0
            avGoalsAgainst = (goalsAgainst.toDouble() / games.toDouble() * 100).roundToInt() / 100.0
        }
    }

    private fun winLossDraw (myTeamGoals: Int, oppGoals: Int) : String{
        return if (myTeamGoals > oppGoals) "Win"
        else if (myTeamGoals < oppGoals) "Loss"
        else "Draw"
    }

    private fun winMargin (winnerGoals: Int, loserGoals: Int) : Int{
        return winnerGoals - loserGoals
    }

}

sealed interface Result<T> {
    object Loading : Result<GameResult?>
    data class Success(val lst: List<Int>) : Result<GameResult?>
    data class Error(val err: Throwable) : Result<GameResult?>
}