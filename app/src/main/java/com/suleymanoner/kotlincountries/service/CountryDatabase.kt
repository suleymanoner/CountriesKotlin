package com.suleymanoner.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.suleymanoner.kotlincountries.model.Country
import kotlinx.coroutines.newFixedThreadPoolContext

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase : RoomDatabase() {

    abstract fun countryDao() : CountryDAO

    companion object {

        //volatile, diğer threadler için görünür hale geliyor. Coroutine kullanacağımız için volatile gerekiyor.
        @Volatile private var instance : CountryDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }


        private fun makeDatabase(context : Context) = Room.databaseBuilder(
                context.applicationContext, CountryDatabase::class.java, "countrydatabase").build()

    }

}