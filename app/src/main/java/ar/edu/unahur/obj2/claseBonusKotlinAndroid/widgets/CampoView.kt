package ar.edu.unahur.obj2.claseBonusKotlinAndroid.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.withAttributes
import kotlinx.android.synthetic.main.widget_campo.view.*

// Custom view que representa una etiqueta y un valor (contenido) asociado a ella.
// Su maquetado está en el archivo widget_campo.xml

class CampoView : FrameLayout {
    init {
        LayoutInflater
            .from(context)
            .inflate(R.layout.widget_campo, this, true)
    }

    var contenido: String
        get() = labelValor.text.toString()
        set(value) {
            labelValor.text = value
        }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr) {

        attrs.withAttributes(this) {
            labelNombre.text = it.getText("label")
        }
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
}