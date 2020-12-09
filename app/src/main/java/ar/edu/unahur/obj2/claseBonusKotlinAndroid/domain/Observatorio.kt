package ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.adapters.PaisAdapter
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.sumByLong
import java.lang.RuntimeException

class Observatorio(private val api: RestCountriesAPI = RestCountriesAPI()) {
    private val adapter = PaisAdapter(api)

    fun paisConNombre(nombre:String): Pais {
        try {
            return adapter.adaptar(api.buscarPaisesPorNombre(nombre).first())
        } catch (e: NoSuchElementException) {
            throw PaisNoEncontradoException()
        }
    }

    fun sonLimitrofes(pais1: String, pais2: String) =
        paisConNombre(pais1).esLimitrofe(paisConNombre(pais2))

    fun necesitanTraduccion(pais1: String, pais2: String) =
        paisConNombre(pais1).necesitaTraduccionCon(paisConNombre(pais2))

    fun sonPotencialmenteAliados(pais1: String, pais2: String) =
        paisConNombre(pais1).sonAliadosPotenciales(paisConNombre(pais2))

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