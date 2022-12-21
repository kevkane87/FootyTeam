package com.example.android.kevkane87.footyteam

import androidx.recyclerview.widget.RecyclerView
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.databinding.GameResultViewholderBinding

class GameResultViewHolder (

    private val binding: GameResultViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
    fun bind(game: GameResult) {
        binding.apply {
            binding.date.text = game.date
            binding.text = game.homeTeam

        }
    }
}
