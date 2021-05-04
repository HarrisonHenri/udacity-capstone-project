package com.harrisonhenri.myapplication.api

import com.harrisonhenri.myapplication.repository.models.Company
import org.json.JSONObject

fun parseCompaniesJsonResult(jsonResult: JSONObject): ArrayList<Company> {
    val companiesObjectsJson = jsonResult.getJSONArray("companies")

    val companiesList = ArrayList<Company>()

    for (i in 0 until companiesObjectsJson.length()) {
        val companyJson = companiesObjectsJson.getJSONObject(i)
        val id = companyJson.getLong("numericalId")
        val name = companyJson.getString("name")
        val address = companyJson.getString("address")

        val images = companyJson
            .getJSONArray("image").getJSONObject(0)

        val imageUrl = images.getString("url").replace("http:", "https:")

        val company = Company(id, name, address, imageUrl)
        companiesList.add(company)
    }


    return companiesList
}