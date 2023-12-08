package com.yavuz.listingdataapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yavuz.listingdataapp.R
import com.yavuz.listingdataapp.databinding.ItemProductBinding
import com.yavuz.listingdataapp.model.ProductResponse

class ProductAdapter(private var products: List<ProductResponse>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent, false
        )
        return ProductViewHolder(itemView)
    }*/

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val data = products[position]
        val firstImageUrl = data.images?.firstOrNull()


        holder.binding.titleId.text = data.title
        holder.binding.descriptionId.text = data.description
        Glide.with(holder.itemView.context)
            .load(firstImageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .into(holder.binding.productImageId)    }

    override fun getItemCount(): Int {
        return products.size
    }

/*    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descriptionProduct: TextView = itemView.findViewById(R.id.descriptionId)
        val imageProduct: ImageView = itemView.findViewById(R.id.productImageId)
        val titleProduct: TextView = itemView.findViewById(R.id.titleId)

    }*/
}