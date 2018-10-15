package com.gitprofile.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.openweatherapp.model.CityModel

/**
 * @author Vishal Patoliya
 * Created by vishal on 9/15/2018.
 * Use for DAO for open weather App DB for bookmark cities.
 */

@Dao
interface DataDao {

    @Query("SELECT * FROM tbl_city")
    fun getCityList(): LiveData<List<CityModel>>

    @Query("SELECT id FROM tbl_city WHERE id = :id")
    fun getCityDetail(id: Int): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookMarkCity(cityModel: CityModel?)

    @Delete
    fun deleteCity(cityModel: CityModel?)

    @Query("DELETE FROM tbl_city")
    fun deleteBookMarks()

}