package com.c.ctgapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.c.ctgapp.R
import com.c.ctgapp.mvvm.model.Company

class SwitchingEnterprisesAdapter(private val companylist: List<Company>, val context: Context):RecyclerView.Adapter<SwitchingEnterprisesAdapter.BodyViewHolder>(){
    private val VIEW_TYPE_ITEM: Int = 1
   private val VIEW_TYPE_EMPTY: Int = 0
    private lateinit var onItemClickListener: OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }
    class BodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var companyname: TextView = itemView.findViewById(R.id.company_name)
        var Currentuse: TextView = itemView.findViewById(R.id.Currentuse)
        var item: RelativeLayout = itemView.findViewById(R.id.relativeLayout5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyViewHolder {
        //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if(viewType == VIEW_TYPE_EMPTY){
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.empty_view_tab,parent,false)
            return BodyViewHolder(view)
        }
        val baseView: View = LayoutInflater.from(parent.context).inflate(R.layout.switchgenterprises_data,parent,false)
        val bodyViewHolder = BodyViewHolder(baseView)
        return  bodyViewHolder
    }



    override fun onBindViewHolder(holder: BodyViewHolder, position: Int) {

            holder.companyname.text = companylist.get(position).orgName
            if(companylist[position].isDefault.equals("0")){
                holder.Currentuse.text = "当前使用"
            }else{
                holder.Currentuse.text = ""
            }
            holder.item.setOnClickListener{
                onItemClickListener.onItemClick(holder.itemView,position)
            }

        holder.itemView.tag = position
    }

    override fun getItemCount(): Int {
        if(companylist.isEmpty()){
            return 1
        }
        return companylist.size
    }
    override fun getItemViewType(position: Int): Int {
        //如果是0，就是头，否则则是其他的item
        if(companylist.isEmpty()){
            return  VIEW_TYPE_EMPTY
        }
        return VIEW_TYPE_ITEM
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }




}