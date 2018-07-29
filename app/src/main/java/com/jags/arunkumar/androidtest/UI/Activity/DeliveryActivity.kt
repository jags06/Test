package com.jags.arunkumar.androidtest.UI.Activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import com.jags.arunkumar.androidtest.ExtensionPackage.addFragment
import com.jags.arunkumar.androidtest.R
import com.jags.arunkumar.androidtest.UI.Fragment.DeliveryFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class DeliveryActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var deliveryFragment: DeliveryFragment

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delivery_activity)
        if (savedInstanceState != null) {
            return
        }
        addFragment(deliveryFragment, R.id.fragment_container)

    }

    fun setupTitleBar(string: String) {
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title = string
        toolbar.setTitleTextColor(Color.WHITE)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }

    }

}