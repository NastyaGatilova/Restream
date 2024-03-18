package com.example.restream

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restream.databinding.FragmentBroadcastsBinding


class Broadcasts : Fragment() {

    private lateinit var bindingFragmentBroadcast: FragmentBroadcastsBinding
    private lateinit var broadcastAdapter: BroadcastAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragmentBroadcast = FragmentBroadcastsBinding.inflate(inflater, container, false)

        bindingFragmentBroadcast.rcView.layoutManager = LinearLayoutManager(requireContext())

        val broadcastAdapter = BroadcastAdapter(mutableListOf())
        bindingFragmentBroadcast.rcView.adapter = broadcastAdapter
        bindingFragmentBroadcast.addBroadcast.setOnClickListener {
            val probaList = Broadcast("Эфир", "17/03/2024", "14:00")
            broadcastAdapter.broadcastList.add(probaList)
            broadcastAdapter.notifyItemInserted(broadcastAdapter.broadcastList.size - 1)
        }
        return bindingFragmentBroadcast.root
    }






}