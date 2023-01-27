package com.gramzin.fitnessclub

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CursorAdapter
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
        val adapter = MemberCursorAdapter(this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        binding.membersList.adapter = adapter
    }
}