package com.openweatherapp.home

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.os.AsyncTask
import com.gitprofile.db.AppDatabase
import com.openweatherapp.model.CityModel

/**
 * Created by vishal on 10/13/2018.
 * View Moedl for storing Virtual Layer of application and Observe DB operation.
 */
class MyViewModel(application: Application) : AndroidViewModel(application)  {

    private var cityList: LiveData<List<CityModel>>

    private var appDatabase: AppDatabase

    init {
        appDatabase = AppDatabase.getInstance(application)!!

        cityList = appDatabase.wheatherAppDao().getCityList()

    }

    /**
     * Use for getting Book Marked City.
     */
    fun getBookMarkedCity(): LiveData<List<CityModel>> {
        return cityList
    }

    /**
     * Adding book mark city.
     */
    fun addCity(cityModel: CityModel) {
        addAsynTask(appDatabase).execute(cityModel)
    }


    /**
     * Removing Book Marked City.
     */
    fun removeCity(cityModel: CityModel) {
        appDatabase.wheatherAppDao().deleteCity(cityModel)
    }

    /**
     * Async Task for doing insertion process for City into Background
     */
    class addAsynTask(db: AppDatabase) : AsyncTask<CityModel, Void, Void>() {
        private var contactDb = db
        override fun doInBackground(vararg params: CityModel): Void? {
            contactDb.wheatherAppDao().insertBookMarkCity(params[0])
            return null
        }

    }

}