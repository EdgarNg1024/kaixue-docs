package com.example.edgarng.myapplication.data

import androidx.lifecycle.MutableLiveData
import com.example.edgarng.myapplication.util.SharePreferencesHelper
import com.example.edgarng.myapplication.vip.VIPDto
import kotlinx.coroutines.*
import java.util.*

class VIPRepository(private val sharePreferencesHelper: SharePreferencesHelper) {

    fun buyVIP(user: VIPDto): MutableLiveData<VIPDto> {
        val result = MutableLiveData<VIPDto>()

        //假装这里是异步赋值的
        val calendar = Calendar.getInstance()
        calendar.time = user.deadlineDate
        calendar.add(Calendar.MONTH, 1)

        val deadLineDate = calendar.time.toString()
        val userName = user.userName

        val contentValue = SharePreferencesHelper.ContentValue("deadLineDate", deadLineDate)
        val contentValue2 = SharePreferencesHelper.ContentValue("userName", userName)
        sharePreferencesHelper.putValues(contentValue, contentValue2)

        result.value = VIPDto(userName, Date(deadLineDate))
        //假装这里是异步赋值的

        return result
    }

    fun getVIP(): MutableLiveData<VIPDto> {
        val deadLineDate = sharePreferencesHelper.getString("deadLineDate") ?: Date().toString()
        val userName = sharePreferencesHelper.getString("userName") ?: "EdgarNg"
        val result = MutableLiveData<VIPDto>()
        //假装这里是异步赋值的

        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                delay(5000)
                async(Dispatchers.Main) {
                    result.value = VIPDto(userName, Date(deadLineDate))
                }
            }.await()
        }

        return result
    }
}