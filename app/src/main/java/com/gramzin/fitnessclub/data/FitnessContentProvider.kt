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
        val cursor = when(uriMatcher.match(uri)){
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
        cursor.setNotificationUri(context?.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri {
        if (!contentValues!!.containsKey(MemberEntry.COLUMN_FIRST_NAME))
            throw IllegalArgumentException("You have to pass the first name to ContentValues")
        if (!contentValues!!.containsKey(MemberEntry.COLUMN_LAST_NAME))
            throw IllegalArgumentException("You have to pass the last name to ContentValues")
        if (!contentValues!!.containsKey(MemberEntry.COLUMN_GROUP))
            throw IllegalArgumentException("You have to pass the group to ContentValues")
        val gender = contentValues.getAsInteger(MemberEntry.COLUMN_GENDER)
        if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN ||
                    gender == MemberEntry.GENDER_FEMALE || gender == MemberEntry.GENDER_MALE))
            throw IllegalArgumentException("The gender parameter is not specified " +
                    "in ContentValues or is specified incorrectly")

        val db = dbOpenHelper.writableDatabase

        when(uriMatcher.match(uri)){
            MEMBERS ->{
                val id = db.insert(MemberEntry.TABLE_NAME, null, contentValues)
                if (id != -1L){
                    context?.contentResolver?.notifyChange(uri, null)
                    return ContentUris.withAppendedId(uri, id)
                }
                else
                    throw IllegalArgumentException("Insertion of data in the table failed for $uri")
            }
            else -> throw IllegalArgumentException("Insertion of data in the table failed for $uri")
        }


    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper.writableDatabase
        var count = when(uriMatcher.match(uri)){
            MEMBERS -> {
                 db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs)
            }
            MEMBER_ID ->{
                val id = ContentUris.parseId(uri).toString()
                db.delete(MemberEntry.TABLE_NAME, "${MemberEntry.COLUMN_ID} =?", arrayOf(id))
            }
            else ->{
                throw IllegalArgumentException("Can't delete incorrect Uri: $uri")
            }
        }
        if (count > 0)
            context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun update(uri: Uri, contentValues: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        if (contentValues!!.containsKey(MemberEntry.COLUMN_GENDER)) {
            val gender = contentValues?.getAsInteger(MemberEntry.COLUMN_GENDER)
            if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN ||
                        gender == MemberEntry.GENDER_FEMALE || gender == MemberEntry.GENDER_MALE))
                throw IllegalArgumentException("The gender parameter in ContentValues is specified incorrectly")
        }
        val db = dbOpenHelper.writableDatabase
        var count = when(uriMatcher.match(uri)){
            MEMBERS -> {
                db.update(MemberEntry.TABLE_NAME, contentValues, selection, selectionArgs)
            }
            MEMBER_ID ->{
                val id = ContentUris.parseId(uri).toString()
                db.update(MemberEntry.TABLE_NAME, contentValues,
                    "${MemberEntry.COLUMN_ID} =?", arrayOf(id))

            }
            else ->{
                throw IllegalArgumentException("Can't update incorrect Uri: $uri")
            }
        }
        if (count > 0)
            context?.contentResolver?.notifyChange(uri, null)
        return count
    }

    override fun getType(uri: Uri): String? {
        return when(uriMatcher.match(uri)){
            MEMBERS -> MemberEntry.MIME_MULTIPLIE_ITEMS
            MEMBER_ID -> MemberEntry.MIME_SINGLE_ITEM
            else ->{
                throw IllegalArgumentException("Unknown Uri: $uri")
            }
        }
    }
}