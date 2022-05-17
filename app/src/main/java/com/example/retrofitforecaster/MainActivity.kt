package com.example.retrofitforecaster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var mService: RetrofitServices
    private val longClickListener: (ListItem) -> Unit = { item ->
        onLongClick(item)
    }
    private val adapter = Adapter(longClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.rView)

        val model: MyViewModel by viewModels()
        model.getWeatherData().observe(this) { weatherData ->
            adapter.submitList(weatherData)
        }

        mService = Common.retrofitService
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun onLongClick(listItem: ListItem) {
        val weatherItem: ArrayList<String> = arrayListOf (
            "City: ${R.string.City}",
            "Date: ${listItem.dt_txt}",
            "Temperature: ${listItem.main.temp}"
        )

        val weather: String = "City: Shklov"

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, weather)
            type = "text/*"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }
}
