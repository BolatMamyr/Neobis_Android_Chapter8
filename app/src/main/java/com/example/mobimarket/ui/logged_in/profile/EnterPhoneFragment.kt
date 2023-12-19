package com.example.mobimarket.ui.logged_in.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.mobimarket.databinding.FragmentEnterPhoneBinding

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

        addTextChangedListener()
    }

    private fun addTextChangedListener() {
        binding.etPhone.addTextChangedListener {
            it?.toString()?.let { text ->
                val openBracketIndex = text.indexOf('(')
                val closeBracketIndex = text.indexOf(')')


            }
        }
    }
}