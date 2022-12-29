package com.example.android.kevkane87.footyteam.gameresults

import androidx.recyclerview.widget.RecyclerView
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.databinding.GameResultsViewholderBinding

class GameResultsViewHolder (

    private val binding: GameResultsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: GameResult) {
        binding.apply {
            binding.date.text = game.date
            binding.homeTeam.text = game.homeTeam
            binding.awayTeam.text = game.awayTeam
            binding.homeGoals.text = game.homeGoals.toString()
            binding.awayGoals.text = game.awayGoals.toString()
        }
    }
}
