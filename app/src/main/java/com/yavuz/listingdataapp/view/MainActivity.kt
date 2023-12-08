package com.yavuz.listingdataapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.yavuz.listingdataapp.repository.HttpRepository
import com.yavuz.listingdataapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            try {
                val deferredResponse = lifecycleScope.async {
                    HttpRepository.getAllProduct()
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
