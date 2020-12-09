package ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View

fun AttributeSet?.withAttributes(view: View, apply: (AttributeMap) -> (Unit)) {
    if (this == null) return

    val context = view.context
    val viewName = view.javaClass.simpleName
    val styleables = context.getStyleablesOf(viewName) ?: return
    val attributes = context.obtainStyledAttributes(this, styleables)

    apply(AttributeMap(attributes, view))

    attributes.recycle()
}

class AttributeMap(private val array: TypedArray, private val view: View) {
    fun getText(name: String): CharSequence {
        return array.getText(styleableFor(name))
    }

    fun getString(name: String, defaultValue: String = ""): String {
        return array.getString(styleableFor(name)) ?: defaultValue
    }

    fun getBoolean(name: String, defaultValue: Boolean = false): Boolean {
        return array.getBoolean(styleableFor(name), defaultValue)
    }

    fun getInt(name: String, defaultValue: Int = 0): Int {
        return array.getInt(styleableFor(name), defaultValue)
    }

    fun getFloat(name: String, defaultValue: Float = 0f): Float {
        return array.getFloat(styleableFor(name), defaultValue)
    }

    fun getResourceId(name: String, defaultValue: Int = -1): Int {
        return array.getResourceId(styleableFor(name), defaultValue)
    }

    fun getDrawable(name: String): Drawable? {
        return array.getDrawable(styleableFor(name))
    }

    fun getDimension(name: String, defaultValue: Float = 0f): Float {
        return array.getDimension(styleableFor(name), defaultValue)
    }

    private fun styleableFor(propertyName: String): Int {
        return view.getStyleable(propertyName) ?: -1
    }
}

private fun View.getStyleable(propertyName: String): Int? {
    val viewName = javaClass.simpleName
    val name = "${viewName}_$propertyName"

    return context.getStyleableField(name) as Int?
}

private fun Context.getStyleablesOf(name: String): IntArray? {
    return getStyleableField(name) as IntArray?
}

private fun Context.getStyleableField(name: String): Any? {
    return try {
        val fields = Class.forName("$packageName.R\$styleable").fields

        fields.find { it.name == name }?.get(null)
    } catch (t: Throwable) {
        null
    }
}