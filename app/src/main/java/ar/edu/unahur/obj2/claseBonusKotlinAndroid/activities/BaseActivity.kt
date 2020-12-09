package ar.edu.unahur.obj2.claseBonusKotlinAndroid.activities

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity(@LayoutRes private val layoutId: Int) : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main
    protected val estaCargando = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        configurarVista()
    }

    protected open fun configurarVista() { }

    protected suspend fun <T> conCarga(accion: suspend () -> T): T {
        estaCargando.value =true

        try {
            return withContext(Dispatchers.IO) { accion() }
        } finally {
            estaCargando.value = false
        }
    }

    protected fun esconderTeclado() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}