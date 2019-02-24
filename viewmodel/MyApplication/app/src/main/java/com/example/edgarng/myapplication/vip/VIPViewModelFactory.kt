package com.example.edgarng.myapplication.vip

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VIPViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VIPViewModel().also { it.getData() } as T
    }

}