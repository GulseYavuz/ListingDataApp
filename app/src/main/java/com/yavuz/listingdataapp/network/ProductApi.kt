package com.yavuz.listingdataapp.network

import com.yavuz.listingdataapp.model.ProductResponse
import retrofit2.Response
import retrofit2.http.GET

interface ProductApi {
    @GET("/products")
    suspend fun getAllProduct(): Response<ProductResponse>
}