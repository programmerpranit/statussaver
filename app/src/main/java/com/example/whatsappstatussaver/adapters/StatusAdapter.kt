package com.example.whatsappstatussaver.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsappstatussaver.PictureActivity
import com.example.whatsappstatussaver.R
import com.example.whatsappstatussaver.VideoActivity
import com.example.whatsappstatussaver.model.statusModel

class StatusAdapter(private val context: Context,
                    private val filesList:ArrayList<statusModel>) :RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.statusview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return filesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = filesList[position]

        Glide.with(holder.itemView.context).load(currentItem.uri).into(holder.statusThumbnail)

        if(currentItem.uri.toString().endsWith(".mp4")){
            holder.playButton.visibility = View.VISIBLE
        }

        holder.statusThumbnail.setOnClickListener{

            if (currentItem.uri.toString().endsWith(".mp4")){
                val vIntent = Intent(context, VideoActivity::class.java)
                vIntent.putExtra("vUri",currentItem.uri.toString())
                vIntent.putExtra("vFileName", currentItem.fileName)
                context.startActivity(vIntent)
            }
            else{
                val pIntent = Intent(context, PictureActivity::class.java)
                pIntent.putExtra("uri",currentItem.uri.toString())
                pIntent.putExtra("fileName", currentItem.fileName)
                context.startActivity(pIntent)
            }
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val statusThumbnail:ImageView = itemView.findViewById(R.id.statusImageView)
    val playButton:ImageView = itemView.findViewById(R.id.playButton)
}
