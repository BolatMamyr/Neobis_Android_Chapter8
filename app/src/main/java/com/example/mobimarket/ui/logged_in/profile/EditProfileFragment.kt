package com.example.mobimarket.ui.logged_in.profile

import android.os.Bundle
import android.util.Log
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
import com.example.mobimarket.util.showErrorMessage

const val TAG = "EditProfileFragment"

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private var image = ""
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            try {
                binding.ivAvatar.setImageURI(uri)
                image = uri?.toString() ?: ""
            } catch (e: Exception) {
                Log.e(TAG, "galleryLauncher: $e")
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
            val username = binding.etEditUsername.text.toString()
            if (username.isEmpty()) {
                binding.etEditUsername.requestFocus()
                showErrorMessage(getString(R.string.please_enter_username_to_proceed))
            } else {
                val action =
                    EditProfileFragmentDirections.actionEditProfileFragmentToEnterPhoneFragment(username)
                navigate(action)
            }
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