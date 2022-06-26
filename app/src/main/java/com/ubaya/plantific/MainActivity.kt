package com.ubaya.plantific

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    val pref = "userSession"
    val key_name = "key.name"
    val key_id="key.id"
    lateinit var sharedPref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = getSharedPreferences(pref, Context.MODE_PRIVATE)
        if(sharedPref.getString(key_name,"") == ""){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        sharedPref.getString(key_name,"")?.let { Log.d("cekparams", it) }
        sharedPref.getString(key_id,"")?.let { Log.d("cekparams", it) }
        setSupportActionBar(toolbar)
        bottomNav.setBackgroundColor(
            ContextCompat.getColor(
                applicationContext,
                android.R.color.transparent
            )
        )
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        bottomNav.setupWithNavController(navController)
        fabScan.setOnClickListener(){
            val action = HomeFragmentDirections.actToScan()
            navController.navigate(action)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}