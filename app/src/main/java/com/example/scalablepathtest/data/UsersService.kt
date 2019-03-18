package com.example.scalablepathtest.data

import com.example.scalablepathtest.data.models.User
import io.reactivex.Observable
import retrofit2.http.GET

interface UsersService {

    @GET("users")
    fun getUsers(): Observable<List<User>>
}
