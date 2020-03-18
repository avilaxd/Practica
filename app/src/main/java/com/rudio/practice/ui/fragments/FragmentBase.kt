package com.rudio.practice.ui.fragments

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.rudio.practice.R

open class FragmentBase : Fragment() {
    private var dialog: AlertDialog? = null

    protected fun exitFragment(error: String) {
        showToast(error)
        activity?.onBackPressed()
    }

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    protected fun showDialog() {
        dismissDialog()
        dialog = AlertDialog.Builder(requireContext()).create()
        dialog?.setView(View.inflate(requireContext(), R.layout.dialog_sms, null))
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    protected fun dismissDialog() {
        if (dialog?.isShowing == true) dialog?.dismiss()
    }
}