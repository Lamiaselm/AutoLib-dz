package com.clovertech.autolibdz

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.clovertech.autolibdz.auth.LoginActivity
import com.clovertech.autolibdz.auth.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        println("first run")
        editor.putBoolean("FirstRun", false)
        editor.apply()
        val preferences: SharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
        val loggedIn =  preferences.getBoolean("LoggedIn",false)
        println("mainhere")

        var toMain = Intent(this@MainActivity, LoginActivity::class.java)
        toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if(loggedIn) {
            println("loggedin")

            toMain = Intent(this@MainActivity, HomeActivity::class.java)
        }
        toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        signIn_btn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signUp_btn.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

}