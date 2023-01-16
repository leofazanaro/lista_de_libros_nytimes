package com.lofstudio.listadelivros.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface Endpoint {

    @GET("svc/books/v3/lists.json?list=paperback-nonfiction&api-key=01rO4tzrUxNU3IY0zuHkRyT2THtIdBPi")
    fun getJson(): Call<JsonObject>
}