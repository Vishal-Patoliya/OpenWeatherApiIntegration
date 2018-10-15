package com.openweatherapp.CityWeather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gitprofile.rest.ApiInterface
import com.gitprofile.utils.Constants
import com.google.gson.Gson
import com.openweatherapp.utils.BaseFragment
import com.openweatherapp.R
import com.openweatherapp.model.CityModel
import com.openweatherapp.model.ErrorResponse
import com.openweatherapp.model.TodayForecastModel
import com.openweatherapp.utils.PreferencesHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_city_weather.*
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by vishal on 10/14/2018.
 * Use for displaying Weather information for book marked city.
 */
class CityWeatherFragment : BaseFragment(), View.OnClickListener {

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.ivBack -> {
                activity.onBackPressed()
            }

        }

    }

    private lateinit var apiService: ApiInterface

    private lateinit var cityModel: CityModel


    private lateinit var unit: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_city_weather, container, false)
    }

    override fun init(view: View?) {

        apiService = ApiInterface.create()

        val preferencesHelper = PreferencesHelper(activity)
        unit = preferencesHelper.unit

    }

    override fun setListener() {

        ivBack.setOnClickListener(this)
    }

    override fun process() {

        cityModel = arguments.getSerializable(Constants.INTENT_KEY.cityInfo) as CityModel

        cityWeatherContainer.visibility = View.GONE

        getUserList(cityModel)

        tvCityName.text = cityModel.cityName

    }

    override fun onUpdate(`object`: Any?) {
    }


    /**
     * Calling user profile list from Github users List.
     */
    private fun getUserList(cityModel: CityModel) {


        val call = apiService.getTodayWeatherInfo(cityModel.lat.toString(),
                cityModel.lng.toString(), Constants.URL.apiKey,
                unit)

        call.enqueue(object : Callback<TodayForecastModel> {
            override fun onResponse(call: Call<TodayForecastModel>, response: retrofit2.Response<TodayForecastModel>?) {

                if (context == null)
                   return

                if (response != null) {

                    if (response.code() == 200) {

                        cityWeatherContainer.visibility = View.VISIBLE

                        val todayForecastModel: TodayForecastModel? = response.body()

                        displayWeatherInfoData(todayForecastModel)

                    }
                } else {

                    if (response?.errorBody()?.string() == null) {
                        val gson = Gson()
                        val errorResponse = gson.fromJson(
                                response?.errorBody().toString(),
                                ErrorResponse::class.java)

                        Toast.makeText(activity, errorResponse.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<TodayForecastModel>, t: Throwable) {
                progressBarWeather.visibility = View.GONE
            }
        })
    }

    /**
     * Displaying weather data after getting response from API.
     */
    private fun displayWeatherInfoData(todayForecastModel: TodayForecastModel?) {

        var temperature = todayForecastModel?.main?.temp

        if (unit != Constants.APP_PREF.metric) {
            temperature = convertFahrenheitToCelcius(todayForecastModel?.main?.temp!!)
        }

        tvTemperature.text = temperature.toString() + " " + getString(R.string.celcius)

        if (todayForecastModel?.weather?.size!! > 0) {

            tvWeatherInfo.text = "Sky is " + todayForecastModel?.weather?.get(0)?.main.toString()

            val url: String = Constants.URL.imageUrl +
                    todayForecastModel?.weather?.get(0)?.icon.toString() +
                    ".png"

            Picasso.get().load(url).into(ivWeatherIcon)

        }

        tvPressure.text = todayForecastModel?.main?.pressure.toString() + " " + "mmHg"

        tvHumidity.text = todayForecastModel?.main?.humidity.toString() + " " + "%"

        tvWindSpeed.text = todayForecastModel?.wind.speed?.toString()

        tvWindDeg.text = todayForecastModel?.wind.deg?.toString() + getString(R.string.degree)

        tvSunrise.text = todayForecastModel?.sys.sunrise?.toString()
        tvSunSet.text = todayForecastModel?.sys.sunset?.toString()


    }


    // Converts to celcius
    private fun convertFahrenheitToCelcius(fahrenheit: Float): Float {
        return (fahrenheit - 32) * 5 / 9
    }

    // Converts to fahrenheit
    private fun convertCelciusToFahrenheit(celsius: Float): Float {
        return celsius * 9 / 5 + 32
    }
}