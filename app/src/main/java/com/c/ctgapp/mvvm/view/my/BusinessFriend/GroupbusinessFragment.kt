package com.c.ctgapp.mvvm.view.my.BusinessFriend

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.c.ctgapp.Adapter.AllBusinessFriendAdapter
import com.c.ctgapp.R
import com.c.ctgapp.Tools.DialogUtils
import com.c.ctgapp.Tools.Utils
import com.c.ctgapp.mvvm.model.BusinessFriend
import com.c.ctgapp.mvvm.model.GroupIdLiveData
import com.c.ctgapp.mvvm.model.Response
import com.c.ctgapp.mvvm.model.UrmFriendGroup
import com.c.ctgapp.mvvm.viewmodel.BusinessFriendViewModel
import com.pilot.common.utils.NetworkUtils
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.fragment_allbusinessfriend.*

@Suppress("DEPRECATION")
class GroupbusinessFragment : Fragment(),Observer<UrmFriendGroup> {
    //分组商友
    private var viewmodel: BusinessFriendViewModel? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var progressDialog: ProgressDialog? = null
    private var dialogUtils: DialogUtils? = null
    private var refreshLayout: SmartRefreshLayout? = null
    private var builder:AlertDialog.Builder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_distributor, container, false)
        refreshLayout = view.findViewById(R.id.refreshLayout)
        dialogUtils = DialogUtils()
        viewmodel = ViewModelProviders.of(this).get(BusinessFriendViewModel::class.java)
        GroupIdLiveData.getInstance().observe(viewLifecycleOwner, this)
        val bundle = this.arguments //得到从Activity传来的数据
        Log.e("groupId",""+bundle!!.getString("groupId"))
        refreshLayout!!.setEnableRefresh(true)
        refreshLayout!!.setEnableLoadMore(false)
        refreshLayout!!.setOnRefreshListener {
            viewmodel!!.urmUserFriendindustry(Utils.getShared2(activity,"token"),bundle.getString("groupId")!!)
        }
        if(NetworkUtils.isConnected(activity)){
            viewmodel!!.urmUserFriendindustry(Utils.getShared2(activity,"token"),bundle.getString("groupId")!!)
        }else {
            Toast.makeText(activity, "请检查您的网络是否连接", Toast.LENGTH_SHORT).show()
        }

        viewmodel!!.getdeletefreinddata().observe(viewLifecycleOwner,Observer{response:Response<*>->
            if(response.status == 0){
                Toast.makeText(activity, "删除成功！", Toast.LENGTH_SHORT).show()
                refreshLayout!!.autoRefresh()
            }else{
                dialogUtils!!.showTwo(activity,"提示",response.msg)
            }
        })


        viewmodel!!.getindustryresponsedata().observe(viewLifecycleOwner, Observer{ response: Response<List<BusinessFriend>> ->
            if(response.status == 0){
                refreshLayout?.finishRefresh(true) //结束刷新
                Log.e("response",""+response.data.size)
                val adapter = AllBusinessFriendAdapter(response.data,activity!!)
                mLinearLayoutManager = LinearLayoutManager(activity)
                recyclerView?.layoutManager = mLinearLayoutManager
                recyclerView?.adapter = adapter
                adapter.setOnItemClickListener(object : AllBusinessFriendAdapter.OnItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        delete(response.data[position].uid)
                    }
                })
                adapter.setOnitemClickListener1(object : AllBusinessFriendAdapter.OnItemClickListener1
                {
                    override fun onItemClick(view: View, position: Int)
                    {
                        val intent = Intent(activity,AddFriendsActivity::class.java)
                            intent.putExtra("code","2")
                            intent.putExtra("friendUserId",response.data[position].uid)
                            intent.putExtra("nickName",response.data[position].realname)
                            startActivityForResult(intent,1)
                    }
                })
                adapter.setOnitemClickListener2(object :AllBusinessFriendAdapter.OnItemClickListener2{
                    override fun onItemClick(view: View, position: Int) {
                        startActivity(Intent(activity, MyBusinessFriendActvity::class.java))
                    }
                })

            }else{
                dialogUtils!!.showTwo(activity, "提示", response.msg)
            }
        })
        return view
    }
    private fun delete(friendUserId:Int){
        builder = AlertDialog.Builder(activity!!).setTitle("提示！")
                .setMessage("确定要删除吗？").setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i ->
                    viewmodel!!.deletefreind(Utils.getShared2(activity,"token"),friendUserId)
                }).setNegativeButton("取消", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
        builder!!.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val success = data?.getStringExtra("success")
                if(success.equals("1")){
                    refreshLayout!!.autoRefresh()
                }
            }
        }

    }

    override fun onChanged(t: UrmFriendGroup?) {

    }
}