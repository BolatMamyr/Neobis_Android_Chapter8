package com.example.mobimarket.ui.logged_in.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentEditProfileBinding
import com.example.mobimarket.util.navigate
import com.example.mobimarket.util.navigateUp

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            try {
                binding.ivAvatar.setImageURI(uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

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
        setOnClickListeners()
        binding.etEditBirthDate.addTextChangedListener {
            binding.birthdateHint.isVisible = binding.etEditBirthDate.rawText.isEmpty()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setOnClickListeners() {
        binding.btnChoosePhoto.setOnClickListener {
            galleryLauncher.launch("image/*")
        }
        binding.containerAddNumber.setOnClickListener {
            navigate(R.id.action_editProfileFragment_to_enterPhoneFragment)
        }

        binding.tbEditProfile.btnMenuText.setOnClickListener {
            saveProfileEdit()
        }
    }

    private fun saveProfileEdit() {
        // todo: save to SharedPrefs
        findNavController().popBackStack(R.id.profileFragment, false)
    }

    private fun setToolbar() {
        binding.tbEditProfile.btnBackText.isVisible = true
        binding.tbEditProfile.tvBack.text = getString(R.string.cancel)
        binding.tbEditProfile.btnMenuText.isVisible = true
        binding.tbEditProfile.tvMenu.text = getString(R.string.done)

        binding.tbEditProfile.btnBackText.setOnClickListener { navigateUp() }
    }
}