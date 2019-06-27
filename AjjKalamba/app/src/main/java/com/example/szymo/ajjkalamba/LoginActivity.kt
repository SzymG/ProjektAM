package com.example.szymo.ajjkalamba

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val db = DataBase(this)
    var login = ""
    var zalogowany = false

    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            this.supportActionBar!!.hide()
        } catch (e: Exception) {}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_log.setOnClickListener{
            if(inDatabase(editTextLogin.text.toString(), editTextHaslo.text.toString())){
                login = editTextLogin.text.toString()
                zalogowany = true
                setLogged()
                setLogin()
                val changePage = Intent(this, MainActivity::class.java)
                startActivity(changePage)
                finish()
            } else{
                errorTextView1.text = "Brak loginu i hasła w bazie danych"
                errorTextView1.visibility = View.VISIBLE
            }
        }
        editTextLogin.setOnClickListener{
            errorTextView1.visibility = View.GONE
        }
        editTextHaslo.setOnClickListener{
            errorTextView1.visibility = View.GONE
        }

        button_reg.setOnClickListener{
            val changePage = Intent(this, RegisterActivity::class.java)
            startActivity(changePage)
        }
    }

    private fun setLogged(){

        val loginShared = getSharedPreferences("com.example.szymo.ajjkalamba.prefs",0)
        val editor = loginShared!!.edit()
        editor.putBoolean("logged",zalogowany)
        editor.apply()
    }

    private fun setLogin(){

        val loginShared = getSharedPreferences("com.example.szymo.ajjkalamba.prefs",0)
        val editor = loginShared!!.edit()
        editor.putString("login",login)
        editor.apply()
    }

    private fun inDatabase(login: String, haslo: String): Boolean {
        val ifEx = db.checkIfExists(login, haslo)
        return ifEx
    }
}
