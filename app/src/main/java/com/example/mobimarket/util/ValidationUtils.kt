package com.example.mobimarket.util

import android.util.Patterns


class ValidationUtils {
    companion object {
        fun isPasswordCorrect(psw: String): Boolean {
            if (psw.length < 8) return false

            val containsDigit = containsDigit(psw)
            val containsLetter = containsLetter(psw)

            return containsDigit && containsLetter
        }

        fun containsLetter(input: String): Boolean {
            for (char in input) {
                if (char.isLetter()) return true
            }
            return false
        }
        fun containsDigit(input: String): Boolean {
            for (char in input) {
                if (char.isDigit()) return true
            }
            return false
        }

        fun isEmailValid(input: String): Boolean {
            if (input.isEmpty()) return false
            return Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }
}