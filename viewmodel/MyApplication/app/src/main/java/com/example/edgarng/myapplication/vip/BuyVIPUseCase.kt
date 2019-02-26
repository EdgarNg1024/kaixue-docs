package com.example.edgarng.myapplication.vip

import androidx.lifecycle.Transformations
import com.example.edgarng.myapplication.data.VIPRepository
import com.example.edgarng.myapplication.util.MediatorUseCase
import com.example.edgarng.myapplication.util.SharePreferencesHelper

class BuyVIPUseCase(private val sharePreferencesHelper: SharePreferencesHelper) : MediatorUseCase<VIPDto, VIPDto>() {
    override fun execute(user: VIPDto) {
        Transformations.switchMap(
            VIPRepository(sharePreferencesHelper).buyVIP(user)
        ) {
            result.value = Result.success(it)
            result
        }
    }

}

