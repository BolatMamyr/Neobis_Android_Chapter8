package com.example.mobimarket.ui.reg

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentSignInBinding
import com.example.mobimarket.util.enable
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.showErrorMessage
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextChangedListener(binding.etUsername)
        addTextChangedListener(binding.pvPassword.getEditText())

        setButtonClickListeners()
    }

    private fun setButtonClickListeners() {
        binding.btnSignIn.setOnClickListener {
            signIn()
        }
        binding.btnRegister.setOnClickListener {
            navigate(R.id.action_signInFragment_to_registerLoginFragment)
        }
    }

    private fun addTextChangedListener(et: EditText) {
        et.addTextChangedListener {
            val username = binding.etUsername.text.toString()
            val password = binding.pvPassword.getText()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                binding.btnSignIn.enable(true)
            } else {
                binding.btnSignIn.enable(false)
            }
        }
    }

    private fun signIn() {
//        showErrorMessage(getString(R.string.wrong_login_or_password))
        navigate(R.id.action_signInFragment_to_loggedInHostFragment)
    }

}