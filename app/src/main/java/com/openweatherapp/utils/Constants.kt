package com.gitprofile.utils

/**
 * Created by vishal on 10/14/2018.
 * Applicatin constants
 */
class Constants {

    object URL {

        const val baseUrl = "http://api.openweathermap.org/data/2.5/"
        const val apiKey = "c6e381d8c7ff98f0fee43775817cf6ad"
        const val imageUrl = "http://openweathermap.org/img/w/"
        const val openWeatherWebPage = "https://openweathermap.org/find?q="

    }

    object INTENT_KEY {

        const val cityInfo = "cityInfo"

    }

    object API_PARMAS {

        const val lat = "lat"
        const val lon = "lon"
        const val appId = "appid"
        const val units = "units"
        const val weather = "weather"

    }

    object APP_PREF {
        const val metric = "metric"
        const val unit = "unit"

    }

}