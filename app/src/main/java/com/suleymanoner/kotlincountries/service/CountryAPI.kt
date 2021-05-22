package com.suleymanoner.kotlincountries.service

import com.suleymanoner.kotlincountries.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //https://raw.githubusercontent.com/

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>

}