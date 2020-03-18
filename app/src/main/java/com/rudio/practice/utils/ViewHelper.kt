package com.rudio.practice.utils

import android.view.View

class ViewHelper {

    companion object {

        fun setVisibility(view: View, isVisible: Boolean) {
            if (isVisible) view.visibility = View.VISIBLE
            else view.visibility = View.GONE
        }
    }
}