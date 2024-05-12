package com.example.mis49mnewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private var articleList: ArrayList<Article> = ArrayList()
    private lateinit var selectedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val turkishButton = findViewById<Button>(R.id.turkishButton)
        val englishButton = findViewById<Button>(R.id.englishButton)
        val categorySpinner = findViewById<Spinner>(R.id.categorySpinner)
        val pleaseSelectTextView = findViewById<TextView>(R.id.pleaseSelectTextView)

        // Spinner'a kategori seçeneklerini ekleme
        val categories = arrayOf("Business", "Technology", "Science", "Health", "Sports")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categorySpinner.adapter = adapter
        categorySpinner.setSelection(AdapterView.INVALID_POSITION)
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = categories[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                pleaseSelectTextView.visibility = View.VISIBLE
            }
        }

        turkishButton.setOnClickListener {
            fetchData("top-headlines?country=tr", "b8c2d44a26c245aaa0ae59d111baf078")
        }

        englishButton.setOnClickListener {
            fetchData("top-headlines?country=us", "b8c2d44a26c245aaa0ae59d111baf078")

        }
    }

    private fun fetchData(url: String, apiKey: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NewsService::class.java)
        val call = service.getData(url, selectedCategory , apiKey)

        call.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        articleList.clear()
                        articleList.addAll(it.articles)
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
                        val adapter = NewsAdapter(this@MainActivity, articleList)
                        recyclerView.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                println("FAILURE: ${t.message}")
            }
        })
    }
}
