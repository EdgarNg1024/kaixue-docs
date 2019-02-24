package com.example.edgarng.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ViewModelDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_model_demo)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, ProfileFragment())
        transaction.commit()
    }

    fun addFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragmentLayout, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentByTag(BuyVIPFragment::class.java.simpleName)
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(fragment)
            transaction.commit()
        } else {
            super.onBackPressed()
        }

    }
}
