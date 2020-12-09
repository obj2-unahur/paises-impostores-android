package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Pais
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.PaisNoEncontradoException
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.GlideApp
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.hide
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.setVisible
import kotlinx.android.synthetic.main.activity_ver_detalle_pais.*
import kotlinx.coroutines.launch

class VerDetallePaisActivity : BaseActivity(R.layout.activity_ver_detalle_pais) {
    private val observatorio = Observatorio()
    private val nombrePais: String get() = inputPais.text.toString()

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
            val pais = conCarga { observatorio.paisConNombre(nombrePais) }
            mostrar(pais)
        } catch(e: PaisNoEncontradoException) {
            mostrarCartelito(R.string.no_se_encontro_el_pais)
            resultados.hide()
        }
    }

    private fun mostrar(pais: Pais) {
        campoNombre.contenido = pais.nombre
        campoCodigo.contenido = pais.codigo
        campoPoblacion.contenido = pais.poblacion.toString()
        campoContinente.contenido = pais.continente

        GlideApp.with(this).load(pais.bandera).into(bandera)
    }
}