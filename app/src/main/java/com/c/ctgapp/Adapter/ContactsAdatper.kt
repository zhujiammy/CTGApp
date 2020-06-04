package com.c.ctgapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c.ctgapp.R
import com.c.ctgapp.mvvm.model.Externalcontact
import de.hdodenhof.circleimageview.CircleImageView

class ContactsAdatper(private val externalcontactlist:List<Externalcontact>,private val context: Context) : RecyclerView.Adapter<ContactsAdatper.BodyViewHolder>(){
    var type = 1
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_ITEM1 = 2
    private lateinit var onItemClickListener: OnItemClickListener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BodyViewHolder{ //在onCreateViewHolder方法中，我们要根据不同的ViewType来返回不同的ViewHolder
        if (viewType == VIEW_TYPE_EMPTY) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.empty_view_tab, viewGroup, false)
            return BodyViewHolder(view)
        }
        if (viewType == VIEW_TYPE_ITEM1) {
            val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.contacts_add_data, viewGroup, false)
            return BodyViewHolder(view)
        }

        val baseView: View
        baseView = LayoutInflater.from(viewGroup.context).inflate(R.layout.contacts_data, viewGroup, false)
        val bodyViewHolder = BodyViewHolder(baseView)
        return bodyViewHolder
    }

    override fun onBindViewHolder(viewHolder: BodyViewHolder, i: Int) {
        viewHolder.name.text = externalcontactlist[i].name
        viewHolder.telephone.text = externalcontactlist[i].telephone
        viewHolder.title.text = externalcontactlist[i].title
        Glide.with(context).load(externalcontactlist[i].headImgUrl).into(viewHolder.niceImageView)

        viewHolder.itemView.tag = i
    }

    override fun getItemCount(): Int {
        if(externalcontactlist.isEmpty()){
            return 1
        }
        return externalcontactlist.size
    }

    /**
     *
     * 复用getItemViewType方法，根据位置返回相应的ViewType
     * @param position
     * @return
     */
    override fun getItemViewType(position: Int): Int { //如果是0，就是头，否则则是其他的item
        return VIEW_TYPE_ITEM
    }


    class BodyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var niceImageView: CircleImageView = itemView.findViewById(R.id.niceImageView)
        var name: TextView = itemView.findViewById(R.id.name)
        var telephone: TextView = itemView.findViewById(R.id.telephone)
        var title: TextView = itemView.findViewById(R.id.title)
        var edit_iv:ImageView = itemView.findViewById(R.id.edit_iv)
    }



}