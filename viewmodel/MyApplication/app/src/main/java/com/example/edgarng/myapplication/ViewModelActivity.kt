package com.example.edgarng.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_showdata.*

class ViewModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showdata)
        txtTips.text = "翻转屏幕，数据不会消失"
        val myViewModel = ViewModelProviders.of(this).get(myViewModel::class.java)
        initView(myViewModel)

        btnAdd.setOnClickListener {
            setShowText(++myViewModel.count)
        }
    }


    private fun initView(viewModel: myViewModel) {
        setShowText(viewModel.count)
    }

    private fun setShowText(count: Int) {
        txtShow.text = "点击按钮 $count 次"
    }
}

class myViewModel : ViewModel() {
    var count = 0
}
