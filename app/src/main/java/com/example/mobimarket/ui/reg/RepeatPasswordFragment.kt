package com.example.mobimarket.ui.reg

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentCreatePasswordBinding
import com.example.mobimarket.databinding.FragmentRepeatPasswordBinding
import com.example.mobimarket.util.ValidationUtils
import com.example.mobimarket.util.enable
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.navigateUp
import com.example.mobimarket.util.showErrorMessage

class RepeatPasswordFragment : Fragment() {

    private var _binding: FragmentRepeatPasswordBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<RepeatPasswordFragmentArgs>()

    private var isPasswordHidden = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepeatPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()

        binding.etCreatedPassword.setText(args.password)
        binding.etRepeatPassword.requestFocus()

        setOnClickListeners()
        addTextChangedListener()
    }

    private fun setToolbar() {
        binding.tbRepeatPassword.btnBack.setOnClickListener { navigateUp() }
        binding.tbRepeatPassword.tvToolbarTitle.text = getString(R.string.registration)
        binding.tbRepeatPassword.btnBack.isVisible = true
        binding.tbRepeatPassword.btnMenu.isVisible = true
        binding.tbRepeatPassword.imgMenu.setImageResource(R.drawable.ic_eye_black)
    }

    private fun setOnClickListeners() {
        binding.btnCreatePassword.setOnClickListener {
            val password = binding.etRepeatPassword.text.toString()

            if (args.password != password) {
                binding.tvRepeatPasswordError.isVisible = true
            } else {
                // todo: register
                findNavController().popBackStack(R.id.signInFragment, false)
            }
        }

        binding.tbRepeatPassword.btnMenu.setOnClickListener {
            if (isPasswordHidden) {
                binding.tbRepeatPassword.imgMenu.setImageResource(R.drawable.ic_eye_unhidden)
                showPassword(true)
                isPasswordHidden = false
            } else {
                binding.tbRepeatPassword.imgMenu.setImageResource(R.drawable.ic_eye_black)
                showPassword(false)
                isPasswordHidden = true
            }
        }
    }

    private fun showPassword(value: Boolean) {
        binding.etCreatedPassword.transformationMethod =
            if (value) null else PasswordTransformationMethod.getInstance()

        // to save cursor position
        val selector = binding.etRepeatPassword.selectionEnd
        binding.etRepeatPassword.transformationMethod =
            if (value) null else PasswordTransformationMethod.getInstance()
        binding.etRepeatPassword.setSelection(selector)
    }

    private fun addTextChangedListener() {
        binding.etRepeatPassword.addTextChangedListener {
            binding.tvRepeatPasswordError.isVisible = false
            val password = binding.etRepeatPassword.text.toString()

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