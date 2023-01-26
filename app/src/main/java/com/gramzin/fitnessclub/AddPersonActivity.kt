package com.gramzin.fitnessclub

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry
import com.gramzin.fitnessclub.databinding.ActivityAddPersonBinding

class AddPersonActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddPersonBinding
    private var genderIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.genderSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val genders = this@AddPersonActivity.resources.getStringArray(R.array.genders)
                val gender = parent?.getItemAtPosition(position) as String
                genderIndex = genders.indexOf(gender)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                genderIndex = MemberEntry.GENDER_UNKNOWN
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_person_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when(item.itemId){
            R.id.save_data_menu_item -> {
                insertMember()
                true
            }
            R.id.delete_data_menu_item -> true
            else -> super.onOptionsItemSelected(item)
        }


    fun insertMember(): Uri?{
        val firstName = binding.firstNameEditText.text.toString().trim()
        val lastName = binding.lastNameEditText.text.toString().trim()
        val group = binding.groupEditText.text.toString().trim()

        val contentValues = ContentValues().apply {
            put(MemberEntry.COLUMN_FIRST_NAME, firstName)
            put(MemberEntry.COLUMN_LAST_NAME, lastName)
            put(MemberEntry.COLUMN_GENDER, genderIndex)
            put(MemberEntry.COLUMN_GROUP, group)
        }

        val uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues)
        if (uri == null){
            Toast.makeText(this, "An error has occurred", Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        }
        return uri
    }
}
