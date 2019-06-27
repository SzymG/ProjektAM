package com.example.szymo.ajjkalamba

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var login = ""
    var list: ArrayList<String> = ArrayList()
    val db = WordsDataBase(this)
    var needToUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            this.supportActionBar!!.hide()
        } catch (e: Exception) {}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLogin()
        getData()

        loginTextView.text = "WITAJ $login!"

        buttonLogout.setOnClickListener{
            val changePage = Intent(this, LoginActivity::class.java)
            startActivity(changePage)
            setNotLogged()
            finish()
        }
        buttonSettings.setOnClickListener{
            needToUpdate = true
            val changePage = Intent(this, SettingsActivity::class.java)
            startActivity(changePage)
        }

        buttonDraw.setOnClickListener {
            if(needToUpdate){
                getData()
                needToUpdate = false
            }
            val random = Random()
            val i = 2*random.nextInt(list.count()/2)
            textViewKat.text = list[i].toUpperCase()
            hasloTextView.text = list[i+1].toUpperCase()
        }
    }

    private fun setNotLogged(){

        val loginShared = getSharedPreferences("com.example.szymo.ajjkalamba.prefs",0)
        val editor = loginShared!!.edit()
        editor.putBoolean("logged",false)
        editor.apply()
    }

    private fun getLogin(){
        val loginShared = getSharedPreferences("com.example.szymo.ajjkalamba.prefs",0)
        login = loginShared.getString("login", "").toUpperCase()
    }

    private fun getData() {
        val wordList = db.readFromDB(login)
        this.list = ArrayList()
        for (x in 0 until wordList.count()){
            list.add(wordList[x])
        }
    }
}
