package com.example.mobimarket.util

import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.ui.other.MyAlertDialog
import com.google.android.material.snackbar.Snackbar

fun Fragment.navigate(resId: Int) = this.findNavController().navigate(resId)
fun Fragment.navigate(navDirections: NavDirections) =
    this.findNavController().navigate(navDirections)

fun Fragment.navigateUp() = this.findNavController().navigateUp()

inline fun Fragment.showAlert(func: MyAlertDialog.() -> Unit) =
    MyAlertDialog(this.requireContext()).apply {
        func()
    }.create()


fun Button.enable(value: Boolean) {
    isEnabled = value
    val color = if (value) {
        resources.getColor(R.color.maine_blue, null)
    } else {
        resources.getColor(R.color.grey, null)
    }
    setBackgroundColor(color)
}

fun Fragment.showErrorMessage(text: String) {
    val errorView = layoutInflater.inflate(R.layout.sign_in_error, null)
    val errorTextView = errorView.findViewById<TextView>(R.id.tvErrorMessage)
    errorTextView.text = text
    Snackbar.make(requireView(), "", Snackbar.LENGTH_LONG).apply {
        view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = this.view as Snackbar.SnackbarLayout
        snackbarLayout.addView(errorView)

        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        view.layoutParams = layoutParams
        show()
    }
}

fun Int.addZero(): String {
    if (this < 10) return "0$this"

    return "$this"
}