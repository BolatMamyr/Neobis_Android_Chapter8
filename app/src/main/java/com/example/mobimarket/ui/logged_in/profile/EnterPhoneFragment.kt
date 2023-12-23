package com.example.mobimarket.ui.logged_in.profile

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentEnterPhoneBinding
import com.example.mobimarket.util.navigate

class EnterPhoneFragment : Fragment() {

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterPhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etPhone.requestFocus()
        setOnClickListeners()
        addTextChangedListener()
    }

    private fun addTextChangedListener() {
        binding.etPhone.addTextChangedListener {
            val length = binding.etPhone.rawText.length
            enableButton(length > 10)
            if (length > 10) {
                val inputManager =
                    requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(binding.etPhone.windowToken, 0)
            }
        }
    }

    private fun enableButton(value: Boolean) {
        binding.btnEnterPhone.isEnabled = value
        val color = if (value) {
            resources.getColor(R.color.maine_blue, null)
        } else {
            resources.getColor(R.color.grey, null)
        }
        binding.btnEnterPhone.setBackgroundColor(color)
    }

    private fun setOnClickListeners() {
        binding.btnEnterPhone.setOnClickListener {
            // todo: check phone
            navigate(R.id.action_enterPhoneFragment_to_enterSmsFragment)
        }
    }


}