package com.suleymanoner.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.suleymanoner.kotlincountries.R
import com.suleymanoner.kotlincountries.model.Country
import com.suleymanoner.kotlincountries.util.downloadFromUrl
import com.suleymanoner.kotlincountries.util.placeholderProgressBar
import com.suleymanoner.kotlincountries.view.CountriesFragmentDirections
import kotlinx.android.synthetic.main.country_item.view.*

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.country_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        holder.view.countryName.text = countryList[position].countryName
        holder.view.countryRegion.text = countryList[position].countryRegion

        holder.view.setOnClickListener {
            val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.view.context))
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    // if data updated in api
    fun updateCountryList(newCountryList : List<Country>){

        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()
    }
}