package com.example.whatsappstatussaver

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.net.toUri
import com.example.whatsappstatussaver.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val video = findViewById<VideoView>(R.id.video)
        val vFab = findViewById<FloatingActionButton>(R.id.vFAB)
        val shareButton = findViewById<ImageView>(R.id.vShare)

        val sharedPref = this.getSharedPreferences("dwa", Context.MODE_PRIVATE)

        val bool = sharedPref.getBoolean("dwaBool",false)

        val oldPath = if (bool){
            Constants.STATUS_LOCATION_DW
        }else{
            Constants.STATUS_LOCATION
        }


        val savedFilePath = Constants.SAVED_STATUS_LOCATION
        val fileName = intent.getStringExtra("vFileName")
        val uri = intent.getStringExtra("vUri")!!.toUri()
        val savedFile = File(savedFilePath+fileName)
        val oldFile = File(oldPath+fileName)

        fabImage(savedFile, vFab)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(video)
        video.setMediaController(mediaController)
        video.setVideoURI(uri)
        video.start()

        vFab.setOnClickListener{
            saveDelete(savedFile,oldFile,vFab)
        }

        shareButton.setOnClickListener{
            if (oldFile.exists()){
//                val imagePath: File = File(Context.getFilesDir(), "my_images")
                val newFile = File(oldPath, fileName!!)
                val contentUri: Uri =
                    getUriForFile(this, "com.example.whatsappstatussaver.provider", newFile)

                share(contentUri)
            }
            else{
                println("dose not exists")
            }
        }

    }

    private fun saveDelete(savedFile:File,oldFile:File,fab: FloatingActionButton){
        if(savedFile.exists()){
            savedFile.delete()
            fab.setImageResource(R.drawable.ic_save)
            Toast.makeText(this,"Deleted Successfully", Toast.LENGTH_SHORT).show()
        }
        else{
            oldFile.copyTo(savedFile,true, DEFAULT_BUFFER_SIZE)
            fab.setImageResource(R.drawable.ic_delete)
            Toast.makeText(this,"Saved Successfully", Toast.LENGTH_SHORT).show()
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
        intent.type = "video/mp4"
        intent.clipData = ClipData.newRawUri("", uri);
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivity(intent)
    }
}