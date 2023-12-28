package com.example.mobimarket.util

import android.content.Context
import android.graphics.Color
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mobimarket.R
import com.example.mobimarket.models.api.ErrorResponse
import com.example.mobimarket.ui.other.MyAlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Response

fun Fragment.navigate(resId: Int) = this.findNavController().navigate(resId)
fun Fragment.navigate(navDirections: NavDirections) =
    this.findNavController().navigate(navDirections)

fun Fragment.navigateUp() = this.findNavController().navigateUp()

inline fun Fragment.showAlert(func: MyAlertDialog.() -> Unit) =
    MyAlertDialog(this.requireContext()).apply {
        func()
        show()
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
    Snackbar.make(requireView(), "", Snackbar.LENGTH_SHORT).apply {
        view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = this.view as Snackbar.SnackbarLayout
        snackbarLayout.addView(errorView)

        val layoutParams = view.layoutParams as CoordinatorLayout.LayoutParams
        layoutParams.gravity = Gravity.TOP
        view.layoutParams = layoutParams
        show()
    }
}

fun Fragment.showSuccessMessage(text: String) {
    val successView = layoutInflater.inflate(R.layout.success_message, null)
    val message = successView.findViewById<TextView>(R.id.tvSuccessMessage)
    message.text = text
    Snackbar.make(requireView(), "", Snackbar.LENGTH_SHORT).apply {
        view.setBackgroundColor(Color.TRANSPARENT)
        val snackbarLayout = this.view as Snackbar.SnackbarLayout
        snackbarLayout.addView(successView)

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

fun Fragment.toast(msg: String) = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

inline fun <reified T> Response<ResponseBody>.getSuccessResult(): T {
    val gson = Gson()
    try {
        return gson.fromJson(body()!!.string(), T::class.java)
    } catch (e: Exception) {
        Log.e("Extensions", "getSuccessResult: $e")
    }
    return gson.fromJson(gson.toJson(body()), T::class.java)
}

fun Response<ResponseBody>.getErrorResult(): ErrorResponse {
    val gson = Gson()
    val errorBody = errorBody()?.string()
    val type = object : TypeToken<ErrorResponse>() {}.type
    return gson.fromJson(errorBody, type)
}

fun Exception.toErrorResponse(): ErrorResponse {
    return ErrorResponse(
        exceptionName = message ?: "",
        httpStatus = "",
        message = localizedMessage ?: "Error"
    )
}

fun Fragment.hideKeyboard(windowToken: IBinder?) {
    if (context != null && windowToken != null) {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
    }
}