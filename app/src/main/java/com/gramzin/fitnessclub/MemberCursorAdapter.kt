package com.gramzin.fitnessclub

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry
import com.gramzin.fitnessclub.databinding.MemberHolderBinding

class MemberCursorAdapter(context: Context?, c: Cursor?, flags: Int) :
    CursorAdapter(context, c, flags) {

    lateinit var binding: MemberHolderBinding

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        binding = MemberHolderBinding.inflate(LayoutInflater.from(context), parent, false)
        return binding.root
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        with(binding) {
            firstNameText.text =
                cursor?.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRST_NAME))
            lastNameText.text =
                cursor?.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_LAST_NAME))
            groupText.text =
                cursor?.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_GROUP))
        }
    }
}