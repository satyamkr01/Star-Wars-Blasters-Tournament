package com.unifydream.starwarsblasterstournament.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.unifydream.starwarsblasterstournament.data.model.Match
import com.unifydream.starwarsblasterstournament.databinding.ItemMatchBinding

class MatchesAdapter : ListAdapter<Match, MatchesAdapter.MatchViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = getItem(position)
        holder.bind(match)
    }

    inner class MatchViewHolder(private val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(match: Match) {
            binding.player1Name.text = match.player1Id
            binding.player2Name.text = match.player2Id
            binding.score.text = "${match.player1Score} - ${match.player2Score}"

            when {
                match.player1Score > match.player2Score -> binding.root.setBackgroundColor(Color.GREEN)
                match.player1Score < match.player2Score -> binding.root.setBackgroundColor(Color.RED)
                else -> binding.root.setBackgroundColor(Color.WHITE)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Match, newItem: Match): Boolean {
            return oldItem == newItem
        }
    }
}