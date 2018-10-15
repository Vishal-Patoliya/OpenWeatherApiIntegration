package com.openweatherapp.setting

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.gitprofile.db.AppDatabase
import com.gitprofile.utils.Constants
import com.openweatherapp.R
import com.openweatherapp.utils.PreferencesHelper
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by vishal on 10/14/2018.
 * Use for displaying Setting for unit or resseting preference
 */
class SettingActivity : AppCompatActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        init()
        process()
        setListener()

    }


    private fun init() {

        appDatabase = AppDatabase.getInstance(this)!!

    }


    private fun process() {
        val preferencesHelper = PreferencesHelper(this)

        val unit = preferencesHelper.unit

        when (unit) {

            Constants.APP_PREF.metric -> {
                rbUnitMetric.isChecked = true
            }
            else -> {
                rbUnitImperia.isChecked = true
            }
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            android.R.id.home -> {
                finish()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setListener() {

        tvResetBookMark.setOnClickListener(this)
        rgUnit.setOnCheckedChangeListener(this)

    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.tvResetBookMark -> {
                displayAlert()
            }

        }

    }

    override fun onCheckedChanged(radioGroup: RadioGroup?, checkedId: Int) {

        val checkedRadioButton = radioGroup?.findViewById(checkedId) as RadioButton
        val preferencesHelper = PreferencesHelper(this)

        when (checkedRadioButton.id) {

            R.id.rbUnitMetric -> {
                preferencesHelper.unit = rbUnitMetric.text.toString().toLowerCase()

            }

            R.id.rbUnitImperia -> {
                preferencesHelper.unit = rbUnitImperia.text.toString().toLowerCase()
            }
        }
    }

    /**
     * Dispalying Alert on click of reset bookmark
     */
    private fun displayAlert() {
        // Initialize a new instance of
        val builder = AlertDialog.Builder(this@SettingActivity)

        // Set the alert dialog title
        builder.setTitle(getString(R.string.reset_bookmark_title))

        // Display a message on alert dialog
        builder.setMessage(getString(R.string.reset_bookmark_desc))

        // Set a positive button and its click listener on alert dialog
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            // Do something when user press the positive button

            appDatabase.wheatherAppDao().deleteBookMarks()

            Toast.makeText(applicationContext, getString(R.string.bookmark_completion_msg), Toast.LENGTH_SHORT).show()

        }

        // Display a negative button on alert dialog
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        // Finally, make the alert dialog using builder
        val dialog: AlertDialog = builder.create()

        // Display the alert dialog on app interface
        dialog.show()
    }

}