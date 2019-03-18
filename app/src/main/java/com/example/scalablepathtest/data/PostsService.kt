package com.example.scalablepathtest.data

import com.example.scalablepathtest.data.models.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    fun getPosts(): Observable<List<Post>>

}
