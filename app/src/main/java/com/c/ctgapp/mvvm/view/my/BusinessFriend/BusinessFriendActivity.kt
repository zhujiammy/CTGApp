package com.c.ctgapp.mvvm.view.my.BusinessFriend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.ButterKnife
import com.c.ctgapp.Adapter.FragmentAdapter
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.GroupIdLiveData
import com.c.ctgapp.mvvm.model.PersonalInfo
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.UrmFriendGroup
import com.c.ctgapp.mvvm.viewmodel.GroupingViewModel
import kotlinx.android.synthetic.main.activity_businessfriend.*
import kotlinx.android.synthetic.main.title.*
import java.util.*


class BusinessFriendActivity : AppCompatActivity(), View.OnClickListener,Observer<UrmFriendGroup>  {
    var mFragments = ArrayList<Fragment>()
    var fragmentTitle = ArrayList<String>()
    var adapter: FragmentAdapter? = null
    private var viewmodel: GroupingViewModel? = null
    private var dialogUtils: DialogUtils? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_businessfriend)
        ButterKnife.bind(this)
        dialogUtils = DialogUtils()
        toolbar_title!!.text = "商友"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initUI()
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(GroupingViewModel::class.java)
        viewmodel!!.urmFriendGrouplist(Utils.getShared2(this,"token"))
        viewmodel!!.getUrmFriendGroupdata().observe(this,Observer{ response: Response<List<UrmFriendGroup>> ->
            if(response.status == 0){
                    for(i in response.data){
                        val checkBox = CheckBox(this)
                        checkBox.setTextColor(resources.getColorStateList(R.drawable.rd_text_color_select))
                        checkBox.textSize = 12F
                        checkBox.buttonDrawable = null
                        checkBox.setBackgroundResource(R.drawable.radio_button_selector)
                        checkBox.width = resources.getDimensionPixelOffset(R.dimen.width)
                        checkBox.height = resources.getDimensionPixelOffset(R.dimen.hight)
                        checkBox.text = i.groupName
                        checkBox.id = i.groupId
                        checkBox.gravity = Gravity.CENTER
                        FriendGroup.addView(checkBox)


                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            if(checkBox.id == i.groupId){

                                val groupbusinessFragment = GroupbusinessFragment()
                                if (isChecked) {
                                   // putpersondata(i)
                                    val bundle = Bundle()
                                    bundle.putString("groupId",checkBox.id.toString())
                                    groupbusinessFragment.arguments = bundle//数据传递到fragment中
                                    mFragments.add(groupbusinessFragment)
                                    fragmentTitle.add(i.groupName)
                                    pager!!.adapter = adapter
                                    adapter!!.notifyDataSetChanged()
                                    pager!!.currentItem = fragmentTitle.size - 1
                                } else {
                                    mFragments.remove(groupbusinessFragment)
                                    fragmentTitle.remove(i.groupName)
                                    pager!!.adapter = adapter
                                    adapter!!.notifyDataSetChanged()
                                }
                            }

                        }

                    }
            }else{
                dialogUtils!!.showTwo(this,"提示",response.msg)
            }
        })
        Event()
    }

    private fun initUI() {
        RB_Distributor!!.isChecked = true
        RB_RepairDepot!!.isChecked = true
        RB_Manufacturer!!.isChecked = true
        RB_logistics!!.isChecked = true
        RB_Insurancecompany!!.isChecked = true
        RB_Cardemolitionplant!!.isChecked = true
        mDrawer_layout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val allBusinessFriendFragment = AllBusinessFriendFragment()
        val carDismantlingFragment = CarDismantlingFragment()
        val distributorFragment = DistributorFragment()
        val lnsuranceCompanyFragment = LnsuranceCompanyFragment()
        val logisticsFragment = LogisticsFragment()
        val manufacturerFragment = ManufacturerFragment()
        val repairDepotFragment = RepairDepotFragment()
        adapter = FragmentAdapter(supportFragmentManager, mFragments, fragmentTitle)
        mFragments.add(allBusinessFriendFragment)
        fragmentTitle.add("所有")
        mFragments.add(distributorFragment)
        fragmentTitle.add("经销商")
        mFragments.add(repairDepotFragment)
        fragmentTitle.add("修理厂")
        mFragments.add(manufacturerFragment)
        fragmentTitle.add("制造商")
        mFragments.add(logisticsFragment)
        fragmentTitle.add("物流")
        mFragments.add(lnsuranceCompanyFragment)
        fragmentTitle.add("保险公司")
        mFragments.add(carDismantlingFragment)
        fragmentTitle.add("拆车厂")
        pager!!.adapter = adapter
        tab_layout!!.setupWithViewPager(pager)
        for (i in fragmentTitle.indices) {
            if (fragmentTitle[i] == "经销商") {
                RB_Distributor!!.isChecked = true
            }
            if (fragmentTitle[i] == "修理厂") {
                RB_RepairDepot!!.isChecked = true
            }
            if (fragmentTitle[i] == "制造商") {
                RB_Manufacturer!!.isChecked = true
            }
            if (fragmentTitle[i] == "物流") {
                RB_logistics!!.isChecked = true
            }
            if (fragmentTitle[i] == "保险公司") {
                RB_Insurancecompany!!.isChecked = true
            }
            if (fragmentTitle[i] == "拆车厂") {
                RB_Cardemolitionplant!!.isChecked = true
            }
        }
        RB_Distributor!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mFragments.add(distributorFragment)
                fragmentTitle.add("经销商")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
                pager!!.currentItem = fragmentTitle.size - 1
            } else {
                mFragments.remove(distributorFragment)
                fragmentTitle.remove("经销商")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        RB_RepairDepot!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mFragments.add(repairDepotFragment)
                fragmentTitle.add("修理厂")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
                pager!!.currentItem = fragmentTitle.size - 1
            } else {
                mFragments.remove(repairDepotFragment)
                fragmentTitle.remove("修理厂")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        RB_Manufacturer!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mFragments.add(manufacturerFragment)
                fragmentTitle.add("制造商")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
                pager!!.currentItem = fragmentTitle.size - 1
            } else {
                mFragments.remove(manufacturerFragment)
                fragmentTitle.remove("制造商")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        RB_logistics!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mFragments.add(logisticsFragment)
                fragmentTitle.add("物流")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
                pager!!.currentItem = fragmentTitle.size - 1
            } else {
                mFragments.remove(logisticsFragment)
                fragmentTitle.remove("物流")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        RB_Insurancecompany!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mFragments.add(lnsuranceCompanyFragment)
                fragmentTitle.add("保险公司")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
                pager!!.currentItem = fragmentTitle.size - 1
            } else {
                mFragments.remove(lnsuranceCompanyFragment)
                fragmentTitle.remove("保险公司")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
        RB_Cardemolitionplant!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mFragments.add(carDismantlingFragment)
                fragmentTitle.add("拆车厂")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
                pager!!.currentItem = fragmentTitle.size - 1
            } else {
                mFragments.remove(carDismantlingFragment)
                fragmentTitle.remove("拆车厂")
                pager!!.adapter = adapter
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    private fun Event() {
        screen_iv!!.setOnClickListener(this)
        close_dr!!.setOnClickListener(this)
        Groupsetting_lin!!.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (item.itemId == android.R.id.home) {
            finish()
        }
        if (id == R.id.add) {
            val intent = Intent(this,AddFriendsActivity::class.java)
            intent.putExtra("code","1")
            startActivityForResult(intent,1)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.addfriends, menu)
        return true
    }

    @SuppressLint("WrongConstant")
    override fun onClick(v: View) {
        if (v === screen_iv) {
            mDrawer_layout!!.openDrawer(Gravity.END)
        }
        if (v === close_dr) {
            mDrawer_layout!!.closeDrawer(Gravity.END)
        }
        if (v === Groupsetting_lin) {
            val intent = Intent(applicationContext, GroupingActivity::class.java)
            startActivity(intent)
        }
    }

    //返回键处理
    @SuppressLint("WrongConstant")
    override fun onBackPressed() {
        if (mDrawer_layout!!.isDrawerOpen(Gravity.END)) {
            mDrawer_layout!!.closeDrawer(Gravity.END)
        } else {
            super.onBackPressed()
        }
    }

    private fun putpersondata(urmFriendGroup: UrmFriendGroup){
        GroupIdLiveData.getInstance().value = urmFriendGroup
    }

    override fun onChanged(t: UrmFriendGroup?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}