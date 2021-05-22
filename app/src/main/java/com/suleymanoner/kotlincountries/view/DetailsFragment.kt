package com.suleymanoner.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.suleymanoner.kotlincountries.view.DetailsFragmentArgs
import com.suleymanoner.kotlincountries.R
import com.suleymanoner.kotlincountries.util.downloadFromUrl
import com.suleymanoner.kotlincountries.util.placeholderProgressBar
import com.suleymanoner.kotlincountries.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.country_item.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel : DetailsViewModel
    private var countryUUID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryUUID = DetailsFragmentArgs.fromBundle(it).countryUUID
        }

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.getDataFromRoom(countryUUID)

        observeLiveData()

    }


    private fun observeLiveData(){

        viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country ->

            country?.let {

                detailsCountryName.text = country.countryName
                detailsCountryCapital.text = country.countryCapital
                detailsCountryCurrency.text = country.countryCurrency
                detailsCountryLanguage.text = country.countryLanguage
                detailsCountryRegion.text = country.countryRegion
                context?.let {
                    detailsImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }
            }
        })
    }

}