package com.example.whatsappstatussaver

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.whatsappstatussaver.fragments.SavedStatus
import com.example.whatsappstatussaver.fragments.SettingFrag
import com.example.whatsappstatussaver.fragments.Status
import com.example.whatsappstatussaver.utils.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >23){

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE )== PackageManager.PERMISSION_GRANTED){
                setupLayout()
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
                checkPermission()
            }
        }
        else{
            setupLayout()
        }
    }

    private fun setupLayout(){
        val statusFragment = Status()
        val savedStatusFragment = SavedStatus()
        val settingsFragment = SettingFrag()

        setCurrentFragment(statusFragment)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navStatus -> setCurrentFragment(statusFragment)
                R.id.navSaved -> setCurrentFragment(savedStatusFragment)
                R.id.navSetting -> setCurrentFragment(settingsFragment).addToBackStack(null)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }


}