package com.example.scalablepathtest.features.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scalablepathtest.R
import com.example.scalablepathtest.domain.models.NamedPost

class NamedPostsAdapter : RecyclerView.Adapter<NamedPostsAdapter.NamedPostHolder>() {

    private var namedPosts: List<NamedPost> = listOf();

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NamedPostHolder {
        return NamedPostHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_cell, parent, false))
    }

    override fun getItemCount(): Int {
        return namedPosts.size
    }

    override fun onBindViewHolder(holder: NamedPostHolder, position: Int) {
        val post = namedPosts[position];
        holder.title.text = post.post.title
        holder.author.text = post.user.name
        holder.content.text = post.post.body
    }

    fun setNamedPosts(namedPosts: List<NamedPost>) {
        this.namedPosts = namedPosts
        notifyDataSetChanged()
    }

    class NamedPostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView;
        val author: TextView;
        val content: TextView;

        init {
            title = itemView.findViewById(R.id.titleTextView)
            author = itemView.findViewById(R.id.authorTextView)
            content = itemView.findViewById(R.id.contentTextView)
        }
    }
}
