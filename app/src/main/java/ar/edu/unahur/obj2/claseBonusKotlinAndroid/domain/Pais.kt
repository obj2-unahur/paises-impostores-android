package ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain

open class Pais(
    val nombre : String,
    val codigo : String,
    val poblacion : Long,
    val continente : String,
    val bandera: String,
    val bloquesRegionales : List<String>,
    val idiomasOficiales : List<String>,
    val paisesLimitrofes : List<Pais>
) {
    fun esLimitrofeCon(otroPais: Pais): Boolean {
        val nombreDePaisesLimitrofes = paisesLimitrofes.map { pais -> pais.nombre }
        return nombreDePaisesLimitrofes.contains(otroPais.nombre)
    }

    fun necesitaTraduccionCon(otroPais: Pais) =
        otroPais.idiomasOficiales.intersect(idiomasOficiales).isEmpty()

    fun sonPotencialmenteAliadosCon(otroPais: Pais) =
        !necesitaTraduccionCon(otroPais) && compartenBloquesRegionalesCon(otroPais)

    private fun compartenBloquesRegionalesCon(otroPais: Pais) =
        otroPais.bloquesRegionales.intersect(bloquesRegionales).isNotEmpty()

}
