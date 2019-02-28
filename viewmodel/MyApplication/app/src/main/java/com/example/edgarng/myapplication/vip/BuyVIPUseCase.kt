package com.example.edgarng.myapplication.vip

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.arch.core.util.Function
import com.example.edgarng.myapplication.data.VIPRepository
import com.example.edgarng.myapplication.util.MediatorUseCase
import com.example.edgarng.myapplication.util.SharePreferencesHelper

class BuyVIPUseCase(private val sharePreferencesHelper: SharePreferencesHelper) : MediatorUseCase<VIPDto, VIPDto>() {
    override fun execute(user: VIPDto) {
        val vipRepositoryData = VIPRepository(sharePreferencesHelper).buyVIP(user)

        val switchMapFunction = Function<VIPDto, LiveData<Result<VIPDto>>> {
            MediatorLiveData<Result<VIPDto>>().apply { value = Result.success(it) }
        }

        result.addSource(
            Transformations.switchMap(
                vipRepositoryData,
                switchMapFunction
            ) as MediatorLiveData<Result<VIPDto>>
        ) {
            result.value = it
        }
    }

}

