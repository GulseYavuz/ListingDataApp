package com.yavuz.listingdataapp.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName ("images"      )  val imageUrl   : String? = null,
    @SerializedName ("title"       )  val title      : String? = null,
    @SerializedName ("description" )  val description: String? = null
)
