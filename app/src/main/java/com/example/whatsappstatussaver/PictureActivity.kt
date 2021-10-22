package com.example.whatsappstatussaver

import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.whatsappstatussaver.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class PictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        val sharedPref = this.getSharedPreferences("dwa", Context.MODE_PRIVATE)

        val bool = sharedPref.getBoolean("dwaBool",false)

        val oldPath = if (bool){
            Constants.STATUS_LOCATION_DW
        }else{
            Constants.STATUS_LOCATION
        }
        val savedFilePath = Constants.SAVED_STATUS_LOCATION
        val fileName = intent.getStringExtra("fileName")
        val uri = intent.getStringExtra("uri")!!.toUri()
        val savedFile = File(savedFilePath+fileName)
        val oldFile = File(oldPath+fileName)

        val fab = findViewById<FloatingActionButton>(R.id.pFAB)
        val shareButton = findViewById<ImageView>(R.id.pShare)
        fabImage(savedFile, fab)

        //show picture
        val picture = findViewById<ImageView>(R.id.picture)

        Glide.with(this).load(uri).into(picture)

        fab.setOnClickListener{
            saveDelete(savedFile,oldFile, fab)
        }

        shareButton.setOnClickListener{
            if (oldFile.exists()){
                val cUri = getUriForFile(this, "com.example.whatsappstatussaver.provider", oldFile)
                share(cUri)
            }
            else{
                println("dose not exists")
            }

        }
    }

    private fun saveDelete(savedFile:File,oldFile:File,fab:FloatingActionButton){
        if(savedFile.exists()){
            savedFile.delete()
            fab.setImageResource(R.drawable.ic_save)
            Toast.makeText(this,"Deleted Successfully",Toast.LENGTH_SHORT).show()
        }
        else{
            oldFile.copyTo(savedFile,true, DEFAULT_BUFFER_SIZE)
            fab.setImageResource(R.drawable.ic_delete)
            Toast.makeText(this,"Saved Successfully",Toast.LENGTH_SHORT).show()

            MediaScannerConnection.scanFile(this,
                arrayOf(Constants.SAVED_STATUS_LOCATION), arrayOf("*/*"),null)
        }
    }



    private fun fabImage(savedFile: File, fab: FloatingActionButton){
        if(savedFile.exists()){
            fab.setImageResource(R.drawable.ic_delete)
        }
        else{
            fab.setImageResource(R.drawable.ic_save)
        }
    }

    private fun share(uri: Uri){
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/jpg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
}

