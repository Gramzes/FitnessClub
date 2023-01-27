package com.gramzin.fitnessclub.data

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

object FitnessClubContract {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "fitnessClub"
    const val SCHEME = "content://"
    const val AUTHORITY = "com.gramzin.fitnessclub"
    const val PATH_MEMBERS= "data/members"

    val BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY)

    object MemberEntry: BaseColumns{
        const val TABLE_NAME = "members"

        const val COLUMN_ID = BaseColumns._ID
        const val COLUMN_FIRST_NAME = "firstName"
        const val COLUMN_LAST_NAME = "lastName"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_GROUP ="group_"

        const val GENDER_UNKNOWN = 0
        const val GENDER_MALE = 1
        const val GENDER_FEMALE = 2

        val CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS)

        val MIME_MULTIPLIE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTHORITY + "/" + PATH_MEMBERS
        val MIME_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE +"/vnd." + AUTHORITY + "/" + PATH_MEMBERS
    }
}