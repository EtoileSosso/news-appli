package com.example.dmii.extensions

import android.widget.EditText
import com.example.dmii.R

internal fun EditText.toDouble(): Double? {
    return text.toString().toDoubleOrNull() ?: run {
        error = context.getString(R.string.number_error)
        null
    }
}