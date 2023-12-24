package com.example.mobimarket.ui.logged_in.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentEnterSmsBinding
import com.example.mobimarket.util.addZero
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EnterSmsFragment : Fragment() {

    private var _binding: FragmentEnterSmsBinding? = null
    private val binding get() = _binding!!

    private val timer = object : CountDownTimer(5000, 1000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            val sec = millisUntilFinished / 1000 + 1
            binding.tvTimer.text = "00:${sec.toInt().addZero()}"
        }

        override fun onFinish() {
            showResendButton()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEnterSmsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timer.start()
        binding.etEnterCode.requestFocus()
        setOnClickListeners()
        addTextChangedListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.btnResendCode.setOnClickListener {
            showTimer()
        }
    }

    private fun showResendButton() {
        binding.containerTimer.isInvisible = true
        binding.btnResendCode.isVisible = true
    }

    private fun showTimer() {
        timer.start()
        binding.btnResendCode.isVisible = false
        binding.containerTimer.isVisible = true
    }

    private fun showProgressBar() {
        binding.etEnterCode.isInvisible = true
        binding.pbEnterSms.isVisible = true
    }

    private fun showError() {
        binding.pbEnterSms.isVisible = false
        binding.etEnterCode.isVisible = true
        binding.etEnterCode.setText("")
        binding.etEnterCode.requestFocus()
        binding.tvError.isVisible = true
    }

    private fun addTextChangedListener() {
        binding.etEnterCode.addTextChangedListener {
            binding.tvError.isVisible = false

            val length = binding.etEnterCode.rawText.length
            if (length > 3) {
                val inputManager =
                    requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(binding.etEnterCode.windowToken, 0)

                checkCode(binding.etEnterCode.rawText)
            }
        }
    }

    private fun checkCode(code: String) {
        // todo:
        // imitating sms check
        showProgressBar()
        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            if (code == "0000") {
                findNavController().popBackStack(R.id.editProfileFragment, false)
            } else {
                showError()
            }
        }
    }
}