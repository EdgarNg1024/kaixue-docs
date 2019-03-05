package com.example.edgarng.myapplication.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.edgarng.myapplication.util.SharePreferencesHelper
import com.example.edgarng.myapplication.vip.VIPDto
import kotlinx.coroutines.*
import java.util.*

class VIPRepository(private val sharePreferencesHelper: SharePreferencesHelper) {

    fun buyVIP(user: VIPDto): LiveData<VIPDto> {
        val result = MutableLiveData<VIPDto>()

        //异步赋值的
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                delay(1000)
                val calendar = Calendar.getInstance()
                calendar.time = user.deadlineDate
                calendar.add(Calendar.MONTH, 1)

                val deadLineDate = calendar.time.toString()
                val userName = user.userName

                val contentValue = SharePreferencesHelper.ContentValue("deadLineDate", deadLineDate)
                val contentValue2 = SharePreferencesHelper.ContentValue("userName", userName)
                sharePreferencesHelper.putValues(contentValue, contentValue2)
                async(Dispatchers.Main) {
                    result.value = VIPDto(userName, Date(deadLineDate))
                }
            }.await()
        }

        return result
    }

    fun getVIP(): LiveData<VIPDto> {

        val result = MutableLiveData<VIPDto>()
        //异步赋值的
        GlobalScope.launch(Dispatchers.Main) {
            async(Dispatchers.IO) {
                delay(1500)
                val deadLineDate = sharePreferencesHelper.getString("deadLineDate") ?: Date().toString()
                val userName = sharePreferencesHelper.getString("userName") ?: "EdgarNg"
                async(Dispatchers.Main) {
                    result.value = VIPDto(userName, Date(deadLineDate))
                }
            }.await()
        }

        return result
    }
}