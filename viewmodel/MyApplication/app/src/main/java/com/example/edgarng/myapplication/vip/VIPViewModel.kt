package com.example.edgarng.myapplication.vip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class VIPViewModel : ViewModel {

    lateinit var deadLineDate: Date
    lateinit var userName: String

    constructor()
    constructor(userName: String, deadLineDate: Date) {
        this.userName = userName
        this.deadLineDate = deadLineDate
    }

    private val _VIPAction = MutableLiveData<VIPViewModel>()
    val vipAction: LiveData<VIPViewModel>
        get() = _VIPAction

    fun getData() {
        this.userName = "Edgar Ng"
        this.deadLineDate = Date()
        _VIPAction.value = this
    }

    fun buyVIP() {
        val calendar = Calendar.getInstance()
        calendar.time = this.deadLineDate
        calendar.add(Calendar.MONTH, 1)
        this.deadLineDate = calendar.time
        _VIPAction.value = this
    }

}