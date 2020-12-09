package ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.adapters

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.Country
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Pais

class PaisAdapter(private val api: RestCountriesAPI = RestCountriesAPI()) {
    fun adaptar(country: Country): Pais {
        val bloquesRegionales = adaptarBloquesRegionales(country)
        val lenguajes = adaptarLenguajes(country)
        val limitrofes = adaptarLimitrofes(country)

        return Pais(
            country.name, country.alpha3Code, country.population, country.region, country.flag,
            bloquesRegionales, lenguajes, limitrofes
        )
    }

    fun adaptarSinLimitrofes(country: Country): Pais {
        val bloquesRegionales = adaptarBloquesRegionales(country)
        val lenguajes = adaptarLenguajes(country)

        return Pais(
            country.name, country.alpha3Code, country.population, country.region, country.flag,
            bloquesRegionales, lenguajes, listOf()
        )
    }

    private fun adaptarBloquesRegionales(country: Country): List<String> {
        return country.regionalBlocs.map { bloqueRegional -> bloqueRegional.toString() }
    }

    private fun adaptarLenguajes(country: Country): List<String> {
        return country.languages.map {lenguaje -> lenguaje.toString()}
    }

    private fun adaptarLimitrofes(country: Country): List<Pais> {
        val countries = country.borders.map { codigo -> api.paisConCodigo(codigo) }
        return countries.map { pais -> adaptarSinLimitrofes(pais) }
    }
}