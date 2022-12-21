package com.example.android.kevkane87.footyteam

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.android.kevkane87.footyteam.database.GameResult
import com.example.android.kevkane87.footyteam.databinding.GameResultViewholderBinding

class GameResultAdapter : PagingDataAdapter<GameResult, GameResultViewHolder>(ARTICLE_DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameResultViewHolder =
        GameResultViewHolder(
            GameResultViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: GameResultViewHolder, position: Int) {
        val tile = getItem(position)
        if (tile != null) {
            holder.bind(tile)
        }
    }

    companion object {
        private val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<GameResult>() {
            override fun areItemsTheSame(oldItem: GameResult, newItem: GameResult): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GameResult, newItem: GameResult): Boolean =
                oldItem == newItem
        }
    }
}
