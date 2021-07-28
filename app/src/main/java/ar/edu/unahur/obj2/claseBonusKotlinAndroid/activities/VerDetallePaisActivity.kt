package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Pais
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.PaisNoEncontradoException
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.GlideApp
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.conDosDecimales
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.hide
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.setVisible
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.siONo
import kotlinx.android.synthetic.main.activity_ver_detalle_pais.*
import kotlinx.coroutines.launch

class VerDetallePaisActivity : BaseActivity(R.layout.activity_ver_detalle_pais) {
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
            val pais = conCarga { Observatorio.buscarPaisDeNombre(nombrePais) }
            mostrar(pais)
        } catch(e: PaisNoEncontradoException) {
            mostrarCartelito(R.string.no_se_encontro_el_pais)
            resultados.hide()
        }
    }

    private fun mostrar(pais: Pais) {
        GlideApp.with(this).load(pais.bandera).fitCenter().into(bandera)

        campoNombre.contenido = pais.nombre
        campoCodigo.contenido = pais.codigoISO3
        campoPoblacion.contenido = pais.poblacion.toString()
        campoContinente.contenido = pais.continente

        campoEsPlurinacional.contenido = pais.esPlurinacional().siONo(this)
        campoEsUnaIsla.contenido = pais.esUnaIsla().siONo(this)
        campoDensidadPoblacional.contenido = pais.densidadPoblacional().conDosDecimales()
        campoVecinoMasPoblado.contenido = pais.vecinoMasPoblado().nombre
    }
}