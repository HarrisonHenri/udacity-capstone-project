package com.harrisonhenri.myapplication.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harrisonhenri.myapplication.R
import com.harrisonhenri.myapplication.companies.CompanyListAdapter
import com.harrisonhenri.myapplication.favoritelist.FavoriteListAdapter
import com.harrisonhenri.myapplication.menu.CategoryListAdapter
import com.harrisonhenri.myapplication.repository.models.Category
import com.harrisonhenri.myapplication.repository.models.Company
import com.squareup.picasso.Picasso

@BindingAdapter("companyList")
fun bindCompanyRecyclerView(recyclerView: RecyclerView, companyList: List<Company>?) {
    val adapter = recyclerView.adapter as CompanyListAdapter
    adapter.submitList(companyList)
}

@BindingAdapter("favoriteCompanyList")
fun bindFavoriteListRecyclerView(recyclerView: RecyclerView, favoriteCompanyList: List<Company>?) {
    val adapter = recyclerView.adapter as FavoriteListAdapter
    adapter.submitList(favoriteCompanyList)
}


@BindingAdapter("imageSource")
fun bindImageViewImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url)
        .placeholder(R.drawable.logo_placeholder)
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

@BindingAdapter("categoriesList")
fun bindMenuRecyclerView(recyclerView: RecyclerView, categoriesList: List<Category>?) {
    val adapter = recyclerView.adapter as CategoryListAdapter
    adapter.submitList(categoriesList)
}