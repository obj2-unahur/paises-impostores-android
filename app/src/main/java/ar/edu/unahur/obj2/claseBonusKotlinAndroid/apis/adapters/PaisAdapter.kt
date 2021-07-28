package ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.adapters

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.Country
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Pais

object PaisAdapter {
    fun adaptarPais(
        country: Country,
        paisesLimitrofes: MutableList<Pais> = adaptarLimitrofes(country).toMutableList()
    ) =
        Pais(
            country.name,
            country.alpha3Code,
            adaptarPoblacion(country),
            country.area ?: 1.0,
            country.region,
            adaptarBloquesRegionales(country),
            adaptarIdiomas(country),
            paisesLimitrofes,
            country.flag
        )

    fun adaptarBloquesRegionales(country: Country) = country.regionalBlocs.map { it.name }
    fun adaptarIdiomas(country: Country) = country.languages.map { it.name }
    fun adaptarPaisSinLimitrofes(country: Country): Pais {
        return adaptarPais(country, mutableListOf())
    }

    fun adaptarPoblacion(country: Country) = country.population.toInt()
    fun adaptarLimitrofes(country: Country): List<Pais> {
        val limitrofes = country.borders.map { codigo -> Observatorio.api.paisConCodigo(codigo) }
        return limitrofes.map { pais -> adaptarPaisSinLimitrofes(pais) }
    }
}