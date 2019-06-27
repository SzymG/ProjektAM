package com.example.szymo.ajjkalamba

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DATABASE_NAME = "MyUsersDb"
val TABLE_NAME = "Users1"
val COL_ID = "id"
val COL_LOGIN = "login"
val COL_PASSWD = "password"

class DataBase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME+" ("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_LOGIN + " VARCHAR(256)," +
                COL_PASSWD + " VARCHAR(256));"
        db?.execSQL(createTable)
        print("DATABASE CREATED")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db)
    }

    fun insertData(login: String, haslo: String): Long {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_LOGIN, login)
        cv.put(COL_PASSWD, haslo)
        var result = db.insert(TABLE_NAME, null, cv)
        return result
    }

    fun dropTable(){
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun readFromDB(): MutableList<String> {
        var rankList: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM "+ TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                rankList.add(result.getString(result.getColumnIndex(COL_LOGIN)))
                rankList.add(result.getString(result.getColumnIndex(COL_PASSWD)))

            }while (result.moveToNext())
        }
        return rankList
    }

    fun checkIfExists(login: String, haslo: String): Boolean {

        val db = this.writableDatabase
        val selectALLQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_LOGIN + " = \"" + login +
                "\" AND " + COL_PASSWD + " = \"" + haslo +"\""
        val cursor = db.rawQuery(selectALLQuery, null)
        if(cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun checkIfLoginExists(login: String): Boolean {

        val db = this.writableDatabase
        val selectALLQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " +
                COL_LOGIN + " = \"" + login + "\""
        val cursor = db.rawQuery(selectALLQuery, null)
        if(cursor.count <= 0){
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }
}