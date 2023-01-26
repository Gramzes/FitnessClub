package com.gramzin.fitnessclub.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry

class FitnessDBHelper(context: Context?)
    : SQLiteOpenHelper(context, FitnessClubContract.DATABASE_NAME,
    null, FitnessClubContract.DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE ${MemberEntry.TABLE_NAME} " +
                "(${MemberEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${MemberEntry.COLUMN_FIRST_NAME} TEXT," +
                "${MemberEntry.COLUMN_LAST_NAME} TEXT," +
                "${MemberEntry.COLUMN_GENDER} INTEGER NOT NULL," +
                "${MemberEntry.COLUMN_GROUP} TEXT);"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS ${MemberEntry.TABLE_NAME}")
        onCreate(db)
    }
}