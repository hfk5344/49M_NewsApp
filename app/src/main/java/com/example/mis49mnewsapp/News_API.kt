package com.example.mis49mnewsapp

import com.example.mis49mnewsapp.NewsResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsService {
    @GET
    fun getData(
        @Url url: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}
