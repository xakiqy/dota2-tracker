package com.example.dota_match_tracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dota_match_tracker.database.PlayerInfoUI
import com.example.dota_match_tracker.databinding.DataTeamListViewBinding
import kotlinx.android.synthetic.main.data_team_list_view.view.*

class HeroesDataAdapter :
    ListAdapter<PlayerInfoUI, HeroesDataAdapter.HeroesDataViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeroesDataAdapter.HeroesDataViewHolder {
        return HeroesDataViewHolder(
            DataTeamListViewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(
        holder: HeroesDataAdapter.HeroesDataViewHolder,
        position: Int
    ) {
        val playerInfo = getItem(position)
        holder.bind(playerInfo)

        holder.itemView.setOnClickListener {
            if (holder.itemView.items.visibility == View.GONE) {
                holder.itemView.items.visibility = View.VISIBLE
            } else {
                holder.itemView.items.visibility = View.GONE
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlayerInfoUI>() {
        override fun areItemsTheSame(
            oldItem: PlayerInfoUI,
            newItem: PlayerInfoUI
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: PlayerInfoUI,
            newItem: PlayerInfoUI
        ): Boolean {
            return oldItem.heroName == newItem.heroName
        }
    }

    class HeroesDataViewHolder(private var binding: DataTeamListViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(playerInfo: PlayerInfoUI) {
            binding.property = playerInfo
            binding.executePendingBindings()
        }
    }
}