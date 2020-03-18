package com.rudio.practice.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rudio.practice.R
import com.rudio.practice.data.PhoneNumber

class FragmentMain : FragmentBase() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val inputPhoneNumber = view.findViewById<TextInputLayout>(R.id.inputPhoneNumber)
        val editPhoneNumber = view.findViewById<TextInputEditText>(R.id.editPhoneNumber)
        val buttonTrack = view.findViewById<Button>(R.id.buttonTrack)
        buttonTrack.setOnClickListener {
            if (isValid(editPhoneNumber)) {
                setError(inputPhoneNumber, true)
                openFragmentTracking(PhoneNumber(editPhoneNumber.text.toString()))
            } else {
                setError(inputPhoneNumber, false)
            }
        }
        return view
    }

    private fun setError(inputLayout: TextInputLayout, isValid: Boolean) {
        if (isValid) inputLayout.error = null
        else inputLayout.error = getString(R.string.fragment_main_input_error)
    }

    private fun isValid(editText: TextInputEditText): Boolean {
        return editText.text !=  null && editText.text?.length != 0
    }

    private fun openFragmentTracking(phoneNumber: PhoneNumber) {
        val action = FragmentMainDirections.actionFragmentMainToFragmentTracking(phoneNumber)
        findNavController().navigate(action)
    }
}