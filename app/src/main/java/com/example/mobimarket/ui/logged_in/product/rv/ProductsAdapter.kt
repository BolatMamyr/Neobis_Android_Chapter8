package com.example.mobimarket.ui.logged_in.product.rv

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mobimarket.databinding.ItemProductBinding
import com.example.mobimarket.models.product.ProductMini

class ProductsAdapter : RecyclerView.Adapter<ProductsViewHolder>() {

    private var list = mutableListOf<ProductMini>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.setView(list[position])
    }

    override fun getItemCount() = list.size

    fun updateData(newList: List<ProductMini>) {
        val diff = DiffUtil.calculateDiff(ProductsDiff(list, newList))
        list.clear()
        list = newList.toMutableList()
        diff.dispatchUpdatesTo(this)
        Log.d("MyLog", newList.size.toString())
    }

    inner class ProductsDiff(
        private val oldList: List<ProductMini>,
        private val newList: List<ProductMini>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}