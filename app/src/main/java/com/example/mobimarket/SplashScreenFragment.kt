package com.example.mobimarket

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobimarket.managers.UserManager
import com.example.mobimarket.util.navigate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    @Inject
    lateinit var userManager: UserManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val action = if (userManager.accessToken.isEmpty()) {
            R.id.action_splashScreenFragment_to_signInFragment
        } else {
            R.id.action_splashScreenFragment_to_loggedInHostFragment
        }

        Handler(Looper.getMainLooper()).postDelayed({
            navigate(action)
        }, 2000)
    }

}