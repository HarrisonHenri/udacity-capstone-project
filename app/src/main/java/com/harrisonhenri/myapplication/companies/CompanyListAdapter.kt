package com.harrisonhenri.myapplication.companies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import com.harrisonhenri.myapplication.databinding.CompanyItemBinding
import com.harrisonhenri.myapplication.repository.models.Company
import com.harrisonhenri.myapplication.utils.getDistanceLatLonKm

class CompanyListAdapter(private val userLatLong: LatLng?, private val clickListener: ClickListener, private val favoriteListener: ClickListener): ListAdapter<Company, CompanyListAdapter.CompanyViewHolder>(DiffCallback) {
    companion object DiffCallback: DiffUtil.ItemCallback<Company>() {
        override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem.id == newItem.id
        }

    }

    class CompanyViewHolder(private var binding: CompanyItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(company: Company, clickListener: ClickListener, favoriteListener: ClickListener, userLatLong: LatLng?) {
            val companyLatLng = LatLng(company.geoLat, company.geoLon)
            binding.company = company
            binding.distance = getDistanceLatLonKm(companyLatLng, userLatLong)
            binding.clickListener = clickListener
            binding.favoriteListener = favoriteListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder(CompanyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val company = getItem(position)
        holder.bind(company, clickListener, favoriteListener, userLatLong)
    }
}

class ClickListener(val clickListener: (Int) -> Unit) {
    fun onClick(menuId: Int) = clickListener(menuId)
}