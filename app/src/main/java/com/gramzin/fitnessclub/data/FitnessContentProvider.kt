package com.gramzin.fitnessclub.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry
import java.lang.IllegalArgumentException

class FitnessContentProvider: ContentProvider() {
    companion object{
        private const val MEMBERS = 1
        private const val MEMBER_ID = 2
    }

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(FitnessClubContract.AUTHORITY, FitnessClubContract.PATH_MEMBERS, MEMBERS)
        addURI(FitnessClubContract.AUTHORITY, FitnessClubContract.PATH_MEMBERS + "/#", MEMBER_ID)
    }

    private lateinit var dbOpenHelper: FitnessDBHelper

    override fun onCreate(): Boolean {
        dbOpenHelper = FitnessDBHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper.readableDatabase
        return when(uriMatcher.match(uri)){
            MEMBERS -> db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder)
            MEMBER_ID ->{
                val id = ContentUris.parseId(uri).toString()
                db.query(MemberEntry.TABLE_NAME, projection, "${MemberEntry.COLUMN_ID} =?",
                    arrayOf(id), null, null, sortOrder)
            }
            else ->{
                throw IllegalArgumentException("Can't query incorrect Uri: $uri")
            }
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }
}