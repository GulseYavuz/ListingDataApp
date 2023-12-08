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

                // Sonucu bekleyin ve ardından işleyin
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


            /*        lifecycleScope.launch {
            val response = retrofit.getAllProduct()

            if (response.isSuccessful) {
                launch(Dispatchers.Main) {
                    if (!response.body().isNullOrEmpty()) {
                        val recyclerAdapter = response.body()?.let { ProductAdapter(it) }
                        binding.idRVProducts.adapter = recyclerAdapter
                    }
                }
            }
            else{
                Log.e("NetworkError", "Hata: ${response.code()} - ${response.message()}")
                return@launch
            }
    }*/

            //   val productApi = retrofit.create(ProductApi::class.java)
            // val call: Call<ArrayList<Product>?> = productApi.getAllProduct()

            /*        call!!.enqueue(object : Callback<ArrayList<Product>?> {
            override fun onResponse(
                call: Call<ArrayList<Product>?>,
                response: Response<ArrayList<Product>?>

            ) {
                if (response.isSuccessful) {
                    binding.idPBLoading.visibility = View.GONE
                    product = response.body()!!
                }

                // on below line we are initializing our adapter.
                productAdapter = ProductAdapter(product)

                // on below line we are setting adapter to recycler view.
                binding.idRVProducts.adapter = productAdapter
            }

            override fun onFailure(call: Call<ArrayList<Product>?>, t: Throwable) {
                // displaying an error message in toast
                Toast.makeText(this@MainActivity, "Fail to get the data..", Toast.LENGTH_SHORT)
                    .show()
            }
        })*/

        }
    }
