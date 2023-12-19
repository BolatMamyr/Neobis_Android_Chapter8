package com.example.mobimarket.ui.reg

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentCreatePasswordBinding
import com.example.mobimarket.util.ValidationUtils
import com.example.mobimarket.util.enable
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.navigateUp
import com.example.mobimarket.util.showErrorMessage

class CreatePasswordFragment : Fragment() {

    private var _binding: FragmentCreatePasswordBinding? = null
    private val binding get() = _binding!!

    private var isPasswordHidden = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        binding.etCreatePassword.requestFocus()

        addTextChangedListener()
        setOnClickListeners()
    }

    private fun setToolbar() {
        binding.tbCreatePassword.btnBack.setOnClickListener { navigateUp() }
        binding.tbCreatePassword.tvToolbarTitle.text = getString(R.string.registration)
        binding.tbCreatePassword.btnBack.isVisible = true
        binding.tbCreatePassword.btnMenu.isVisible = true
        binding.tbCreatePassword.imgMenu.setImageResource(R.drawable.ic_eye_black)
    }

    private fun setOnClickListeners() {
        binding.btnCreatePassword.setOnClickListener {
            val password = binding.etCreatePassword.text.toString()
            if (ValidationUtils.isPasswordCorrect(password)) {
                val action = CreatePasswordFragmentDirections
                    .actionCreatePasswordFragmentToRepeatPasswordFragment(password)
                navigate(action)
            } else {
                showErrorMessage(getString(R.string.password_error))
            }
        }

        binding.tbCreatePassword.btnMenu.setOnClickListener {
            if (isPasswordHidden) {
                binding.tbCreatePassword.imgMenu.setImageResource(R.drawable.ic_eye_unhidden)
                showPassword(true)
                isPasswordHidden = false
            } else {
                binding.tbCreatePassword.imgMenu.setImageResource(R.drawable.ic_eye_black)
                showPassword(false)
                isPasswordHidden = true
            }
        }
    }

    private fun showPassword(value: Boolean) {
        // to save cursor position
        val selector = binding.etCreatePassword.selectionEnd
        binding.etCreatePassword.transformationMethod =
            if (value) null else PasswordTransformationMethod.getInstance()
        binding.etCreatePassword.setSelection(selector)
    }

    private fun addTextChangedListener() {
        binding.etCreatePassword.addTextChangedListener {
            val password = binding.etCreatePassword.text.toString()

            if (password.length > 7) {
                binding.btnCreatePassword.enable(true)
            } else {
                binding.btnCreatePassword.enable(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}