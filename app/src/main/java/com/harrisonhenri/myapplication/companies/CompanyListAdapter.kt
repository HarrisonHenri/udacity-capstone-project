package com.harrisonhenri.myapplication.companies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harrisonhenri.myapplication.databinding.CompanyItemBinding
import com.harrisonhenri.myapplication.repository.models.Company

class CompanyListAdapter: ListAdapter<Company, CompanyListAdapter.CompanyViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<Company>() {
        override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class CompanyViewHolder(private var binding: CompanyItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Company) {
            binding.company = company
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder(CompanyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = getItem(position)
        holder.bind(company)
    }
}