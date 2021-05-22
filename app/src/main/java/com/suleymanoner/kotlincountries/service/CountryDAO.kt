package com.suleymanoner.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.suleymanoner.kotlincountries.model.Country

@Dao
interface CountryDAO {
    //suspend, durdurup tekrar çalıştırmaya imkan veriyor.
    //vararg, sayısı belli olmayan objeleri bununla kullanılabiliyor.
    //list long , primary key döndürüyor.

    @Insert
    suspend fun insertAll(vararg countries : Country) : List<Long>

    @Query("SELECT * FROM country")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    suspend fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()

}