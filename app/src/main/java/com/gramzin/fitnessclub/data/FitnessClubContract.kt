package com.gramzin.fitnessclub.data

import android.provider.BaseColumns

object FitnessClubContract {
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "fitnessClub"

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
    }
}