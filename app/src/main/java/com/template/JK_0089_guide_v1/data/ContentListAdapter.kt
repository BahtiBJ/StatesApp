package com.template.JK_0089_guide_v1.data

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.template.JK_0089_guide_v1.R
import com.template.JK_0089_guide_v1.models.ContentType

class ContentListAdapter(
    private val listOfValues: ArrayList<out ContentType>,
    private val onListItemTouchListener: OnListItemTouchListener
) : RecyclerView.Adapter<ContentListAdapter.ViewHolder>() {

    init{
        Log.d("ChapterTAG","New Instance Adapter Values = ${listOfValues} ")
    }

    interface OnListItemTouchListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = listOfValues[position].title
        holder.image.setImageURI(listOfValues[position].image)
        holder.itemView.setOnClickListener {
            onListItemTouchListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return listOfValues.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.list_item_title)
        val image: ImageView = itemView.findViewById(R.id.list_item_preview)
    }
}