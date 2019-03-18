package com.example.scalablepathtest.features.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scalablepathtest.Constants
import com.example.scalablepathtest.R
import com.example.scalablepathtest.data.PostsService
import com.example.scalablepathtest.data.UsersService
import com.example.scalablepathtest.domain.NamedPostsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // Normally we would inject all of it with Dagger
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.domain)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val namedPostsUseCase = NamedPostsUseCase(
        retrofit.create(UsersService::class.java),
        retrofit.create(PostsService::class.java)
    )

    private val subscribtions: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var recyclerView = findViewById<RecyclerView>(R.id.namedPostsRecyclerView)
        val adapter = NamedPostsAdapter()
        recyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        subscribtions.add(
            namedPostsUseCase.getNamedPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ posts ->
                    adapter.setNamedPosts(posts)
                }, { error ->
                    Toast.makeText(this, "Error fetching the data", Toast.LENGTH_LONG).show()
                })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribtions.clear()
    }
}
