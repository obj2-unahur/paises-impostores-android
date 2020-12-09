package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import android.content.Intent
import android.view.View
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun configurarVista() {
        listOf(
            Boton(botonDetalle) { Intent(this, VerDetallePaisActivity::class.java) },
            Boton(botonRelacionar) { Intent(this, RelacionarPaisesActivity::class.java) },
            Boton(botonEstadisticas) { Intent(this, VerEstadisticasGeneralesActivity::class.java) }
        ).forEach { (view, intent) ->
            view.setOnClickListener {
                startActivity(intent())
            }
        }
    }
}

data class Boton(
    val view: View,
    val intent: () -> Intent
)