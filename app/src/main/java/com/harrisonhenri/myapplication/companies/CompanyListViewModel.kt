package com.harrisonhenri.myapplication.companies

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.firebase.ui.auth.AuthUI
import com.harrisonhenri.myapplication.api.network.Api
import com.harrisonhenri.myapplication.api.parseCompaniesJsonResult
import com.harrisonhenri.myapplication.authentication.FirebaseUserLiveData
import com.harrisonhenri.myapplication.repository.models.Company
import com.harrisonhenri.myapplication.utils.Constants
import kotlinx.coroutines.launch
import org.json.JSONObject

class CompanyListViewModel(application: Application): AndroidViewModel(application) {
    val companyList = MutableLiveData<List<Company>>()
    val showLoading = MutableLiveData<Boolean>(false)
    private val TAG = CompanyListViewModel::class.simpleName

    val isAuthenticated = FirebaseUserLiveData().map { user ->
        user != null
    }

    init {
        viewModelScope.launch {
            getCompanyList()
        }
    }

    suspend fun getCompanyList(){
        showLoading.value = true
        try{
            val companiesResult  = Api.apiService.getApiPayload()
            companyList.value = parseCompaniesJsonResult(JSONObject(companiesResult))
        } catch (e: Exception){
            Log.i(TAG, e.message ?: "Api query fail")
        }
        showLoading.value = false
    }

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
}