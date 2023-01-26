package com.gramzin.fitnessclub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gramzin.fitnessclub.databinding.ActivityAddPersonBinding

class AddPersonActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}