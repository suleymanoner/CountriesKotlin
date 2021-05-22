package com.suleymanoner.kotlincountries.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.suleymanoner.kotlincountries.R
import com.suleymanoner.kotlincountries.databinding.CountryItemBinding
import com.suleymanoner.kotlincountries.model.Country
import com.suleymanoner.kotlincountries.util.downloadFromUrl
import com.suleymanoner.kotlincountries.util.placeholderProgressBar
import com.suleymanoner.kotlincountries.view.CountriesFragmentDirections
import kotlinx.android.synthetic.main.country_item.view.*

class CountryAdapter(val countryList : ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), CountryClickListener {

    class CountryViewHolder(var view: CountryItemBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<CountryItemBinding>(inflater, R.layout.country_item, parent, false)
        //val view = inflater.inflate(R.layout.country_item, parent, false) without data binding
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

        // this is data binding
        holder.view.country = countryList[position]

        // listener ı direk adaptere veriyoruz.
        holder.view.listener = this



        /*
        holder.view.countryName.text = countryList[position].countryName
        holder.view.countryRegion.text = countryList[position].countryRegion

        holder.view.setOnClickListener {
            val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment(countryList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeholderProgressBar(holder.view.context))

         */
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

    override fun onCountryClicked(v: View) {

        val uuid = v.uuidFromXML.text.toString().toInt()
        val action = CountriesFragmentDirections.actionCountriesFragmentToDetailsFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}