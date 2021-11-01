package com.htf.kidzoon.Room.Listeners

import com.htf.kidzoon.models.Country

interface CountryListener {
    fun onGetAllCountries(list: List<Country>?)
    fun onCountryClickListener(country: Country)
}