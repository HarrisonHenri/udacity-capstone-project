package com.harrisonhenri.myapplication.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harrisonhenri.myapplication.databinding.CategoryItemBinding
import com.harrisonhenri.myapplication.repository.models.Category

class CategoryListAdapter(): ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class CategoryViewHolder(private var binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category = category
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val company = getItem(position)
        holder.bind(company)
    }
}