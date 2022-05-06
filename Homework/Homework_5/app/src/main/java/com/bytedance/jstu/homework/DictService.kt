package com.bytedance.jstu.homework

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DictService {
    @GET("jsonapi")
    fun get(@Query("q") q: String): Call<DictData>
}