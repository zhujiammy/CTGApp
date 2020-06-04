package com.c.ctgapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c.ctgapp.R
import com.c.ctgapp.mvvm.model.Externalcontact
import com.c.ctgapp.mvvm.model.Honor
import com.c.ctgapp.mvvm.model.UrmFriendGroup
import de.hdodenhof.circleimageview.CircleImageView

class GroupingAdapter(private val urmFriendGroup:List<UrmFriendGroup>, private val context: Context) : RecyclerView.Adapter<GroupingAdapter.BodyViewHolder>(){
    var type = 1
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_ITEM1 = 2
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var onItemClickListener1: OnItemClickListener1
    private lateinit var onItemClickListener2: OnItemClickListener2

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener

    }
    fun setOnitemClickListener1(listenner1:OnItemClickListener1){
        this.onItemClickListener1 = listenner1
    }
    fun setOnitemClickListener2(listenner2:OnItemClickListener2){
        this.onItemClickListener2 = listenner2
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }
    interface OnItemClickListener1{
        fun onItemClick(view: View, position: Int)
    }
    interface OnItemClickListener2{
        fun onItemClick(view: View, position: Int)
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BodyViewHolder{ //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if (viewType == VIEW_TYPE_EMPTY) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.empty_view_tab, viewGroup, false)
            return BodyViewHolder(view)
        }
        val baseView: View
        baseView = LayoutInflater.from(viewGroup.context).inflate(R.layout.grouping_data, viewGroup, false)
        val bodyViewHolder = BodyViewHolder(baseView)
        return bodyViewHolder
    }

    override fun onBindViewHolder(viewHolder: BodyViewHolder, i: Int) {
        viewHolder.groupName.text = urmFriendGroup[i].groupName
        viewHolder.btnUnRead.setOnClickListener {
            onItemClickListener1.onItemClick(viewHolder.itemView,i)
        }
        viewHolder.btnDelete.setOnClickListener {
            onItemClickListener.onItemClick(viewHolder.itemView,i)
        }

        viewHolder.select_group.setOnClickListener {
            onItemClickListener2.onItemClick(viewHolder.itemView,i)
        }
        viewHolder.itemView.tag = i
    }

    override fun getItemCount(): Int {
        if(urmFriendGroup.isEmpty()){
            return 0
        }
        return urmFriendGroup.size
    }

    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int { //如果是0，就是头，否则则是其他的item
        if(urmFriendGroup.isEmpty()){
            return  VIEW_TYPE_EMPTY
        }
        return VIEW_TYPE_ITEM
    }


    class BodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var groupName: TextView = itemView.findViewById(R.id.groupName)
        var btnUnRead: Button = itemView.findViewById(R.id.btnUnRead)
        var btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        var select_group:LinearLayout = itemView.findViewById(R.id.select_group)
    }



}