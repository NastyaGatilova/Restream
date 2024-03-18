package com.example.restream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restream.databinding.ListItemBroadcastBinding

class BroadcastAdapter(val broadcastList: MutableList<Broadcast>) :  RecyclerView.Adapter<BroadcastAdapter.BroadcastHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BroadcastHolder {
       // val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_broadcast, parent, false)
        val binding = ListItemBroadcastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BroadcastHolder(binding)
    }

    override fun onBindViewHolder(holder: BroadcastHolder, position: Int) {

        holder.bind(broadcastList[position])

    }

    override fun getItemCount(): Int {
        return broadcastList.size
    }
    class BroadcastHolder(private val binding: ListItemBroadcastBinding) : RecyclerView.ViewHolder(binding.root) {

       // val bindingList = ListItemBroadcastBinding.bind(item)

        fun bind(broadcast: Broadcast) = with (binding) {
            binding.nameBroadcast.text = broadcast.name
            dateBroadcast.text = broadcast.date
            timeBroadcast.text = broadcast.time
        }


    }











}