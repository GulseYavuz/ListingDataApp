package com.yavuz.listingdataapp.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products"           )   var products            : List<ProductResponse> ,
    @SerializedName("id"                 )   var id                  : Int?                = null,
    @SerializedName("title"              )   var title               : String?             = null,
    @SerializedName("description"        )   var description         : String?             = null,
    @SerializedName("price"              )   var price               : Int?                = null,
    @SerializedName("discountPercentage" )   var discountPercentage  : Float?              = null,
    @SerializedName("rating"             )   var rating              : Float?              = null,
    @SerializedName("stock"              )   var stock               : Int?                = null,
    @SerializedName("brand"              )   var brand               : String?             = null,
    @SerializedName("category"           )   var category            : String?             = null,
    @SerializedName("thumbnail"          )   var thumbnail           : String?             = null,
    @SerializedName("images"             )   var images              : List<String>?       = null,

    )
