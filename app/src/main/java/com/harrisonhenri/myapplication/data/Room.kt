package com.harrisonhenri.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harrisonhenri.myapplication.data.dao.CompanyDao
import com.harrisonhenri.myapplication.data.entities.CompanyEntity

@Database(entities = [CompanyEntity::class], version = 1)
abstract class CompaniesDatabase : RoomDatabase() {
    abstract val companyDao: CompanyDao
}

private lateinit var INSTANCE: CompaniesDatabase

fun getDatabase(context: Context): CompaniesDatabase {
    synchronized(CompaniesDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                CompaniesDatabase::class.java,
                "asteroids").build()
        }
    }
    return INSTANCE
}