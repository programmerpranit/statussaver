package com.example.whatsappstatussaver.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.adapters.StatusAdapter
import com.example.whatsappstatussaver.dao.Dao
import com.example.whatsappstatussaver.model.statusModel
import com.example.whatsappstatussaver.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial
import java.io.File

class Status() : Fragment(R.layout.fragment_status) {

    private lateinit var path:String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("dwa", Context.MODE_PRIVATE)

        val bool = sharedPref.getBoolean("dwaBool",false)

        path = if (bool){
            Constants.STATUS_LOCATION_DW
        }else{
            Constants.STATUS_LOCATION
        }

        setupLayout(view)

    }

    private fun setupLayout(view: View){
        val filesList = Dao().getData(path, requireContext())
        println(path)
        val recyclerView = view.findViewById<RecyclerView>(R.id.statusRecyclerView)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        val adapter = StatusAdapter(requireContext(), filesList)
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}