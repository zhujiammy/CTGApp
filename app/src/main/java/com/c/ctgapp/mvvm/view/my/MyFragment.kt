@file:Suppress("DEPRECATION")

package com.c.ctgapp.mvvm.view.my

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.databinding.FragmentMyBinding
import com.c.ctgapp.mvvm.model.*
import com.c.ctgapp.mvvm.view.my.BusinessFriend.BusinessFriendActivity
import com.c.ctgapp.mvvm.view.my.SwitchingEnterprises.SwitchingEnterprisesActivity
import com.c.ctgapp.mvvm.viewmodel.DaggerViewModelFactory
import dagger.android.support.AndroidSupportInjection
import java.security.acl.Owner
import kotlinx.android.synthetic.main.fragment_my.*
import javax.inject.Inject

//我的
class MyFragment : Fragment(), View.OnClickListener,Observer<PersonalInfo> {
    private var binding: FragmentMyBinding? = null
    @JvmField
    @Inject
    var viewModelFactory: DaggerViewModelFactory? = null
    private var myViewModell: MyViewModel? = null
    private var intent: Intent? = null
    private var dialogUtils: DialogUtils? = null
    private var companyId: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_my, container, false)
        binding = DataBindingUtil.bind(root)
        dialogUtils = DialogUtils()
        myViewModell = ViewModelProviders.of(this, viewModelFactory).get(MyViewModel::class.java)
        UserInfoLiveData.getInstance().observe(viewLifecycleOwner, this)
        Log.e("companyName",""+Utils.getShared2(activity, "companyName"))
        binding!!.companyname.setText(Utils.getShared2(activity, "companyName"))
        Event()
        return root
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }


    private fun Event() {
        binding!!.SwitchingEnterprisesIv.setOnClickListener(this)
        binding!!.niceImageView.setOnClickListener(this)
        binding!!.name.setOnClickListener(this)
        binding!!.phoneNum.setOnClickListener(this)
        binding!!.editIv.setOnClickListener(this)
        binding!!.BusinessfriendTv.setOnClickListener(this)
        binding!!.SetRL.setOnClickListener(this)
        binding!!.AttentionnotesRl.setOnClickListener(this)
        binding!!.QueryrecordRl.setOnClickListener(this)
        binding!!.mybusinessLin.setOnClickListener(this)
        binding!!.MyteamLin.setOnClickListener(this)
        binding!!.customermanagementLin.setOnClickListener(this)
        binding!!.companyname.setOnClickListener(this)
    }

    override fun onClick(v: View) { //切换企业
        if (v === binding!!.SwitchingEnterprisesIv || v == binding!!.companyname) {
            intent = Intent(activity, SwitchingEnterprisesActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.niceImageView || v === binding!!.name || v === binding!!.phoneNum || v === binding!!.editIv) { //修改个人信息
            intent = Intent(activity, PersonalInformationActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.BusinessfriendTv) { //商友
            intent = Intent(activity, BusinessFriendActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.SetRL) { //设置
            intent = Intent(activity, SetActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.AttentionnotesRl) { //关注记录
            intent = Intent(activity, AttentionNotesActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.QueryrecordRl) { //查询记录
            intent = Intent(activity, QueryrecordActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.mybusinessLin) { //我的企业
            intent = Intent(activity, MyBusinessActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.MyteamLin) { //我的团队
            intent = Intent(activity, MyTeamActivity::class.java)
            startActivity(intent)
        }
        if (v === binding!!.customermanagementLin) { //客户管理
            intent = Intent(activity, CustomerManagementActivity::class.java)
            startActivity(intent)
        }
    }



    override fun onChanged(t: PersonalInfo?) {
        if (t != null) {
                name.setText(t.nickname)
                phone_num.setText(t.telphone)
                Glide.with(this).load(t.file).into(niceImageView)
        }
    }
}