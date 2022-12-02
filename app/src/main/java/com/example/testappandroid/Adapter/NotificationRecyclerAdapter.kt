package com.example.testappandroid.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testappandroid.R
import com.example.testappandroid.RoomDB.Expense
import com.example.testappandroid.RoomDB.Expense2
import java.util.ArrayList

class NotificationRecyclerAdapter(private val mList: ArrayList<Expense?>) : RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_layout, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.time.text = ItemsViewModel!!.time
        holder.date.text = ItemsViewModel.date
        holder.battery.text = ItemsViewModel.battery

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.textView)
        val time: TextView = itemView.findViewById(R.id.textView1)
        val date: TextView = itemView.findViewById(R.id.textView2)
        val battery: TextView = itemView.findViewById(R.id.textView3)
    }
}