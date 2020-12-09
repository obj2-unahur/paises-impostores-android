package ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils

import android.view.View

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}