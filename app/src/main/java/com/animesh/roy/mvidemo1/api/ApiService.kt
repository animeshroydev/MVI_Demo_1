package com.animesh.roy.mvidemo1.api

import androidx.lifecycle.LiveData
import com.animesh.roy.mvidemo1.model.BlogPost
import com.animesh.roy.mvidemo1.model.User
import com.animesh.roy.mvidemo1.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/user/{userId}")
    fun getUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

}