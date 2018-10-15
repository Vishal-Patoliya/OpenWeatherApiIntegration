package com.openweatherapp.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.gitprofile.utils.Constants
import com.openweatherapp.CityWeather.CityWeatherFragment
import com.openweatherapp.R
import com.openweatherapp.help.HelpFragment
import com.openweatherapp.model.CityModel
import com.openweatherapp.setting.SettingActivity
import com.openweatherapp.utils.BaseFragment
import com.openweatherapp.utils.Common
import com.openweatherapp.utils.FragmentHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

/**
 * @author Vishal
 * Main Activity for Displaying Book Mark city, Setting  and Help screen.
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnCityTitleClick {

    override fun onCityClick(cityModel: CityModel) {

        selectedFragment = CityWeatherFragment()

        fab.visibility = View.GONE

        var bundle = Bundle()
        bundle.putSerializable(Constants.INTENT_KEY.cityInfo, cityModel)

        fragmentHandler.replaceFragment(this, supportFragmentManager, frameLayout.id, selectedFragment,
                null, bundle, true, selectedFragment.javaClass.simpleName, 0,
                FragmentHandler.ANIMATION_TYPE.NONE)
    }

    private lateinit var selectedFragment: BaseFragment
    private lateinit var fragmentHandler: FragmentHandler
    private lateinit var commoms: Common

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->

            val intent = Intent(this, LocationPickerActivity::class.java)
            startActivity(intent)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()


        init()
        process()
        setListener()

    }

    private fun init() {

        commoms = Common.getInstance()

        selectedFragment = HomeFragment()
        fragmentHandler = FragmentHandler()

    }

    private fun process() {

        fragmentHandler.addFragment(this, supportFragmentManager, frameLayout.id, selectedFragment,
                null, null, false, selectedFragment.javaClass.simpleName, 0, FragmentHandler.ANIMATION_TYPE.NONE)

    }


    private fun setListener() {

        nav_view.setNavigationItemSelectedListener(this)

    }

    override fun onBackPressed() {

        if (selectedFragment is CityWeatherFragment) {

            selectedFragment = HomeFragment()

            supportFragmentManager.popBackStack()

            fab.visibility = View.VISIBLE

        } else {

            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            } else {
                super.onBackPressed()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> {

                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {

                if (!(selectedFragment is HomeFragment || selectedFragment is CityWeatherFragment)) {

                    fab.visibility = View.VISIBLE

                    selectedFragment = HomeFragment()

                    fragmentHandler.replaceFragment(this, supportFragmentManager, frameLayout.id, selectedFragment,
                            null, null, false, selectedFragment.javaClass.simpleName,
                            0, FragmentHandler.ANIMATION_TYPE.NONE)

                }

            }

            R.id.nav_help -> {

                supportFragmentManager.popBackStack()
                fab.visibility = View.GONE

                if (!(selectedFragment is HelpFragment)) {

                    Handler().postDelayed({


                        selectedFragment = HelpFragment()

                        fragmentHandler.addFragment(this, supportFragmentManager, frameLayout.id, selectedFragment,
                                null, null, false, selectedFragment.javaClass.simpleName,
                                0, FragmentHandler.ANIMATION_TYPE.NONE)

                    }, 300)

                }
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}