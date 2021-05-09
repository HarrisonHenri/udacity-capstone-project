package com.harrisonhenri.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.harrisonhenri.myapplication.api.network.Api
import com.harrisonhenri.myapplication.api.parseCompaniesJsonResult
import com.harrisonhenri.myapplication.api.parseMenuJsonResult
import com.harrisonhenri.myapplication.data.CompaniesDatabase
import com.harrisonhenri.myapplication.data.toDatabaseModel
import com.harrisonhenri.myapplication.data.toDomainModel
import com.harrisonhenri.myapplication.repository.models.Company
import com.harrisonhenri.myapplication.repository.models.Menu
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class CompanyRepository(private val database: CompaniesDatabase)
{

    val companyList : LiveData<List<Company>> = Transformations.map(database.companyDao.getCachedCompanies()) { companyEntities ->
        companyEntities.toDomainModel()
    }

    val menuHash = MutableLiveData<HashMap<Int, Menu>>()

    suspend fun getCompanies(){
        withContext(Dispatchers.IO){
            try{
                val result = Api.apiService.getApiPayload()
                val parsedCompanies = parseCompaniesJsonResult(JSONObject(result))
                database.companyDao.insertAll(*parsedCompanies.toDatabaseModel())
                menuHash.postValue(parseMenuJsonResult(JSONObject(result)))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}