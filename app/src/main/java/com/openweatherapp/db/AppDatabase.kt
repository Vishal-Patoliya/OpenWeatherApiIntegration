package com.gitprofile.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.openweatherapp.model.CityModel

/**
 * Created by vishal on 9/15/2018.
 * Database instanse for performing operation on DB.
 */

@Database(entities = arrayOf(CityModel::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wheatherAppDao(): DataDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase::class.java, "openweatherapp")
                            .allowMainThreadQueries()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}