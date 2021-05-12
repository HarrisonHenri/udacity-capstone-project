package com.harrisonhenri.myapplication.favoritelist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harrisonhenri.myapplication.databinding.FavoriteItemBinding
import com.harrisonhenri.myapplication.repository.models.Company


class FavoriteListAdapter(): ListAdapter<Company, FavoriteListAdapter.FavoriteViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<Company>() {
        override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class FavoriteViewHolder(private var binding: FavoriteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Company) {
            binding.company = company
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val company = getItem(position)
        holder.bind(company)
    }
}