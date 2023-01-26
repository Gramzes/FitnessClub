package com.gramzin.fitnessclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.save_data_menu_item -> return true
            R.id.delete_data_menu_item -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
