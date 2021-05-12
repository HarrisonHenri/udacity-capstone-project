package com.harrisonhenri.myapplication.companies

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI
import com.harrisonhenri.myapplication.authentication.FirebaseUserLiveData
import com.harrisonhenri.myapplication.data.getDatabase
import com.harrisonhenri.myapplication.repository.CompanyRepository
import com.harrisonhenri.myapplication.repository.models.Menu
import kotlinx.coroutines.launch

class CompanyListViewModel(application: Application): AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val companyRepository = CompanyRepository(database)
    val showLoading = MutableLiveData(false)
    private val TAG = CompanyListViewModel::class.simpleName

    private val _navigateToMenu = MutableLiveData<Menu>()

    val navigateToMenu: LiveData<Menu>
        get() = _navigateToMenu

    private val _navigateToFavoriteList = MutableLiveData<Boolean>()

    val navigateToFavoriteList: LiveData<Boolean>
        get() = _navigateToFavoriteList


    val isAuthenticated = FirebaseUserLiveData().map { user ->
        user != null
    }


    init {
        _navigateToMenu.value = null
        _navigateToFavoriteList.value = null
        showLoading.value = true
        viewModelScope.launch {
            companyRepository.getCompanies()
            showLoading.postValue(false)
        }
    }

    val companyList = companyRepository.companyList
    val menuHash = companyRepository.menuHash

    fun getLoginIntent(): Intent {
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLockOrientation(true)
            .setAvailableProviders(buildAvaiableProviders())
            .setIsSmartLockEnabled(false)
            .build()
    }

    fun onFavoriteClicked(companyId: Int){
        viewModelScope.launch {
            companyRepository.companyFavorite(companyId = companyId.toString())
        }
    }

    fun onStarButtonClicked() {
        _navigateToFavoriteList.value = true
    }

    private fun buildAvaiableProviders(): List<AuthUI.IdpConfig> = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
    )

    fun doneMenuNavigation(){
        _navigateToMenu.value = null
    }

    fun doneFavoriteListNavigation(){
        _navigateToFavoriteList.value = null
    }

    fun companyClicked(menuId: Int) {
        _navigateToMenu.value = menuHash.value?.get(menuId)
    }
}