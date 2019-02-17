package com.example.edgarng.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_showdata.*

class NoViewModelActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdata)
        txtTips.text = "翻转屏幕，数据会消失"
        Log.d("tag", "count----> $count")
        setShowText(count)
        btnAdd.setOnClickListener {
            setShowText(++count)
        }
    }

    private fun setShowText(count: Int) {
        txtShow.text = "点击按钮 $count 次"
    }
}
