package com.suleymanoner.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.suleymanoner.kotlincountries.view.DetailsFragmentArgs
import com.suleymanoner.kotlincountries.R
import com.suleymanoner.kotlincountries.databinding.FragmentDetailsBinding
import com.suleymanoner.kotlincountries.util.downloadFromUrl
import com.suleymanoner.kotlincountries.util.placeholderProgressBar
import com.suleymanoner.kotlincountries.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.country_item.*
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel : DetailsViewModel
    private var countryUUID = 0
    private lateinit var dataBinding : FragmentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return dataBinding.root
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

                dataBinding.selectedCountry = country

                /*
                detailsCountryName.text = country.countryName
                detailsCountryCapital.text = country.countryCapital
                detailsCountryCurrency.text = country.countryCurrency
                detailsCountryLanguage.text = country.countryLanguage
                detailsCountryRegion.text = country.countryRegion
                context?.let {
                    detailsImage.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
                }
                 */
            }
        })


    }

}