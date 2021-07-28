package ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils

import android.content.Context
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R

fun Boolean.siONo(context: Context) = context.resources.getString(
    if (this) R.string.si else R.string.no
)