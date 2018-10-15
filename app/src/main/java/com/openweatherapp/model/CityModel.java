package com.openweatherapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.gitprofile.db.DbConstants;

import java.io.Serializable;

/**
 * @Author Vishal Patoliya
 * Use for show bookmark city
 */
@Entity(tableName = DbConstants.TABLE_NAME.TBL_CITY)
public class CityModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String cityName;

    private String address;

    private long lat;

    private long lng;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLng() {
        return lng;
    }

    public void setLng(long lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}