package com.example.edgarng.myapplication.vip

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.edgarng.myapplication.data.VIPRepository
import com.example.edgarng.myapplication.util.MediatorUseCase
import com.example.edgarng.myapplication.util.SharePreferencesHelper

class GetVIPUseCase(private val sharePreferencesHelper: SharePreferencesHelper) : MediatorUseCase<Unit, VIPDto>() {
    override fun execute(parameters: Unit) {

        val vipRepositoryData = VIPRepository(sharePreferencesHelper).getVIP()

        val switchMapFunction = Function<VIPDto, LiveData<Result<VIPDto>>> {
            result.value = Result.success(it)
            result
        }

        Transformations.switchMap(vipRepositoryData, switchMapFunction)
    }
}