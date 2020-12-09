package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.R
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Pais
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.GlideApp
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.setVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main

    private val observatorio = Observatorio()
    private val estaCargando = MutableLiveData<Boolean>()
    private val nombrePais: String get() =
        inputPais.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configurarVista()
    }

    private fun configurarVista() {
        estaCargando.observe(this) { estaCargando ->
            botonBuscar.setVisible(!estaCargando)
            progressBar.setVisible(estaCargando)
        }

        botonBuscar.setOnClickListener {
            launch { buscar() }
        }
    }

    private suspend fun buscar() {
        lateinit var pais: Pais
        conCarga { pais = observatorio.paisConNombre(nombrePais) }
        mostrar(pais)
    }

    private fun mostrar(pais: Pais) {
        GlideApp.with(this).load(pais.bandera).into(bandera)
    }

    private suspend fun conCarga(accion: suspend () -> Unit) {
        withContext(Dispatchers.IO) {
            estaCargando.postValue(true)
            accion()
            estaCargando.postValue(false)
        }
    }
}