package com.example.mobimarket.ui.reg

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentRepeatPasswordBinding
import com.example.mobimarket.models.reg.RegisterRequestBody
import com.example.mobimarket.models.ApiState
import com.example.mobimarket.util.Constants
import com.example.mobimarket.util.Exceptions
import com.example.mobimarket.util.enable
import com.example.mobimarket.util.navigateUp
import com.example.mobimarket.util.showErrorMessage
import com.example.mobimarket.util.showSuccessMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RepeatPasswordFragment : Fragment() {

    private var _binding: FragmentRepeatPasswordBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<RepeatPasswordFragmentArgs>()

    private var isPasswordHidden = true

    private val viewModel by viewModels<RegistrationViewModel>()

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
        collectRegistrationState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                val body = RegisterRequestBody(
                    username = args.username,
                    email = args.email,
                    password = password
                )
                viewModel.register(body)
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

    private fun collectRegistrationState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.registrationState.collectLatest { res->
                when (res) {
                    ApiState.Loading -> {
                        showProgressBar(true)
                    }
                    is ApiState.Error -> {
                        showProgressBar(false)
                        showErrorMessage(res.error.message)

                        if (res.error.exceptionName.contains(Exceptions.userAlreadyExistException)) {
                            delay(Constants.LENGTH_SHORT)
                            findNavController().popBackStack(R.id.createUsernameFragment, false)
                        }
                    }
                    is ApiState.Success -> {
                        showSuccessMessage(getString(R.string.reg_success))
                        delay(Constants.LENGTH_SHORT)
                        findNavController().popBackStack(R.id.signInFragment, false)
                    }
                }
            }
        }
    }

    private fun showProgressBar(value: Boolean) {
        binding.btnCreatePassword.isInvisible = value
        binding.pbRegister.isVisible = value
    }
}