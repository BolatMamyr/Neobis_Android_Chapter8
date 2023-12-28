package com.example.mobimarket.ui.logged_in.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentLikedProdsBinding
import com.example.mobimarket.models.product.ProductImage
import com.example.mobimarket.models.product.ProductMini
import com.example.mobimarket.ui.logged_in.product.rv.ItemDecorator
import com.example.mobimarket.ui.logged_in.product.rv.ProductsAdapter
import com.example.mobimarket.util.navigateUp

class LikedProdsFragment : Fragment() {

    private var _binding: FragmentLikedProdsBinding? = null
    private val binding get() = _binding!!

    private val mAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLikedProdsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setRV()
        setTestData()
    }

    private fun showNothingToShow() {
        binding.rvLikedProds.isVisible = false
        binding.ivEmpty.isVisible = true
        binding.tvEmpty.isVisible = true
    }

    private fun setUpToolbar() {
        binding.tbLikedProds.apply {
            tvToolbarTitle.text = getString(R.string.liked)
            btnBack.isVisible = true
            btnBack.setOnClickListener { navigateUp() }
        }
    }

    private fun setTestData() {
        val list = mutableListOf<ProductMini>()
        val id = 0
        val name = "Product"
        val price = 1000
        val url = "https://buffer.com/cdn-cgi/image/w=1000,fit=contain,q=90,f=auto/library/content/images/size/w600/2023/10/free-images.jpg"
        val likes = 10
        for (i in 0..10) {
            val prod = ProductMini(id + i, "$name $i", price * i, listOf(ProductImage(id + i, url)), likes * i)
            list.add(prod)
        }
        mAdapter.updateData(list)
    }

    private fun setRV() {
        binding.rvLikedProds.apply {
            adapter = mAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            val x = (resources.displayMetrics.density * 4).toInt()
            addItemDecoration(ItemDecorator(x))
        }
    }

}