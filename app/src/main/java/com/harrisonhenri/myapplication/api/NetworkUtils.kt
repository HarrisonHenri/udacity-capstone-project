package com.harrisonhenri.myapplication.api

import android.location.Location
import com.harrisonhenri.myapplication.repository.models.Category
import com.harrisonhenri.myapplication.repository.models.Company
import com.harrisonhenri.myapplication.repository.models.Menu
import com.harrisonhenri.myapplication.utils.Constants.BASE_URL
import org.json.JSONArray
import org.json.JSONObject

fun parseCompaniesJsonResult(jsonResult: JSONObject): ArrayList<Company> {
    val companiesObjectsJson = jsonResult.getJSONArray("companies")

    val companiesList = ArrayList<Company>()

    for (i in 0 until companiesObjectsJson.length()) {
        val companyJson = companiesObjectsJson.getJSONObject(i)
        val id = companyJson.getInt("numericalId")
        val name = companyJson.getString("name")
        val address = companyJson.getString("address")
        val geoLat = companyJson.getString("geoLat").toDouble()
        val geoLon = companyJson.getString("geoLon").toDouble()
        val menuId = companyJson.getString("menu").replace(BASE_URL.plus("menu/"), "").toInt()

        val images = companyJson
            .getJSONArray("image").getJSONObject(0)

        val imageUrl = images.getString("url").replace("http:", "https:")


        val company = Company(id, name, address, imageUrl, menuId, geoLat, geoLon)

        companiesList.add(company)
    }


    return companiesList
}

fun parseMenuJsonResult(jsonResult: JSONObject): HashMap<Int, Menu> {
    val menusObjectsJson = jsonResult.getJSONArray("menus")
    val categoriesHash = parseCategoriesJsonResult(jsonResult.getJSONArray("categories"))

    val menusHash = HashMap<Int, Menu>()

    for (i in 0 until menusObjectsJson.length()) {
        val menuJson = menusObjectsJson.getJSONObject(i)
        val id = menuJson.getInt("numericalId")
        val categoriesArray =  menuJson.getJSONArray("categories")
        val categoriesList = ArrayList<Category>()

        for (j in 0 until categoriesArray.length()){
            val categoryId = categoriesArray.getString(j).replace(BASE_URL.plus("category/"), "").toInt()
            val category = categoriesHash.get(categoryId) as Category
            categoriesList.add(category)
        }

        menusHash[id] = Menu(id, categoriesList)
    }


    return menusHash
}

fun parseCategoriesJsonResult(categoriesObjectsJson: JSONArray): HashMap<Int, Category> {
    val categoriesHash = HashMap<Int, Category>()

    for (i in 0 until categoriesObjectsJson.length()) {
        val categoryJson = categoriesObjectsJson.getJSONObject(i)
        val id = categoryJson.getInt("numericalId")
        val name = categoryJson.getString("name")

        try {
            val images = categoryJson
                    .getJSONArray("image").getJSONObject(0)
            val imageUrl = images.getString("url").replace("http:", "https:")

            categoriesHash[id] = Category(id, name, imageUrl)
        } catch (e: Exception){
            categoriesHash[id] = Category(id, name, "https://images.onyo.com/LcuX3SFcXtRZB9xbopfL7NjdNbs=/0x0:450x480/fit-in/450x480/https://onyo.s3.amazonaws.com/picture/2d7b0782134945d8a92e8b76f6a1dc37.png")
        }
    }


    return categoriesHash
}