package com.suleymanoner.kotlincountries.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleymanoner.kotlincountries.model.Country
import com.suleymanoner.kotlincountries.service.CountryDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : BaseViewModel(application) {

    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid : Int){

        launch {

            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getCountry(uuid)
            countryLiveData.value = country

        }
    }

}