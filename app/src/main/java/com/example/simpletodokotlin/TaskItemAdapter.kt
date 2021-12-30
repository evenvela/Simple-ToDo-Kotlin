package com.example.simpletodokotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskItemAdapter (val listOfItems: List<String>,
                       val longClickListener: OnLongClickListener,
                       val clickListener: OnClickListener): RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    interface OnClickListener{
        fun onItemClicked(position: Int)
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the data model based on position
        val item = listOfItems.get(position)

        // Set item views based on your views and data model
        val tvItem = holder.tvItem
        tvItem.text = item
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvItem:TextView

        init{
            tvItem = itemView.findViewById<TextView>(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }

            itemView.setOnClickListener {
                clickListener.onItemClicked(adapterPosition)
                true
            }
        }
    }

}