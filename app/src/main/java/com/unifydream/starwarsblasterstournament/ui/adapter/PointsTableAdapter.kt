package com.unifydream.starwarsblasterstournament.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.unifydream.starwarsblasterstournament.R
import com.unifydream.starwarsblasterstournament.data.model.Player
import com.unifydream.starwarsblasterstournament.data.model.PointsTableEntry
import com.unifydream.starwarsblasterstournament.databinding.ItemPlayerBinding

class PointsTableAdapter(
    private val onPlayerClick: (Player) -> Unit
) : ListAdapter<PointsTableEntry, PointsTableAdapter.PointsViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointsViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PointsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PointsViewHolder, position: Int) {
        val entry = getItem(position)
        holder.bind(entry)
    }

    inner class PointsViewHolder(private val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: PointsTableEntry) {
            binding.playerName.text = entry.player.name
            binding.playerPoints.text = entry.points.toString()
            Glide.with(binding.playerAvatar.context)
                .load(entry.player.avatarUrl)
                .error(R.drawable.ic_launcher_background)
                .into(binding.playerAvatar)

            binding.root.setOnClickListener {
                onPlayerClick(entry.player)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PointsTableEntry>() {
        override fun areItemsTheSame(oldItem: PointsTableEntry, newItem: PointsTableEntry): Boolean {
            return oldItem.player.id == newItem.player.id
        }

        override fun areContentsTheSame(oldItem: PointsTableEntry, newItem: PointsTableEntry): Boolean {
            return oldItem == newItem
        }
    }
}