package ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain

class Pais(
    val nombre: String,
    val codigoISO3: String,
    val poblacion: Int,
    val superficie: Double,
    val continente: String,
    val bloquesRegionales: List<String>,
    val idiomas: List<String>,
    val paisesLimitrofes: MutableList<Pais>,
    val bandera: String? = null
) {
    fun esPlurinacional() = idiomas.size > 1
    fun esUnaIsla() = paisesLimitrofes.isEmpty()
    fun densidadPoblacional() = poblacion / superficie
    fun vecinoMasPoblado() = (paisesLimitrofes + this).maxByOrNull { it.poblacion }!!
    fun esLimitrofeCon(pais: Pais) = paisesLimitrofes.map { it.nombre }.contains(pais.nombre)
    fun necesitaTraduccion(pais: Pais) = cantidadDeIdiomasEnComun(pais) == 0
    fun cantidadDeIdiomasEnComun(pais: Pais) = this.idiomas.intersect(pais.idiomas).size
    fun esPotencialAliadoDe(pais: Pais) =
        !this.necesitaTraduccion(pais) && this.comparteAlMenosUnBloqueRegionalCon(pais)

    fun cantidadDeBloquesRegionalesEnComun(pais: Pais) =
        this.bloquesRegionales.intersect(pais.bloquesRegionales).size

    fun comparteAlMenosUnBloqueRegionalCon(pais: Pais) =
        this.cantidadDeBloquesRegionalesEnComun(pais) >= 1
}
