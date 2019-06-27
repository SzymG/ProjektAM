package com.example.szymo.ajjkalamba

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    val db = DataBase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        try {
            this.supportActionBar!!.hide()
        } catch (e: Exception) {}

        button_back.setOnClickListener{
            finish()
        }

        editTextLoginReg.setOnClickListener{
            errorTextView.visibility = View.GONE
        }

        editTextHasloReg.setOnClickListener{
            errorTextView.visibility = View.GONE
        }

        editTextHaslo2Reg.setOnClickListener{
            errorTextView.visibility = View.GONE
        }

        button_register.setOnClickListener {
            if(editTextHasloReg.text.toString().length < 8){
                errorTextView.text = "Hasło musi posiadać co najmniej 8 znaków!"
                errorTextView.visibility = View.VISIBLE
            }
            else if(checkTheSame()){
                errorTextView.visibility = View.GONE
                if(editTextLoginReg.text.toString().isBlank()){
                    errorTextView.text = "Login nie może być pusty!"
                    errorTextView.visibility = View.VISIBLE
                }else{
                    if(! db.checkIfLoginExists(editTextLoginReg.text.toString())){
                        db.insertData(editTextLoginReg.text.toString(), editTextHasloReg.text.toString())
                        Toast.makeText(applicationContext,"Rejestracja zakończona pomyślnie",Toast.LENGTH_SHORT).show()
                        finish()
                    }else{
                        errorTextView.text = "Podany login istnieje już w bazie danych"
                        errorTextView.visibility = View.VISIBLE
                    }

                }

            } else {
                errorTextView.text = "Wprowadzone hasła są różne!"
                errorTextView.visibility = View.VISIBLE
            }
        }
    }

    private fun checkTheSame(): Boolean {
        if(editTextHasloReg.text.toString().compareTo(editTextHaslo2Reg.text.toString()) == 0) return true
        return false
    }
}
