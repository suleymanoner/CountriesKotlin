package com.suleymanoner.kotlincountries.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleymanoner.kotlincountries.model.Country
import com.suleymanoner.kotlincountries.service.CountryAPI
import com.suleymanoner.kotlincountries.service.CountryAPIService
import com.suleymanoner.kotlincountries.service.CountryDatabase
import com.suleymanoner.kotlincountries.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CountriesViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L  // nanosaniye cinsinden 10 dk

    val countries = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        val updateTime = customPreferences.getTime()

        // eğer 10 dk dan az ise, database den alıcak.
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }

    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }


    private fun getDataFromSQLite(){
        countryLoading.value = true

        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries from SQLite",Toast.LENGTH_LONG).show()
        }
    }


    private fun getDataFromAPI(){

        countryLoading.value = true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {

                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries from API",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showCountries(list : List<Country>){
        countries.value = list
        countryError.value = false
        countryLoading.value = false
    }

    private fun storeInSQLite(list : List<Country>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()

            //toTypedArray , listeyi teker teker hale getiriyor. çünkü vararg tek tek alıyor.
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0

            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }

            showCountries(list)
        }

        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        //hafızayı verimli kullanabilmek için, uygulama kapanırken disposable temizliyor.
    }

}