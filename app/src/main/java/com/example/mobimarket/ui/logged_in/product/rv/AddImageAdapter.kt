package com.example.mobimarket.ui.logged_in.product.rv

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.mobimarket.databinding.ItemAddImageBinding
import com.example.mobimarket.databinding.ItemAddedImgBinding

class AddImageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val list = arrayListOf<ImageItem>(ImageItem.AddImage)

    var openGallery: (() -> Unit)? = null

    fun addItem(uri: Uri) {
        list.add(ImageItem.Image(uri))
        notifyItemInserted(list.size - 1)
    }

    fun getList() = list.filterIsInstance<ImageItem.Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val binding = ItemAddImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AddImageViewHolder(binding)
            }

            else -> {
                val binding = ItemAddedImgBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                AddedImageViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AddImageViewHolder -> {
                holder.setClickListener()
            }

            else -> {
                holder as AddedImageViewHolder
                val item = list[position] as? ImageItem.Image
                item?.uri?.let { uri ->
                    holder.setImage(uri)
                }

                if (position == list.size - 1) {
                    holder.addMarginEnd()
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ImageItem.AddImage -> {
                0
            }

            is ImageItem.Image -> {
                1
            }
        }
    }


    inner class AddImageViewHolder(private val binding: ItemAddImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setClickListener() {
            binding.cvAddImg.setOnClickListener {
                openGallery?.invoke()
            }
        }
    }

    inner class AddedImageViewHolder(private val binding: ItemAddedImgBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setImage(uri: Uri) {
            binding.ivAddedImg.setImageURI(uri)
        }

        fun addMarginEnd() {
            MarginLayoutParams(binding.cvAddedImg.layoutParams).also { layoutParams ->
                layoutParams.marginEnd = 6
                binding.cvAddedImg.layoutParams = layoutParams
            }
        }
    }

}