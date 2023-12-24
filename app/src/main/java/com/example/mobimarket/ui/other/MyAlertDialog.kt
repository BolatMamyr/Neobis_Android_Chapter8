package com.example.mobimarket.ui.other

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.mobimarket.databinding.AlertDialogBinding

class MyAlertDialog(context: Context) : AlertDialog(context) {

    private var binding: AlertDialogBinding? = null

    init {
        binding = AlertDialogBinding.inflate(LayoutInflater.from(context))
        setView(binding?.root)
    }

    fun setImage(img: Int) {
        binding?.ivAlert?.setImageResource(img)
    }

    fun setTitle(text: String) {
        binding?.tvAlert?.text = text
    }

    fun setPositiveButtonText(text: String) {
        binding?.btnAlertAccept?.text = text
    }

    fun setNegativeButtonText(text: String) {
        binding?.btnAlertCancel?.text = text
    }

    fun setPositiveButton(func: () -> Unit) {
        binding?.btnAlertAccept?.setOnClickListener {
            func()
        }
    }

    fun setNegativeButton(func: () -> Unit) {
        binding?.btnAlertCancel?.setOnClickListener {
            func()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }
}