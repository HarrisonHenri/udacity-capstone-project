package com.harrisonhenri.myapplication.companies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.harrisonhenri.myapplication.api.network.Api
import com.harrisonhenri.myapplication.api.parseCompaniesJsonResult
import com.harrisonhenri.myapplication.repository.models.Company
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class CompanyListViewModel(application: Application): AndroidViewModel(application) {
    val companyList = MutableLiveData<List<Company>>()
    val showLoading = MutableLiveData<Boolean>(false)
    private val TAG = CompanyListViewModel::class.simpleName

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
}