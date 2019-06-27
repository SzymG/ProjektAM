package com.example.szymo.ajjkalamba

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

val DB_NAME = "MyWordsDb"
val TB_NAME = "Words"
val COL_I = "id"
val COL_LOG = "login"
val COL_CAT = "kategoria"
val COL_WORD = "word"

class WordsDataBase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TB_NAME+" ("+
                COL_I + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LOG + " VARCHAR(256)," +
                COL_CAT + " VARCHAR(256)," +
                COL_WORD + " VARCHAR(256));"
        db?.execSQL(createTable)
        print("DATABASE CREATED")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(db)
    }

    fun insertData(login: String, kategoria: String, haslo: String): Long {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_LOG, login)
        cv.put(COL_CAT, kategoria)
        cv.put(COL_WORD, haslo)
        var result = db.insert(TB_NAME, null, cv)
        return result
    }

    fun dropTable(){
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS " + TB_NAME)
        onCreate(db)
    }

    fun deleteData(login: String, kategoria:String, haslo: String){
        val db = this.writableDatabase
        db?.execSQL("DELETE FROM " + TB_NAME + " WHERE "
                +COL_LOG+ " = \"" + login + "\""+
                " AND " + COL_CAT + " = \"" + kategoria +"\"" +
                " AND " + COL_WORD + " = \"" + haslo +"\""
        )
    }

    fun checkIfExists(login: String, kategoria: String, haslo:String): Boolean {

        val db = this.writableDatabase

        val selectALLQuery = "SELECT * FROM " + TB_NAME + " WHERE " + COL_LOG+ " = \"" + login + "\"" +
                " AND " + COL_CAT + " = \"" + kategoria +"\""+
               " AND " + COL_WORD + " = \"" + haslo +"\""

        val cursor = db.rawQuery(selectALLQuery, null)


        if(cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun readFromDB(login: String): MutableList<String> {
        var rankList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM " + TB_NAME + " WHERE " + COL_LOG+ " = \"" + login + "\""
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                rankList.add(result.getString(result.getColumnIndex(COL_CAT)))
                rankList.add(result.getString(result.getColumnIndex(COL_WORD)))
            }while (result.moveToNext())
        }
        return rankList
    }
}