package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.conDosDecimales
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.setVisible
import kotlinx.android.synthetic.main.activity_ver_estadisticas_generales.*
import kotlinx.coroutines.launch

class VerEstadisticasGeneralesActivity : BaseActivity(R.layout.activity_ver_estadisticas_generales) {
    override fun configurarVista() {
        estaCargando.observe(this) { estaCargando ->
            progressBar.setVisible(estaCargando)
            resultados.setVisible(!estaCargando)
        }

        launch { cargar() }
    }

    private suspend fun cargar() {
        val codigoIso5PaisesMasDensos = conCarga { Observatorio.codigoIso5PaisesMasDensos() }
        val continenteConMasPlurinacionales = conCarga { Observatorio.continenteConMasPlurinacionales() }
        val promedioDeDensidadDePaisesIsla = conCarga { Observatorio.promedioDeDensidadDePaisesIsla() }

        campoPaisesMasDensos.contenido = codigoIso5PaisesMasDensos.joinToString("\n")
        campoContinenteConMasPlurinacionales.contenido = continenteConMasPlurinacionales
        campoPromedioDensidadPoblacionalPaisesIsla.contenido = promedioDeDensidadDePaisesIsla.conDosDecimales()
    }
}