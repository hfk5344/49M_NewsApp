package com.example.mis49mnewsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(private val context: Context, private val articles: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_layout, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = articles[position]
        holder.textTitle.text = currentItem.title
        holder.textAuthor.text = currentItem.author ?: "Unknown Author" // Eğer author null ise "Unknown Author" yaz
        holder.textUrl.text = "Habere Git" // Sabit bir metin olarak "Habere Git" yaz

        holder.itemView.setOnClickListener {
            // Burada tıklanan haberin URL'ini açmak için bir işlem yapabilirsiniz
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(currentItem.url))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textAuthor: TextView = itemView.findViewById(R.id.textAuthor) // textAuthor bileşenine ait tanımı ekleyin
        val textUrl: TextView = itemView.findViewById(R.id.textUrl)
    }
}
