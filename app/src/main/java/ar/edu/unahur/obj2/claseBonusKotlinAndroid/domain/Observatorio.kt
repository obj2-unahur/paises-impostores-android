package ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.adapters.PaisAdapter

object Observatorio {
    var api: RestCountriesAPI = RestCountriesAPI()

    fun todosLosPaises() = api.todosLosPaises().map { PaisAdapter.adaptarPaisSinLimitrofes(it) }
    fun todosLosPaisesConLimitrofes() = api.todosLosPaises().map { PaisAdapter.adaptarPais(it) }
    fun buscarPaisDeNombre(nombrePais: String) =
        try {
            PaisAdapter.adaptarPais(api.buscarPaisesPorNombre(nombrePais).first())
        } catch (e: Exception) {
            throw PaisNoEncontradoException()
        }

    fun sonLimitrofes(nombrePais1: String, nombrePais2: String): Boolean {
        val pais1 = buscarPaisDeNombre(nombrePais1)
        val pais2 = buscarPaisDeNombre(nombrePais2)

        return pais1.esLimitrofeCon(pais2)
    }

    fun necesitanTraduccion(nombrePais1: String, nombrePais2: String) =
        buscarPaisDeNombre(nombrePais1).necesitaTraduccion(buscarPaisDeNombre(nombrePais2))

    fun sonPotencialesAliados(nombrePais1: String, nombrePais2: String) =
        buscarPaisDeNombre(nombrePais1).esPotencialAliadoDe(buscarPaisDeNombre(nombrePais2))

    fun paisesOrdenadosPorDensidad() =
        todosLosPaises().sortedByDescending { it.densidadPoblacional() }

    fun top5PaisesMasDensos() = paisesOrdenadosPorDensidad().take(5)
    fun codigoIso5PaisesMasDensos() = top5PaisesMasDensos().map { it.codigoISO3 }
    fun paisesDeContinente(continente: String) =
        todosLosPaises().filter { it.continente == continente }

    fun cantidadDePlurinacionalesDeContinente(continente: String) =
        paisesDeContinente(continente).count { it.esPlurinacional() }

    fun todosLosContinentes() = todosLosPaises().map { it.continente }.toSet()
    fun continenteConMasPlurinacionales() = todosLosContinentes().maxByOrNull { continente ->
        cantidadDePlurinacionalesDeContinente(continente)
    }!!

    fun paisesIsla() = todosLosPaisesConLimitrofes().filter { it.esUnaIsla() }
    fun promedioDeDensidadDePaisesIsla() = paisesIsla().map { it.densidadPoblacional() }.average()
}

class PaisNoEncontradoException : RuntimeException()