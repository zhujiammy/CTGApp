package com.c.ctgapp.mvvm.view.my.BusinessFriend

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import com.c.ctgapp.Adapter.GroupingAdapter
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.RecyclerViewEmptySupport
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.UrmFriendGroup
import com.c.ctgapp.mvvm.viewmodel.GroupingViewModel
import com.pilot.common.utils.NetworkUtils
import java.util.*
import kotlinx.android.synthetic.main.title.*
import kotlinx.android.synthetic.main.activity_grouping.*

@Suppress("DEPRECATION")
class GroupingActivity : AppCompatActivity(), View.OnClickListener {
    private var adapter: GroupingAdapter? = null
    private var viewmodel:GroupingViewModel? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    private var dialog1:AlertDialog? = null
    private var builder:AlertDialog.Builder? = null
    private var name:String? = null
    private var groupId:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grouping)
        ButterKnife.bind(this)
        toolbar_title!!.text = "分组"
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        progressDialog = ProgressDialog(this@GroupingActivity,
                R.style.AppTheme_Dark_Dialog)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(true)
        progressDialog!!.setMessage("加载中...")
        progressDialog!!.show()
        viewmodel = ViewModelProvider(
                this, ViewModelProvider.AndroidViewModelFactory(application)
        ).get(GroupingViewModel::class.java)
        dialogUtils = DialogUtils()
        Log.e("token",Utils.getShared2(this,"token"))
        viewmodel!!.urmFriendGrouplist(Utils.getShared2(this,"token"))
        viewmodel!!.getResponseData().observe(this,Observer{response:Response<*>->
            if(response.status == 0){
                progressDialog!!.dismiss()
                dialog1!!.dismiss()
                Toast.makeText(applicationContext, "添加成功！", Toast.LENGTH_SHORT).show()
                viewmodel!!.urmFriendGrouplist(Utils.getShared2(this,"token"))
            }else{
                progressDialog!!.dismiss()
                Toast.makeText(applicationContext, response.msg, Toast.LENGTH_SHORT).show()
            }
        })
        viewmodel!!.geturmFriendGroupdatedata().observe(this,Observer{response:Response<*>->
            if(response.status == 0){
                progressDialog!!.dismiss()
                dialog1!!.dismiss()
                Toast.makeText(applicationContext, "更新成功！", Toast.LENGTH_SHORT).show()
                viewmodel!!.urmFriendGrouplist(Utils.getShared2(this,"token"))
            }else{
                dialogUtils!!.showTwo(this,"提示",response.msg)
            }
        })

        viewmodel!!.geturmFriendGroupdeletedata().observe(this,Observer{response:Response<*>->
            if(response.status == 0){
                Toast.makeText(applicationContext, "删除成功！", Toast.LENGTH_SHORT).show()
                viewmodel!!.urmFriendGrouplist(Utils.getShared2(this,"token"))
            }else{
                dialogUtils!!.showTwo(this,"提示",response.msg)
            }
        })
        viewmodel!!.getUrmFriendGroupdata().observe(this,Observer{response:Response<List<UrmFriendGroup>> ->
            if(response.status == 0){
                progressDialog!!.dismiss()
                adapter = response.data?.let { GroupingAdapter(it,this) }
                recyclerView.layoutManager = GridLayoutManager(applicationContext, 1, GridLayoutManager.VERTICAL, false)
                recyclerView.adapter = adapter
                adapter?.setOnItemClickListener(object : GroupingAdapter.OnItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        delete(response.data[position].groupId)
                    }
                })
                adapter?.setOnitemClickListener1(object : GroupingAdapter.OnItemClickListener1
                {
                    override fun onItemClick(view: View, position: Int)
                    {
                        name = response.data[position].groupName
                        groupId = response.data[position].groupId
                        add_group()
                    }
                })
                adapter?.setOnitemClickListener2(object :GroupingAdapter.OnItemClickListener2{
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent()
                        name = response.data[position].groupName
                        groupId = response.data[position].groupId
                        intent.putExtra("groupId", groupId.toString())
                        intent.putExtra("name", name!!)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                })

            }else{
                progressDialog!!.dismiss()
                dialogUtils!!.showTwo(this,"提示",response.msg)
            }
        })
        Event()
    }

    private fun Event() {
        add_group_lin!!.setOnClickListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View) {
        if (v === add_group_lin) { //添加分组
            add_group()
        }
    }

    private fun delete(groupId:Int){
        builder = AlertDialog.Builder(this).setTitle("提示！")
                .setMessage("确定要删除吗？").setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                    viewmodel!!.deletegroup(groupId,Utils.getShared2(this,"token"))
                }).setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
        builder!!.create().show()
    }

    @SuppressLint("InflateParams")
    private fun add_group() {
        val dialog = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this.baseContext).inflate(R.layout.dialog_group, null, false)
        dialog.setView(view)
        dialog.setCancelable(false)
        val group_name = view.findViewById<EditText>(R.id.group_name)
        val confirm_bt = view.findViewById<Button>(R.id.confirm_bt)
        val cancel_bt = view.findViewById<Button>(R.id.cancel_bt)
        if(name!= null){
            group_name.setText(name)
        }
        dialog1 = dialog.show()
        confirm_bt.setOnClickListener {
            if(TextUtils.isEmpty(group_name.text.toString())){
                Toast.makeText(applicationContext, "分组名称不能为空！", Toast.LENGTH_SHORT).show()
            }else if(!NetworkUtils.isConnected(applicationContext)){
                Toast.makeText(applicationContext, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
            }else{
                progressDialog!!.setMessage("保存中...")
                progressDialog!!.show()
                    if(name != null)
                    {
                    viewmodel!!.updategroup(groupId!!,Utils.getShared2(this,"token"),group_name.text.toString())
                    }else
                    {
                    viewmodel!!.createname(group_name.text.toString(),Utils.getShared2(this,"token"))
                    }
            }
        }
        cancel_bt.setOnClickListener { dialog1!!.dismiss()}
    }
}