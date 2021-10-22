package com.example.whatsappstatussaver.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.adapters.StatusAdapter
import com.example.whatsappstatussaver.dao.Dao
import com.example.whatsappstatussaver.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SavedStatus : Fragment(R.layout.fragment_saved_status) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView(view)

        val refresh = view.findViewById<FloatingActionButton>(R.id.refresh)
        refresh.setOnClickListener{
            recyclerView(view)
        }

    }

    private fun recyclerView(view: View){
        val filesList = Dao().getData(Constants.SAVED_STATUS_LOCATION, requireContext())
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSavedStatus)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        val adapter = StatusAdapter(requireContext(), filesList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}