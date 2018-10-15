package com.openweatherapp.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.openweatherapp.R
import com.openweatherapp.model.CityModel
import com.openweatherapp.utils.Common


/**
 * Created by vishal on 14/09/2018
 * User for list city into recycler view which are fetch from DB.
 */
open class CityListAdapter() : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {

    private var cityModelList: List<CityModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_city_bookmark, parent, false))

    }


    fun restoreItem(cityModel: CityModel, position: Int) {
        (cityModelList as MutableList).add(position, cityModel)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        (cityModelList as MutableList).removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int {
        return cityModelList.size
    }

    fun setData(cityModel: List<CityModel>) {
        this.cityModelList = cityModel
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val cityModel: CityModel = cityModelList.get(position)

        holder.tvCityName.setText(cityModel.cityName)
        holder.tvAddress.setText(cityModel.address)

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvCityName: TextView
        var tvAddress: TextView
        var llBookMarkedCityInfo: LinearLayout

        init {
            tvCityName = itemView.findViewById(R.id.tvCityName)
            tvAddress = itemView.findViewById(R.id.tvAddress)
            llBookMarkedCityInfo = itemView.findViewById(R.id.llBookMarkedCityInfo)

            llBookMarkedCityInfo.setOnClickListener(this)

        }

        override fun onClick(itemView: View?) {
            when (itemView?.id) {

                R.id.llBookMarkedCityInfo ->
                        onItemClick(cityModelList.get(layoutPosition))
            }
        }

    }

    open fun getData(): List<CityModel> {
        return cityModelList
    }

    open fun onItemClick(cityModel: CityModel) {
    }
}