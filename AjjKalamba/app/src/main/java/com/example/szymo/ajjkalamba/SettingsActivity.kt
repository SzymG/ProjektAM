package com.example.szymo.ajjkalamba

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var wordList: MutableList<String> =  ArrayList()
    var list: ArrayList<String> = ArrayList()
    val db = WordsDataBase(this)
    var login = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        try {
            this.supportActionBar!!.hide()
        } catch (e: Exception) {}

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        getLogin()
        getData()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list)
        listView.adapter = adapter

        buttonPowrot.setOnClickListener{
            finish()
        }

        buttonAdd.setOnClickListener {
            if(editKategoria.text.toString().compareTo("") == 0 ||
                editHaslo.text.toString().compareTo("") == 0){
                Toast.makeText(applicationContext,"Kategoria i Hasło nie mogą być puste!", Toast.LENGTH_SHORT).show()
            }else{
                val bT = BackgroundTask(this, adapter, list)
                bT.execute(login, editKategoria.text.toString(), editHaslo.text.toString())
            }
        }

        buttonDelete.setOnClickListener {
            if (editKategoria.text.toString().compareTo("") == 0 ||
                editHaslo.text.toString().compareTo("") == 0
            ) {
                Toast.makeText(applicationContext, "Kategoria i Hasło nie mogą być puste!", Toast.LENGTH_SHORT).show()
            } else {
                if (db.checkIfExists(login, editKategoria.text.toString(), editHaslo.text.toString())) {
                    val builder = AlertDialog.Builder(this@SettingsActivity)
                    builder.setTitle("Czy na pewno chcesz usunąć to hasło ?")
                    builder.setMessage(
                        "Kategoria: " + editKategoria.text.toString() + "\n" +
                                "Hasło: " + editHaslo.text.toString()
                    )
                    builder.setPositiveButton("Tak") { dialog, which ->
                        deleteWord(editKategoria.text.toString(), editHaslo.text.toString())
                        list.remove(editKategoria.text.toString() + ": " + editHaslo.text.toString())
                        adapter.notifyDataSetChanged()
                    }
                    builder.setNegativeButton("Nie") { dialog, which ->
                        dialog.cancel()
                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                } else {
                    Toast.makeText(applicationContext, "Podane hasło nie istnieje w bazie danych", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun getData() {
        this.wordList = db.readFromDB(login)
        this.list = ArrayList()
        for (x in 0 until wordList.count() step 2){
            list.add(wordList[x]+": "+wordList[x+1])
        }
    }

    private fun deleteWord(kategoria: String, haslo: String) {
        db.deleteData(login, kategoria, haslo)
        Toast.makeText(applicationContext,"Pomyślnie usunięto hasło", Toast.LENGTH_SHORT).show()
    }

    private fun getLogin(){
        val loginShared = getSharedPreferences("com.example.szymo.ajjkalamba.prefs",0)
        login = loginShared.getString("login", "").toUpperCase()
    }

}
