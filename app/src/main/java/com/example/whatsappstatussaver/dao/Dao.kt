package com.example.whatsappstatussaver.dao

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.example.whatsappstatussaver.model.statusModel
import com.example.whatsappstatussaver.utils.Constants
import java.io.File

class Dao {

    fun getData(path:String, context: Context):ArrayList<statusModel>{
        var fileData: statusModel  //status data model

        val filesList:ArrayList<statusModel> = ArrayList()

        val statusDir = File(path) //files location
        val statusFiles:Array<out File>? = statusDir.listFiles() //array of all files in files location

        if (statusFiles != null) {
            for (f in statusFiles){
                fileData = statusModel(f.name, Uri.fromFile(f), f.path)  //object of status model with required data
                if (!Uri.fromFile(f).toString().endsWith(".nomedia")){
                    filesList.add(fileData)   //creating array of object
                }
            }
        }
        else{
            Toast.makeText(context, "Open WhatsApp to get status", Toast.LENGTH_LONG).show()
        }
        return filesList
    }
}