package com.example.mobimarket.ui.logged_in.product.rv

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mobimarket.R
import com.example.mobimarket.databinding.ItemProductBinding
import com.example.mobimarket.models.product.ProductMini

class ProductsViewHolder(val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setView(item: ProductMini) {
        val name = item.name
        val price = item.price
        val likes = item.likes
        val imageUrl = item.images[0].imageUrl

        binding.tvProdName.text = name
        binding.tvPrice.text = price.toString()
        binding.tvLikes.text = likes.toString()

        Glide.with(binding.root)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.drawable.img_placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.e("MyLog", "Load failed", e)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(binding.ivProduct)
    }

}