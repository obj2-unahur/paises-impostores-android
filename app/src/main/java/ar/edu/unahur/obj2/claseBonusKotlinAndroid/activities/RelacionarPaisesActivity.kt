package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.PaisNoEncontradoException
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.hide
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.setVisible
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.siONo
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.widgets.CampoView
import kotlinx.android.synthetic.main.activity_relacionar_paises.*
import kotlinx.coroutines.launch

class RelacionarPaisesActivity : BaseActivity(R.layout.activity_relacionar_paises) {
    private val nombrePais1: String get() = inputPais1.text.toString()
    private val nombrePais2: String get() = inputPais2.text.toString()

    override fun configurarVista() {
        estaCargando.observe(this) { estaCargando ->
            progressBar.setVisible(estaCargando)
            botonBuscar.setVisible(!estaCargando)
            resultados.setVisible(!estaCargando)
        }

        botonBuscar.setOnClickListener {
            esconderTeclado()
            launch { buscar() }
        }
    }

    private suspend fun buscar() {
        try {
            cargarPregunta(campoSonLimitrofes) {
                Observatorio.sonLimitrofes(nombrePais1, nombrePais2)
            }

            cargarPregunta(campoNecesitanTraduccion) {
                Observatorio.necesitanTraduccion(nombrePais1, nombrePais2)
            }

            cargarPregunta(campoSonPotencialesAliados) {
                Observatorio.sonPotencialesAliados(nombrePais1, nombrePais2)
            }
        } catch(e: PaisNoEncontradoException) {
            mostrarCartelito(R.string.no_se_encontro_alguno_de_los_paises)
            resultados.hide()
        }
    }

    private suspend fun cargarPregunta(vista: CampoView, carga: () -> Boolean) {
        vista.contenido = conCarga { carga() }.siONo(this)
    }
}