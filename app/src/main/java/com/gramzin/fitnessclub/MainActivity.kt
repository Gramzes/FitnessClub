package com.gramzin.fitnessclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gramzin.fitnessclub.databinding.ActivityMainBinding
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addPersonBtn.setOnClickListener{
            val intent = Intent(this, AddPersonActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        displayData()
    }

    fun displayData(){
        val projection = arrayOf(MemberEntry.COLUMN_ID, MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME, MemberEntry.COLUMN_GROUP, MemberEntry.COLUMN_GENDER)
        val cursor = contentResolver.query(MemberEntry.CONTENT_URI, projection,
            null, null, null)

        binding.textViewMembers.text = "All members:\n\n"
        binding.textViewMembers.append(projection.joinToString(separator = "  ", postfix = "\n"))

        val idColumnIndex = cursor?.getColumnIndexOrThrow(MemberEntry.COLUMN_ID)
        val firstNameColumnIndex = cursor?.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRST_NAME)
        val lastNameColumnIndex = cursor?.getColumnIndexOrThrow(MemberEntry.COLUMN_LAST_NAME)
        val groupColumnIndex = cursor?.getColumnIndexOrThrow(MemberEntry.COLUMN_GROUP)
        val genderColumnIndex = cursor?.getColumnIndexOrThrow(MemberEntry.COLUMN_GENDER)

        while (cursor!=null && cursor?.moveToNext()){
            val id = cursor.getInt(idColumnIndex!!)
            val firstName = cursor.getString(firstNameColumnIndex!!)
            val lastName = cursor.getString(lastNameColumnIndex!!)
            val group = cursor.getString(groupColumnIndex!!)
            val gender = cursor.getString(genderColumnIndex!!)

            binding.textViewMembers.append("$id $firstName $lastName $group $gender\n")
        }
    }
}