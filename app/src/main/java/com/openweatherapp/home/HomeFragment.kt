package com.openweatherapp.home

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gitprofile.db.AppDatabase
import com.openweatherapp.R
import com.openweatherapp.model.CityModel
import com.openweatherapp.utils.BaseFragment
import com.openweatherapp.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_recyclerview.*


/**
 * Created by vishal on 2/21/2018.
 * Use for displaying City List.
 */
class HomeFragment : BaseFragment() {

    private lateinit var cityListAdapter: CityListAdapter

    private lateinit var viewModel: MyViewModel

    private lateinit var appDatabase: AppDatabase

    private lateinit var onCityTitleClick: OnCityTitleClick

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun init(view: View?) {

        appDatabase = AppDatabase.getInstance(activity)!!

        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)


        cityListAdapter = object : CityListAdapter() {

            override fun onItemClick(cityModel: CityModel) {
                super.onItemClick(cityModel)

                onCityTitleClick.onCityClick(cityModel)
            }
        }

        viewModel.getBookMarkedCity().observe(activity, Observer<List<CityModel>> { cityList ->

            if (cityList != null)
                 cityListAdapter.setData(cityList)

            if (tvEmptyView != null) {
                if (cityList == null || cityList.size == 0) {
                    tvEmptyView.visibility = View.VISIBLE
                } else {
                    tvEmptyView.visibility = View.GONE
                }
            }
        })

        rvCityList.adapter = cityListAdapter

        var layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        rvCityList.layoutManager = layoutManager

        enableSwipeToDeleteAndUndo()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onCityTitleClick = context as OnCityTitleClick
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(activity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {

                val position = viewHolder.adapterPosition

                val item = cityListAdapter.getData().get(position)

                cityListAdapter.removeItem(position)

                viewModel.removeCity(item)

                val snackbar = Snackbar
                        .make(constraintLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG)
                snackbar.setAction("UNDO") {
                    cityListAdapter.restoreItem(item, position)
                    rvCityList.scrollToPosition(position)

                    viewModel.addCity(item)

                }

                snackbar.setActionTextColor(Color.YELLOW)
                snackbar.show()

            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(rvCityList)
    }

    override fun setListener() {
    }

    override fun process() {
    }

    override fun onUpdate(`object`: Any?) {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }
}