package com.example.mobimarket.ui.logged_in.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentAddProductBinding
import com.example.mobimarket.models.api.ApiState
import com.example.mobimarket.models.product.AddProductRequestBody
import com.example.mobimarket.ui.logged_in.product.rv.AddImageAdapter
import com.example.mobimarket.util.navigateUp
import com.example.mobimarket.util.showAlert
import com.example.mobimarket.util.showErrorMessage
import com.example.mobimarket.util.showSuccessMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<AddProductViewModel>()

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
        collectAddProductState()
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
                dismiss()
                navigateUp()
            }
            setNegativeButton {
                dismiss()
            }
        }
    }

    private fun createProduct() {
        val images = addImageAdapter.getList()
        // todo: make max price not to go beyond integer max size
        val price = binding.etPrice.text.toString()
        val name = binding.etName.text.toString()
        val overview = binding.etOverview.text.toString()
        val desc = binding.etDesc.text.toString()

        if (images.isEmpty() || price.isEmpty() || name.isEmpty() || overview.isEmpty() || desc.isEmpty()) {
            showErrorMessage(getString(R.string.please_fill_all_the_required_fields))
            return
        }

        val body = AddProductRequestBody(
            name = name,
            shortDescription = overview,
            fullDescription = desc,
            price = price.toInt()
        )
        viewModel.addProduct(body)
    }

    private fun collectAddProductState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.addProductState.collectLatest {
                when (it) {
                    ApiState.Loading -> showProgressBar(true)
                    is ApiState.Success -> {
                        showSuccessMessage(getString(R.string.product_added))
                        navigateUp()
                    }
                    is ApiState.Error -> {
                        showProgressBar(false)
                        showErrorMessage(it.error.message)
                    }
                }
            }
        }
    }

    private fun showProgressBar(value: Boolean) {
        binding.pbAddProd.isVisible = value
        binding.tbAddProd.btnMenuText.isEnabled = !value
    }
}