package com.harrisonhenri.myapplication.companies

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.firebase.ui.auth.AuthUI
import com.harrisonhenri.myapplication.api.network.Api
import com.harrisonhenri.myapplication.api.parseCompaniesJsonResult
import com.harrisonhenri.myapplication.api.parseMenuJsonResult
import com.harrisonhenri.myapplication.authentication.FirebaseUserLiveData
import com.harrisonhenri.myapplication.data.getDatabase
import com.harrisonhenri.myapplication.repository.CompanyRepository
import com.harrisonhenri.myapplication.repository.models.Company
import com.harrisonhenri.myapplication.repository.models.Menu
import com.harrisonhenri.myapplication.utils.Constants
import kotlinx.coroutines.launch
import org.json.JSONObject

class CompanyListViewModel(application: Application): AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val companyRepository = CompanyRepository(database)
    val showLoading = MutableLiveData(false)
    private val TAG = CompanyListViewModel::class.simpleName
    private val _navigateToMenu = MutableLiveData<Menu>()

    val navigateToMenu: LiveData<Menu>
        get() = _navigateToMenu

    val isAuthenticated = FirebaseUserLiveData().map { user ->
        user != null
    }


    init {
        _navigateToMenu.value = null
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

    private fun buildAvaiableProviders(): List<AuthUI.IdpConfig> = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build()
    )

    fun doneNavigation(){
        _navigateToMenu.value = null
    }

    fun companyClicked(menuId: Int) {
        Log.i("a", menuHash.value.toString())
        _navigateToMenu.value = menuHash.value?.get(menuId)
    }
}