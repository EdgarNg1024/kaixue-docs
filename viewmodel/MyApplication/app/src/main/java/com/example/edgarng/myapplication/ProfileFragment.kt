package com.example.edgarng.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.edgarng.myapplication.vip.VIPDto
import com.example.edgarng.myapplication.vip.VIPViewModel
import com.example.edgarng.myapplication.vip.VIPViewModelFactory
import kotlinx.android.synthetic.main.fragment_profile.*
import java.text.SimpleDateFormat


class ProfileFragment : Fragment() {

    private lateinit var vipViewModel: VIPViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { activity ->
            vipViewModel =
                ViewModelProviders.of(activity, VIPViewModelFactory(activity.application!!))
                    .get(VIPViewModel::class.java)
            vipViewModel.vipAction.observe(this, Observer<VIPDto> {
                txtUserName.text = it.userName
                txtDeadlineDate.text = SimpleDateFormat("yyyy-MM-dd").format(it.deadlineDate)
                btnBuyVIP.visibility = View.VISIBLE
            })
        }
        btnBuyVIP.visibility = View.GONE
        btnBuyVIP.setOnClickListener {
            (activity as ViewModelDemoActivity).addFragment(BuyVIPFragment())
        }
    }
}
