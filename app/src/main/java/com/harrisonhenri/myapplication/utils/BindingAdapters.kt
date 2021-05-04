package com.harrisonhenri.myapplication.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harrisonhenri.myapplication.R
import com.harrisonhenri.myapplication.companies.CompanyListAdapter
import com.harrisonhenri.myapplication.repository.models.Company
import com.squareup.picasso.Picasso

@BindingAdapter("companyList")
fun bindRecyclerView(recyclerView: RecyclerView, companyList: List<Company>?) {
    val adapter = recyclerView.adapter as CompanyListAdapter
    adapter.submitList(companyList)
}

@BindingAdapter("companyImage")
fun bindImageViewToCompanyImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url)
        .placeholder(R.drawable.company_logo_placeholder)
        .into(imageView)
}

@BindingAdapter("android:fadeVisible")
fun setFadeVisible(view: View, visible: Boolean? = true) {
    if (view.tag == null) {
        view.tag = true
        view.visibility = if (visible == true) View.VISIBLE else View.GONE
    } else {
        view.animate().cancel()
        if (visible == true) {
            if (view.visibility == View.GONE)
                view.alpha = 1f
        } else {
            if (view.visibility == View.VISIBLE)
                view.alpha = 0f
        }
    }
}