package com.bytedance.jstu.homework

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBOpenHelper(private val context: Context, name: String, version: Int): SQLiteOpenHelper(context, name, null, version) {

    companion object {
        private const val DBTableCreation =
            "create table todolist("+
                    "id integer primary key autoincrement," +
                    "description text," +
                    "status text" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBTableCreation)
        Log.d("DBOpenHelper", "Finish creating table")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //
    }

}