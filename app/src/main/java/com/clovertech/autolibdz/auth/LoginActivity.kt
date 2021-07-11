package com.clovertech.autolibdz.auth

import com.clovertech.autolibdz.ViewModel.MainViewModelFactoryCard
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.clovertech.autolibdz.FindYourCarActivity
import com.clovertech.autolibdz.HomeActivity
import com.clovertech.autolibdz.MainActivity
import com.clovertech.autolibdz.R
import com.clovertech.autolibdz.ViewModel.MainViewModel
import com.clovertech.autolibdz.ViewModel.MainViewModelFactory
import com.clovertech.autolibdz.password.ResetPasswordActivity
import kotlinx.android.synthetic.main.activity_login.*
import model.Authentication
import repository.Repository


class LoginActivity : AppCompatActivity() , View.OnClickListener {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val preferences: SharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
        val loggedIn =  preferences.getBoolean("LoggedIn",false)

        var toMain = Intent(this@LoginActivity, MainActivity::class.java)
        toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        if(loggedIn) {
            println("loggedin")

            toMain = Intent(this@LoginActivity, HomeActivity::class.java)
        }
        toMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)


        login_btn.setOnClickListener(this)
        forgotPassword_txt_view.setOnClickListener(this)
        register_txt_view.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.login_btn -> {
                login()
            }
            R.id.forgotPassword_txt_view -> {
                startActivity(Intent(this, ResetPasswordActivity::class.java))
            }
            R.id.register_txt_view -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
            else -> {}
        }
    }

    private fun login(){
        /// Authentification Api
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(MainViewModel::class.java)

        //startActivity(Intent(this, HomeActivity::class.java))
        if (email_edit_txt.text.toString() == ""){
            email_edit_txt.setError("Email required !")
        }else if (password_edit_txt.text.toString() == ""){
            password_edit_txt.setError("Password required !")
        } else {
            var authentication = Authentication(email_edit_txt.text.toString(), password_edit_txt.text.toString())
            viewModel.pushAuthentication(authentication)
            viewModel.authenticationResponse.observe(this, Observer { response ->
                if (response.isSuccessful) {
                    Toast.makeText(this, "SignIn Successfully", Toast.LENGTH_SHORT).show()
                    val idUser=response.body()?.id
                    val token =response.body()?.token.toString()
                    if (idUser != null) {
                        saveUserToken(token,idUser)
                    }
                    startActivity(Intent(this, HomeActivity::class.java))
                    val preferences: SharedPreferences = getSharedPreferences("LoggedIn", Context.MODE_PRIVATE)
                    preferences.edit().putBoolean("LoggedIn", true).apply()
                    Log.e("Push", response.body()?.id.toString())
                    Log.e("Push", response.body().toString())
                    Log.e("Push", response.code().toString())
                    Log.e("Push", response.message())

                } else {
                    Toast.makeText(this, "Login failed !!! ${response.message()}", Toast.LENGTH_SHORT).show()
                    Log.e("Push", response.body().toString())
                    Log.e("Push", response.code().toString())
                    Log.e("Push", response.message())
                }
            })
        }
    }

    private fun saveUserToken(token: String,idUser:Int){
        val preferences: SharedPreferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        preferences.edit().putString("TOKEN", token).apply()
        preferences.edit().putInt("IDUSER", idUser).apply()


        /// Retrive Saved TOKEN
        //val retrivedToken = preferences.getString("TOKEN", null) //second parameter default value.
    }

}