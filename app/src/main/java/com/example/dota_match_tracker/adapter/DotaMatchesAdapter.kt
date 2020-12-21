package com.example.dota_match_tracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dota_match_tracker.database.MatchData
import com.example.dota_match_tracker.databinding.ListMatchViewItemBinding

class DotaMatchesAdapter(val onClickListener: OnClickListener) :
    ListAdapter<MatchData, DotaMatchesAdapter.MatchesDataViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DotaMatchesAdapter.MatchesDataViewHolder {
        return MatchesDataViewHolder(
            ListMatchViewItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(
        holder: DotaMatchesAdapter.MatchesDataViewHolder,
        position: Int
    ) {
        val matchProperty = getItem(position)
        holder.bind(matchProperty)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(matchProperty)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MatchData>() {
        override fun areItemsTheSame(
            oldItem: MatchData,
            newItem: MatchData
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: MatchData,
            newItem: MatchData
        ): Boolean {
            return oldItem.matchId == newItem.matchId
        }
    }

    class MatchesDataViewHolder(private var binding: ListMatchViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(matchProperty: MatchData) {
            binding.property = matchProperty
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (matchProperty: MatchData) -> Unit) {
        fun onClick(matchProperty: MatchData) = clickListener(matchProperty)
    }
}