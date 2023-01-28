package com.gramzin.fitnessclub

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry
import com.gramzin.fitnessclub.databinding.ActivitySavePersonBinding

class SavePersonActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {

    companion object{
        const val MEMBER_CHANGE = 249
    }

    private lateinit var binding: ActivitySavePersonBinding
    private var genderIndex = 0
    private var uriMemberChange: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavePersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.genderSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val genders = this@SavePersonActivity.resources.getStringArray(R.array.genders)
                val gender = parent?.getItemAtPosition(position) as String
                genderIndex = genders.indexOf(gender)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                genderIndex = MemberEntry.GENDER_UNKNOWN
            }
        }

        uriMemberChange = intent.data
        title = if(uriMemberChange == null)
            resources.getString(R.string.add_person)
        else {
            LoaderManager.getInstance(this).initLoader(MEMBER_CHANGE,null, this)
            resources.getString(R.string.change_data)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_person_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId){
            R.id.save_data_menu_item -> {
                saveMember()
                true
            }
            R.id.delete_data_menu_item -> true
            else -> super.onOptionsItemSelected(item)
        }

    private fun saveMember(): Uri?{
        val firstName = binding.firstNameEditText.text.toString().trim()
        val lastName = binding.lastNameEditText.text.toString().trim()
        val group = binding.groupEditText.text.toString().trim()

        val contentValues = ContentValues().apply {
            put(MemberEntry.COLUMN_FIRST_NAME, firstName)
            put(MemberEntry.COLUMN_LAST_NAME, lastName)
            put(MemberEntry.COLUMN_GENDER, genderIndex)
            put(MemberEntry.COLUMN_GROUP, group)
        }
        if (uriMemberChange == null) {
            val uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues)
            if (uri == null) {
                Toast.makeText(this, "An error has occurred", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
            }
            return uri
        }
        else{
            val count = contentResolver.update(uriMemberChange!!, contentValues, null, null)
            if (count == 0) {
                Toast.makeText(this, "An error has occurred", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Data changed", Toast.LENGTH_SHORT).show()
            }
            return uriMemberChange
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(MemberEntry.COLUMN_ID, MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME, MemberEntry.COLUMN_GROUP, MemberEntry.COLUMN_GENDER)
        return CursorLoader(this, uriMemberChange!!, projection,
            null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        with(binding){
            if(data != null && data.moveToFirst()) {
                firstNameEditText.setText(data.getString(data.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRST_NAME)))
                lastNameEditText.setText(data.getString(data.getColumnIndexOrThrow(MemberEntry.COLUMN_LAST_NAME)))
                groupEditText.setText(data.getString(data.getColumnIndexOrThrow(MemberEntry.COLUMN_GROUP)))
                genderSpinner.setSelection(data.getInt(data.getColumnIndexOrThrow(MemberEntry.COLUMN_GENDER)))
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
    }
}
