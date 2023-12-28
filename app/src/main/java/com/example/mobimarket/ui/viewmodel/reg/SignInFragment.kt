package com.example.mobimarket.ui.viewmodel.reg

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentSignInBinding
import com.example.mobimarket.managers.UserManager
import com.example.mobimarket.models.api.ApiState
import com.example.mobimarket.models.reg.SignInRequestBody
import com.example.mobimarket.util.enable
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.showErrorMessage
import com.example.mobimarket.util.showSuccessMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<RegistrationViewModel>()

    @Inject
    lateinit var userManager: UserManager

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
        collectSignInState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val username = binding.etUsername.text.toString().trim()
        val password = binding.pvPassword.getText().trim()
        val body = SignInRequestBody(username = username, password = password)
        viewModel.signIn(body)
    }

    private fun collectSignInState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signInState.collectLatest {
                when (it) {
                    ApiState.Loading -> showProgressBar(true)
                    is ApiState.Success -> {
                        showProgressBar(false)
                        showSuccessMessage(getString(R.string.successfully_signed_in))

                        val accessToken = it.data.accessToken ?: ""
                        val refreshToken = it.data.refreshToken ?: ""

                        userManager.saveTokens(accessToken, refreshToken)

                        navigate(R.id.action_signInFragment_to_loggedInHostFragment)
                    }
                    is ApiState.Error -> {
                        showProgressBar(false)
                        showErrorMessage(getString(R.string.wrong_login_or_password))
                    }
                }
            }
        }
    }

    private fun showProgressBar(value: Boolean) {
        binding.btnSignIn.isInvisible = value
        binding.pbSignIn.isVisible = value
    }
}