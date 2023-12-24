package com.example.mobimarket.ui.logged_in.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentAddProductBinding
import com.example.mobimarket.ui.logged_in.product.rv.AddImageAdapter
import com.example.mobimarket.util.navigateUp
import com.example.mobimarket.util.showAlert

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val addImageAdapter = AddImageAdapter()

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                addImageAdapter.addItem(uri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpToolbar()
        setRv()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setRv() {
        binding.rvAddImages.apply {
            adapter = addImageAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        addImageAdapter.openGallery = {
            galleryLauncher.launch("image/*")
        }
    }

    private fun setUpToolbar() {
        binding.tbAddProd.apply {
            tvBack.text = getString(R.string.cancel)
            btnBackText.isVisible = true
            btnBackText.setOnClickListener { showAlert() }

            tvMenu.text = getString(R.string.done)
            btnMenuText.isVisible = true
            btnMenuText.setOnClickListener { createProduct() }
        }
    }

    private fun showAlert() {
        showAlert {
            setImage(R.drawable.ic_error)
            setTitle(getString(R.string.cancel_adding_product_alert))
            setPositiveButtonText(getString(R.string.yes))
            setNegativeButtonText(getString(R.string.no))

            setPositiveButton {
                navigateUp()
            }
            setNegativeButton {
                dismiss()
            }

            show()
        }
    }

    private fun createProduct() {
        // todo
    }

}