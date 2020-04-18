@file:Suppress("UNREACHABLE_CODE")

package com.c.ctgapp.ui.my.BusinessFriend

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.ctgapp.Adapter.ItemAdapter
import com.c.ctgapp.R
import com.c.ctgapp.Tools.AZList.AZItemEntity
import com.c.ctgapp.Tools.AZList.AZTitleDecoration
import com.c.ctgapp.Tools.AZList.LettersComparator
import com.c.ctgapp.Tools.PhoneUtil
import com.pilot.common.utils.PinyinUtils
import kotlinx.android.synthetic.main.activity_maillist.*
import kotlinx.android.synthetic.main.title.*
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

class MaillistActivitys : AppCompatActivity() {

    var context : Context? = null
    private var mAdapter : ItemAdapter? = null
    private val lettersarry : MutableList<String>  = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maillist)
        context = this
        toolbar_title.text = "通讯录"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        initView()
        initEvent()
        check()
    }

    private fun initView() {
        recycler_list.layoutManager = LinearLayoutManager(context)
        recycler_list.addItemDecoration(AZTitleDecoration(AZTitleDecoration.TitleAttributes(context)))
    }


    private fun initEvent() {
        bar_list?.setOnLetterChangeListener { letter ->
            val position : Int = mAdapter!!.getSortLettersFirstPosition(letter)
            if(position != -1){
                if(recycler_list!!.layoutManager is LinearLayoutManager){
                    val manager = recycler_list!!.layoutManager as LinearLayoutManager?
                        manager!!.scrollToPositionWithOffset(position,0)
                }else{
                    recycler_list!!.layoutManager!!.scrollToPosition(position)
                }
            }
        }
    }


    private fun check() {
        //判断是否有权限
        if (ContextCompat.checkSelfPermission(this@MaillistActivitys,Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@MaillistActivitys, arrayOf(Manifest.permission.READ_CONTACTS),200)

        }else{
            initData()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 201){
            initData()
        }else{
            return
        }
    }

    private fun initData(){
        val phoneUtil  = PhoneUtil(this)
        val dateList = fillData(phoneUtil.phone)
        Collections.sort(dateList,LettersComparator())
        recycler_list!!.adapter = ItemAdapter(dateList,this).also { mAdapter = it}
    }




    private fun fillData(date : List<AZItemEntity>): List<AZItemEntity>{

        val sortList : MutableList<AZItemEntity> = ArrayList()
        for (aDate in date){
            val item  = AZItemEntity(aDate.name,aDate.phoneNum,aDate.imgUrl)
            item.name = aDate.name
            //汉字转换拼音
            val pinyin : String = PinyinUtils.getPingYin(aDate.name)
            //获取第一首字母
            val letters = pinyin.substring(0,1).toUpperCase(Locale.ROOT)
            //正则表达式,判断首字母是否是英文
            if (letters.matches( "[A-Z]".toRegex())) {
                item.sortLetters = letters.toUpperCase(Locale.ROOT)
                lettersarry.add(letters.toUpperCase(Locale.ROOT))
                Log.e("TAG","fillData:"+letters.toUpperCase(Locale.ROOT))
            }else {
                item.sortLetters = "#"
                lettersarry.add("#")
            }
            sortList.add(item)
        }
        val comparator = Collator.getInstance(Locale.CHINA)
        Collections.sort(lettersarry,comparator)
        Log.e("TAG","lettersarry:"+lettersarry.size)
        bar_list!!.mLetters = ridRepeat2(lettersarry)
        return  sortList
    }

    companion object {
        fun ridRepeat2(list: List<String>): List<String> {
            println("list = [$list]")
            val listNew: MutableList<String> = ArrayList()
            val set = hashSetOf<Any>()
            for (str: String in list) {
                if (set.add(str)) {
                    listNew.add(str)
                }
            }

            println("listNew = [$listNew]")
            return listNew

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }




}

