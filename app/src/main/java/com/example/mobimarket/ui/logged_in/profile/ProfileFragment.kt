package com.example.mobimarket.ui.logged_in.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentProfileBinding
import com.example.mobimarket.util.navigate

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        setOnClickListeners()
    }

    private fun setToolbar() {
        binding.tbProfile.tvToolbarTitle.text = getString(R.string.profile)
        binding.tbProfile.btnMenuText.isVisible = true
        binding.tbProfile.tvMenu.text = getString(R.string.edit_short)
    }

    private fun setOnClickListeners() {
        binding.tbProfile.btnMenuText.setOnClickListener {
            navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.btnLiked.setOnClickListener {
            navigate(R.id.action_profileFragment_to_likedProdsFragment)
        }
        binding.btnMyProds.setOnClickListener {
            navigate(R.id.action_profileFragment_to_myProdsFragment)
        }
        binding.btnFinishReg.setOnClickListener {
            navigate(R.id.action_profileFragment_to_finishRegistrationFragment)
        }
    }

}