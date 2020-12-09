package ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.adapters.PaisAdapter
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.sumByLong

class Observatorio(private val api: RestCountriesAPI = RestCountriesAPI()) {
    private val adapter = PaisAdapter(api)

    fun paisConNombre(nombre:String): Pais {
        try {
            return adapter.adaptar(api.buscarPaisesPorNombre(nombre).first())
        } catch (e: NoSuchElementException) {
            throw PaisNoEncontradoException()
        }
    }

    fun relacionEntre(unPais: String, otroPais: String): RelacionPaises {
        val pais1 = paisConNombre(unPais)
        val pais2 = paisConNombre(otroPais)

        return RelacionPaises(
            pais1.esLimitrofe(pais2),
            pais1.necesitaTraduccionCon(pais2),
            pais1.sonAliadosPotenciales(pais2)
        )
    }

    fun paisesConMasPoblacion(): List<String> {
        val paises = api.todosLosPaises().map { pais -> adapter.adaptarSinLimitrofes(pais) }.toMutableList()

        val paisesConMayorPoblacion = mutableListOf<Pais>()
        repeat(5) {
            val paisConMayorPoblacion = paises.maxByOrNull { pais -> pais.poblacion }!!
            paisesConMayorPoblacion.add(paisConMayorPoblacion)
            paises.remove(paisConMayorPoblacion)
        }

        return paisesConMayorPoblacion.map { pais -> pais.nombre }
    }

    fun continenteConMasHabitantes(): String {
        val paises = api.todosLosPaises().map { country -> adapter.adaptarSinLimitrofes(country) }
        val continentes = paises.map { pais -> pais.continente }.toSet()

        return continentes.maxByOrNull { continente ->
            habitantesDeUnContinente(
                continente,
                paises
            )
        }!!
    }

    private fun habitantesDeUnContinente(continente: String, paises: List<Pais>) =
        paises.filter { pais -> pais.continente == continente }.sumByLong { pais -> pais.poblacion }
}

class PaisNoEncontradoException : RuntimeException()

data class RelacionPaises(
    val sonLimitrofes: Boolean,
    val necesitanTraduccion: Boolean,
    val sonAliadosPotenciales: Boolean
)