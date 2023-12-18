package com.example.mobimarket.ui.logged_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mobimarket.R
import com.example.mobimarket.databinding.FragmentLoggedInHostBinding
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class LoggedInHostFragment : Fragment() {

    private var _binding: FragmentLoggedInHostBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoggedInHostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.bottomNav.background = null
        binding.bottomNav.menu.getItem(2).isEnabled = false

        // set corner radius for BottomAppBar
        val background = binding.bottomAppBar.background as MaterialShapeDrawable
        background.shapeAppearanceModel = background.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, 40f)
            .setTopLeftCorner(CornerFamily.ROUNDED, 40f)
            .build()

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.loggedInHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        // todo: check both navController and findNavController()
        binding.bottomNav.setupWithNavController(navController)
    }

}