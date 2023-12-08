package com.yavuz.listingdataapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.yavuz.listingdataapp.databinding.ActivityMainBinding
import com.yavuz.listingdataapp.network.ProductApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/products/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)

        lifecycleScope.launch {
            try {
                val deferredResponse = lifecycleScope.async {
                    retrofit.getAllProduct()
                }

                val response = deferredResponse.await()
                if (response.isSuccessful) {
                    launch(Dispatchers.Main) {
                        response.body()?.let { productResponse ->
                            val productList = productResponse.products
                            val recyclerAdapter = ProductAdapter(productList)
                            binding.idRVProducts.adapter = recyclerAdapter
                        }
                    }
                } else {
                    Log.e("NetworkError", "Hata: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("NetworkError", "Bir hata oluştu: ${e.message}")
            }
        }
    }
}
