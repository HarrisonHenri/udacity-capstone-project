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
import com.harrisonhenri.myapplication.repository.models.Company
import com.harrisonhenri.myapplication.repository.models.Menu
import com.harrisonhenri.myapplication.utils.Constants
import kotlinx.coroutines.launch
import org.json.JSONObject

class CompanyListViewModel(application: Application): AndroidViewModel(application) {
    val companyList = MutableLiveData<List<Company>>()
    val menuHash = MutableLiveData<HashMap<Int, Menu>>()
    val showLoading = MutableLiveData<Boolean>(false)
    private val TAG = CompanyListViewModel::class.simpleName

    private val _navigateToMenu = MutableLiveData<String>()

    val navigateToMenu: LiveData<String>
        get() = _navigateToMenu

    val isAuthenticated = FirebaseUserLiveData().map { user ->
        user != null
    }

    init {
        _navigateToMenu.value = null
        viewModelScope.launch {
            getCompanyList()
        }
    }

    suspend fun getCompanyList(){
        showLoading.value = true
        try{
            val result  = Api.apiService.getApiPayload()
            companyList.value = parseCompaniesJsonResult(JSONObject(result))
            menuHash.value = parseMenuJsonResult(JSONObject(result))
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

    fun doneNavigation(){
        _navigateToMenu.value = null
    }
}