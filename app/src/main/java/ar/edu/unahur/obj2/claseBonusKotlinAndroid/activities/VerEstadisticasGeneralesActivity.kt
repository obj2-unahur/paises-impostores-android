package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.setVisible
import kotlinx.android.synthetic.main.activity_ver_estadisticas_generales.*
import kotlinx.coroutines.launch

class VerEstadisticasGeneralesActivity : BaseActivity(R.layout.activity_ver_estadisticas_generales) {
    private val observatorio = Observatorio()

    override fun configurarVista() {
        estaCargando.observe(this) { estaCargando ->
            progressBar.setVisible(estaCargando)
            resultados.setVisible(!estaCargando)
        }

        launch { cargar() }
    }

    private suspend fun cargar() {
        val paisesConMasPoblacion = conCarga { observatorio.paisesConMasPoblacion() }
        val continenteConMasHabitantes = conCarga { observatorio.continenteConMasHabitantes() }

        campoPaisesConMasPoblacion.contenido = paisesConMasPoblacion.joinToString("\n")
        campoContinenteConMasHabitantes.contenido = continenteConMasHabitantes
    }
}