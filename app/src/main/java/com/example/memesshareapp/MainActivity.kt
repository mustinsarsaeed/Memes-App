package com.example.memesshareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import android.net.Uri
import android.view.View
import java.io.FileNotFoundException
import java.io.InputStream


class MainActivity : AppCompatActivity() {
    var currentImageUrl: String? = null
    var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar);
        loadMemes()
    }

    private fun loadMemes() {

        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).into(findViewById(R.id.setMemes))

                progressBar?.visibility = View.GONE
            },
            { })

// Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

    fun funNext(view: android.view.View) {

        progressBar?.visibility = View.VISIBLE
        loadMemes()
    }

    fun funShare(view: android.view.View) {

        val sharingIntent = Intent(Intent.ACTION_SEND)
        val currentImageUrl: Uri = Uri.parse("android.resource://com.android.test/*")
        try {
            val stream: InputStream? = contentResolver.openInputStream(currentImageUrl)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        sharingIntent.type = "image/jpeg"
        sharingIntent.putExtra(Intent.EXTRA_STREAM, currentImageUrl)
        startActivity(Intent.createChooser(sharingIntent, "Share image using"))
    }
}