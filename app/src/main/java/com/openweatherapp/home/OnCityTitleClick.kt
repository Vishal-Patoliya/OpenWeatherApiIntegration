package com.openweatherapp.home

import com.openweatherapp.model.CityModel

/**
 * Created by vishal on 9/15/2018.
 * Use for on Notify into MainActivity after click on perticular bookmarked city.
 */
interface OnCityTitleClick {

    fun onCityClick(cityModel: CityModel)

}