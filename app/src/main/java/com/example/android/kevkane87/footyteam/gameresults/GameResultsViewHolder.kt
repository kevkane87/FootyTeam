package com.example.android.kevkane87.footyteam.gameresults

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.databinding.GameResultsViewholderBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class GameResultsViewHolder (
    private val binding: GameResultsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.UK)
    private val dateFormatDatabase = DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.UK)

    var id: Int? = null

    fun bind(game: GameResult) {
        binding.apply {
            val date = LocalDate.parse(game.date, dateFormatDatabase)
            binding.date.text = dateFormat.format(date)
            binding.homeTeam.text = game.homeTeam
            binding.awayTeam.text = game.awayTeam
            binding.homeGoals.text = game.homeGoals.toString()
            binding.awayGoals.text = game.awayGoals.toString()
            id = (game as? GameResult)?.id
        }
    }
}
