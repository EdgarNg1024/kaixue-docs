package com.example.edgarng.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNoViewModel.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, NoViewModelActivity::class.java)
            startActivity(intent)
        }

        btnViewmodel.setOnClickListener {
            val intent = Intent()
            intent.setClass(this, ViewModelActivity::class.java)
            startActivity(intent)
        }
    }
}
