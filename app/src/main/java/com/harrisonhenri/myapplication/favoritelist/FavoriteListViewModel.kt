package com.harrisonhenri.myapplication.favoritelist

import android.app.Application
import androidx.lifecycle.*
import com.harrisonhenri.myapplication.data.getDatabase
import com.harrisonhenri.myapplication.repository.CompanyRepository

class FavoriteListViewModel(application: Application): AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val companyRepository = CompanyRepository(database)


    val favoriteCompanyList = companyRepository.favoriteCompanyList
}