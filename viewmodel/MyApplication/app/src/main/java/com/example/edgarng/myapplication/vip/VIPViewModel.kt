package com.example.edgarng.myapplication.vip

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.edgarng.myapplication.util.SharePreferencesHelper

class VIPViewModel(application: Application) : AndroidViewModel(application) {

    private val _VIPAction = MediatorLiveData<VIPDto>()
    val vipAction: LiveData<VIPDto>
        get() = _VIPAction

    fun getData() {
        val useCase = GetVIPUseCase(SharePreferencesHelper(getApplication(), "VIP"))
        _VIPAction.addSource(useCase.observe()) {
            if (it.isSuccess) {
                _VIPAction.value = it.getOrThrow()
            }
        }
        useCase.execute(Unit)
    }

    fun buyVIP() {
        val useCase = BuyVIPUseCase(SharePreferencesHelper(getApplication(), "VIP"))
        _VIPAction.addSource(useCase.observe()) {
            if (it.isSuccess) {
                _VIPAction.value = it.getOrThrow()
            }
        }
        _VIPAction.value?.let { useCase.execute(it) }

    }

}