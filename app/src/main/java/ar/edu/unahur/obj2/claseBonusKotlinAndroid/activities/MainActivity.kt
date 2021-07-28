package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import android.content.Intent
import android.view.View
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(R.layout.activity_main) {
    override fun configurarVista() {
        listOf(
            Pair(botonDetalle, { Intent(this, VerDetallePaisActivity::class.java) }),
            Pair(botonRelacionar, { Intent(this, RelacionarPaisesActivity::class.java) }),
            Pair(botonEstadisticas, { Intent(this, VerEstadisticasGeneralesActivity::class.java) })
        ).forEach { (view, intent) ->
            view.setOnClickListener {
                startActivity(intent())
            }
        }
    }
}
