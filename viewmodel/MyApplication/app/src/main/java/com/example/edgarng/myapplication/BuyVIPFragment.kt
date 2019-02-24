package com.example.edgarng.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.edgarng.myapplication.vip.VIPViewModel
import com.example.edgarng.myapplication.vip.VIPViewModelFactory
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat


class BuyVIPFragment : Fragment() {

    private lateinit var vipViewModel: VIPViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buy_vip, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        vipViewModel = ViewModelProviders.of(activity!!, VIPViewModelFactory()).get(VIPViewModel::class.java)
        vipViewModel.vipAction.observe(activity!!, Observer<VIPViewModel> {
            txtUserName?.text = it.userName
            txtDeadlineDate?.text = SimpleDateFormat("yyyy-MM-dd").format(it.deadLineDate)
        })

        btnBuyVIP.setOnClickListener {
            vipViewModel.buyVIP()
        }
    }
}
