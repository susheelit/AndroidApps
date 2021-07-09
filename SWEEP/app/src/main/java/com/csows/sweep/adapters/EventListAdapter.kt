package com.csows.sweep.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.csows.sweep.R
import com.csows.sweep.adapters.EventListAdapter.MyViewHolder
import com.csows.sweep.models.EventDetail
import kotlinx.android.synthetic.main.event_list.view.*

class EventListAdapter(private val eventList: List<EventDetail>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListAdapter.MyViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.event_list,parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventListAdapter.MyViewHolder, position: Int) {
        holder.bindItems(eventList[position])
    }

    override fun getItemCount() = eventList.size

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindItems(eventDetails:EventDetail){
            itemView.tvName.text = eventDetails.EventName
            itemView.tvEventDate.text = eventDetails.EventDate
            itemView.tvRemark.text = eventDetails.Remark
            itemView.tvCreatedOn.text = eventDetails.EntryDate
        }
    }
}
