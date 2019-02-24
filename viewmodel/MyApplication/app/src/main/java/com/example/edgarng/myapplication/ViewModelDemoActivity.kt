package com.example.edgarng.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class ViewModelDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model_demo)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, ProfileFragment())
        transaction.commit()
    }

    fun addFragment(fragment:Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentLayout, fragment)
        transaction.commit()
    }
}
