package com.gramzin.fitnessclub

import android.content.ContentUris
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.gramzin.fitnessclub.databinding.ActivityMainBinding
import com.gramzin.fitnessclub.data.FitnessClubContract.MemberEntry

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor>{

    companion object{
        const val MEMBER_LOADER = 249
    }
    lateinit var binding: ActivityMainBinding
    lateinit var memberCursorAdapter: MemberCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addPersonBtn.setOnClickListener{
            val intent = Intent(this, SavePersonActivity::class.java)
            startActivity(intent)
        }
        memberCursorAdapter  = MemberCursorAdapter(this, null, 0)
        binding.membersList.adapter = memberCursorAdapter
        LoaderManager.getInstance(this).initLoader(MEMBER_LOADER,null, this)

        binding.membersList.setOnItemClickListener { adapterView, view, position, id->
            Intent(this, SavePersonActivity::class.java).apply {
                data = ContentUris.withAppendedId(MemberEntry.CONTENT_URI, id)
                startActivity(this)
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf(MemberEntry.COLUMN_ID, MemberEntry.COLUMN_FIRST_NAME,
            MemberEntry.COLUMN_LAST_NAME, MemberEntry.COLUMN_GROUP, MemberEntry.COLUMN_GENDER)
        return CursorLoader(this, MemberEntry.CONTENT_URI, projection,
            null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        memberCursorAdapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        memberCursorAdapter.swapCursor(null)
    }
}