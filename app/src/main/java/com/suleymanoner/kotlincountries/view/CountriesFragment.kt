package com.suleymanoner.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.suleymanoner.kotlincountries.R
import com.suleymanoner.kotlincountries.adapter.CountryAdapter
import com.suleymanoner.kotlincountries.viewmodel.CountriesViewModel
import kotlinx.android.synthetic.main.fragment_countries.*

class CountriesFragment : Fragment() {

    private lateinit var viewModel : CountriesViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)
        viewModel.refreshData()

        countryRecyclerView.layoutManager = LinearLayoutManager(context)
        countryRecyclerView.adapter = countryAdapter

        refreshLayout.setOnRefreshListener {

            countryRecyclerView.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE

            viewModel.refreshFromAPI()

            refreshLayout.isRefreshing = false

        }

        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.countries.observe(viewLifecycleOwner, Observer { countries ->

            countries?.let {

                countryRecyclerView.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->

            error?.let {
                if(it){
                    countryError.visibility = View.VISIBLE
                } else {
                    countryError.visibility = View.GONE
                }
            }
        })

        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading ->

            loading?.let {
                if(it) {
                    countryLoading.visibility = View.VISIBLE
                    countryRecyclerView.visibility = View.GONE
                    countryError.visibility = View.GONE
                } else {
                    countryLoading.visibility = View.GONE
                }
            }
        })
    }

}