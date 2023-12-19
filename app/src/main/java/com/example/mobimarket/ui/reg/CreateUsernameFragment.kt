package com.example.mobimarket.ui.reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentCreateUsernameBinding
import com.example.mobimarket.util.ValidationUtils
import com.example.mobimarket.util.enable
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.navigateUp
import com.example.mobimarket.util.showErrorMessage

class CreateUsernameFragment : Fragment() {

    private var _binding: FragmentCreateUsernameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateUsernameBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbCreateLogin.tvToolbarTitle.text = getString(R.string.registration)
        binding.tbCreateLogin.btnBack.isVisible = true
        binding.tbCreateLogin.btnBack.setOnClickListener { navigateUp() }

        addTextChangedListener(binding.etNewUsername)
        addTextChangedListener(binding.etEmail)

        binding.btnCreateUsernameFurther.setOnClickListener {
            val username = binding.etNewUsername.text.toString()
            val email = binding.etEmail.text.toString()
            validate(username, email)
        }
    }

    private fun validate(username: String, email: String) {
        if (ValidationUtils.isEmailValid(email)) {
            navigate(R.id.action_createUsernameFragment_to_createPasswordFragment)
        } else {
            showErrorMessage(getString(R.string.invalid_email_format))
        }
    }

    private fun addTextChangedListener(et: EditText) {
        et.addTextChangedListener {
            val username = binding.etNewUsername.text.toString()
            val password = binding.etEmail.getText()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                binding.btnCreateUsernameFurther.enable(true)
            } else {
                binding.btnCreateUsernameFurther.enable(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}