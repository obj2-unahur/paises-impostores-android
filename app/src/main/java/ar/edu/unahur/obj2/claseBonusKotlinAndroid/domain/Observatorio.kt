package ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.adapters.PaisAdapter
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.utils.sumByLong

class Observatorio(private val api: RestCountriesAPI = RestCountriesAPI()) {
    private val adapter = PaisAdapter(api)
    private val todosLosPaises: List<Pais> by lazy {
        api.todosLosPaises().map { country -> adapter.adaptarSinLimitrofes(country) }
    }

    companion object {
        var instance = Observatorio()
    }

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
            pais1.esLimitrofeCon(pais2),
            pais1.necesitaTraduccionCon(pais2),
            pais1.sonPotencialmenteAliadosCon(pais2)
        )
    }

    fun paisesConMasPoblacion(): List<String> {
        val paises = todosLosPaises.toMutableList()
        val paisesConMayorPoblacion = mutableListOf<Pais>()

        repeat(5) {
            val paisConMayorPoblacion = paises.maxByOrNull { pais -> pais.poblacion }!!
            paisesConMayorPoblacion.add(paisConMayorPoblacion)
            paises.remove(paisConMayorPoblacion)
        }

        return paisesConMayorPoblacion.map { pais -> pais.nombre }
    }

    fun continenteConMasHabitantes(): String {
        val continentes = todosLosPaises.map { pais -> pais.continente }.toSet()

        return continentes.maxByOrNull { continente ->
            habitantesDeUnContinente(
                continente,
                todosLosPaises
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
    val sonPotencialmenteAliados: Boolean
)