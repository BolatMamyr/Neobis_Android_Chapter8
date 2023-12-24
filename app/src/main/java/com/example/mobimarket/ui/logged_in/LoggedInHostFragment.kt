package com.example.mobimarket.ui.logged_in

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
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
        binding.bottomNav.menu.getItem(2).isEnabled = false

        setBottomNavCornerRadius()
        setUpNavController()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpNavController() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.loggedInHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment, R.id.walletFragment, R.id.chatsFragment, R.id.profileFragment -> {
                    showBottomNav(true)
                }

                else -> showBottomNav(false)
            }
        }

        binding.btnAddProduct.setOnClickListener {
            navController.navigate(R.id.action_global_addProductFragment)
        }
    }

    private fun setBottomNavCornerRadius() {
        val background = binding.bottomAppBar.background as MaterialShapeDrawable
        background.shapeAppearanceModel = background.shapeAppearanceModel
            .toBuilder()
            .setTopRightCorner(CornerFamily.ROUNDED, 40f)
            .setTopLeftCorner(CornerFamily.ROUNDED, 40f)
            .build()
    }

    private fun showBottomNav(value: Boolean) {
        binding.bottomAppBar.isVisible = value
        binding.btnAddProduct.isVisible = value
    }
}