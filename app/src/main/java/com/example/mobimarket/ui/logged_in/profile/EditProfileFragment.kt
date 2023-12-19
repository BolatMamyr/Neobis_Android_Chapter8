package com.example.mobimarket.ui.logged_in.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentEditProfileBinding
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.navigateUp

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        binding.containerAddNumber.setOnClickListener {
            navigate(R.id.action_editProfileFragment_to_enterPhoneFragment)
        }
    }

    private fun setToolbar() {
        binding.tbEditProfile.btnBackText.isVisible = true
        binding.tbEditProfile.tvBack.text = getString(R.string.cancel)
        binding.tbEditProfile.btnMenuText.isVisible = true
        binding.tbEditProfile.tvMenu.text = getString(R.string.done)

        binding.tbEditProfile.btnBackText.setOnClickListener { navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}