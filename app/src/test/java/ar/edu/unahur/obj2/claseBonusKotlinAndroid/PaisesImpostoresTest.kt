package ar.edu.unahur.obj2.claseBonusKotlinAndroid

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.Country
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.Language
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RegionalBloc
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Pais
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class PaisesImpostoresTest:DescribeSpec({
    val api = mockk<RestCountriesAPI>()
    Observatorio.api = api

    // Etapa 1
    // 1.1.1
    describe("Un pais") {

        val estadosUnidos = Pais("Estados Unidos", "EEUU", 525000000, 400000.0, "America", listOf("NORMER"), listOf("Ingles"),
            mutableListOf())
        val canada = Pais("Canada", "Can", 500000000, 400000.0, "America", listOf("NORMER"), listOf("Ingles", "Frances"),
            mutableListOf(estadosUnidos))
        val francia = Pais("Francia", "FRA", 510000000, 400000.0, "Europa", listOf("EUROMER"), listOf("Francés"),mutableListOf())
        val argentina = Pais("Argentina", "ARG", 440000000, 400000.0, "America", listOf("Mercosur"), listOf("español"),mutableListOf())
        val mexico = Pais("Mexico", "MEX", 510000000, 400000.0, "America", listOf("NORMER"), listOf("español"),mutableListOf(canada,estadosUnidos))
        val australia = Pais("Australia", "AUS", 510000000, 400000.0, "Oceania", listOf("OCEAMER"), listOf("Ingles"),mutableListOf())
        estadosUnidos.paisesLimitrofes.add(mexico)
        estadosUnidos.paisesLimitrofes.add(canada)
        canada.paisesLimitrofes.add(mexico)

        it("con mas de 1 idioma oficial es Plurinacional") {
            canada.esPlurinacional().shouldBeTrue()

        }
        it("con 1 idioma oficial NO es Plurinacional") {
            estadosUnidos.esPlurinacional().shouldBeFalse()
        }

        // 1.1.2
        it("sin paises limítrofes es una isla") {
            australia.esUnaIsla().shouldBeTrue()
        }
        it("con paises limítrofes NO es una isla") {

            canada.esUnaIsla().shouldBeFalse()
        }

        // 1.1.3
        describe("tiene una densidad poblacional") {
            it("de 1312.5 para estados unidos") {
                estadosUnidos.densidadPoblacional().shouldBe(1312.5)
            }
        }

        // 1.1.4
        describe("conoce al vecino mas poblado") {
            it("siendo EEUU para el caso de Mexico") {

                mexico.vecinoMasPoblado().shouldBe(estadosUnidos)
            }
            it("siendo EEUU para el caso de EEUU") {

                estadosUnidos.vecinoMasPoblado().shouldBe(estadosUnidos)
            }
        }

        // 1.2.1
        describe("es limítrofe con otro país") {
            it("Mexico es limitrofe con canadá") {

                mexico.esLimitrofeCon(canada).shouldBeTrue()
            }
            it("EEUU NO es limitrofe con Francia") {

                estadosUnidos.esLimitrofeCon(francia).shouldBeFalse()
            }
        }

        // 1.2.2
        describe("sabe si necesita traduccion para poder dialogar") {
            it("Estados unidos SI necesita traduccion para dialogar con Francia") {
                estadosUnidos.necesitaTraduccion(francia).shouldBeTrue()
            }
            it("EEUU NO necesita traduccion para dialogar con Canadá") {
                estadosUnidos.necesitaTraduccion(canada).shouldBeFalse()
            }
        }

        // 1.2.3
        describe("sabe si es potencial aliado de otro país") {
            it("EEUU es potencial aliado de canada") {
                estadosUnidos.esPotencialAliadoDe(canada).shouldBeTrue()
                // comparten idioma y bloque regional (se cumplen los 2 requisitos)
            }
            it("EEUU NO es potencial aliado de francia") {
                estadosUnidos.esPotencialAliadoDe(francia).shouldBeFalse()
                // no comparten ni idioma ni bloque regional (no se cumple ningun requisito)
            }
            it("Argentina NO es potencial aliado de Mexico") {
                argentina.esPotencialAliadoDe(mexico).shouldBeFalse()
                // comparten el idioma, pero no comparten bloque regional (se cumple solo 1 requisito)
            }
            it("Mexico no es potencial aliado de eeuu") {
                mexico.esPotencialAliadoDe(estadosUnidos).shouldBeFalse()
                //comparten bloque regional, pero no comparten  el idioma (se cumple solo 1 requisito)
            }
        }
    }

    // Etapa 2
    describe("Un Observatorio puede") {
        it("buscar un pais con nombre específico") {
            every { api.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(
                    "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                    emptyList(), emptyList(), emptyList()
                )
            )
            Observatorio.buscarPaisDeNombre("Argentina").nombre.shouldBe("Argentina")
        }

        // 2.1
        describe("saber si 2 paises son limitrofes") {
            every { api.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(
                    "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                    listOf("PAR"), emptyList(), emptyList()
                )
            )
            every { api.buscarPaisesPorNombre("Paraguay") } returns listOf(
                Country(
                    "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                    listOf("ARG"), emptyList(), emptyList()
                )
            )
            every {api.buscarPaisesPorNombre("Francia")} returns listOf(Country(
                "Francia", "FRA", "Paris", "Europa", 40000000, 100000.0,
                listOf(), emptyList(), emptyList()
            ))
            every { api.todosLosPaises() } returns listOf(Country(
                "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                listOf("PAR"), emptyList(), emptyList()
            ),
                Country(
                    "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                    listOf("ARG"), emptyList(), emptyList()
                ),
                Country(
                    "Francia", "FRA", "Paris", "Europa", 40000000, 100000.0,
                    listOf(), emptyList(), emptyList()
                ))
            every { api.paisConCodigo("PAR") } returns Country(
                "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                listOf("ARG"), emptyList(), emptyList()
            )
            every { api.paisConCodigo("FRA") } returns Country(
                "Francia", "FRA", "Paris", "Europa", 40000000, 100000.0,
                listOf(), emptyList(), emptyList()
            )
            every {api.paisConCodigo("ARG")} returns Country(
                "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                listOf("PAR"), emptyList(), emptyList()
            )

            it("Argentina y paraguay son limitrofes") {
                Observatorio.sonLimitrofes("Argentina","Paraguay").shouldBeTrue()
            }

            it("Argentina y Francia NO son limitrofes") {
                Observatorio.sonLimitrofes("Argentina", "Francia").shouldBeFalse()
            }

        }

        // 2.2
        describe("saber si 2 paises necesitan traduccion") {
            every { api.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(
                    "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                    listOf("PAR"), listOf(Language("Español")), emptyList()
                )

            )
            every {api.buscarPaisesPorNombre("Francia")} returns listOf(Country(
                "Francia", "FRA", "Paris", "Europa", 40000000, 100000.0,
                listOf(), emptyList(), emptyList()
            ))
            every { api.paisConCodigo("PAR") } returns Country(
                "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                listOf("ARG"), emptyList(), emptyList()
            )
            every { api.buscarPaisesPorNombre("Paraguay") } returns listOf(
                Country(
                    "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                    listOf("ARG"),listOf(Language("Guarani"),Language("Español")), emptyList()
                )
            )
            every {api.paisConCodigo("ARG")} returns Country(
                "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                listOf("PAR"), emptyList(), emptyList()
            )

            it("Argentina y Francia necesitan traduccion."){
                Observatorio.necesitanTraduccion("Argentina","Francia").shouldBeTrue()
            }
            it("Argentina y Paraguay no necesitan traduccion."){
                Observatorio.necesitanTraduccion("Argentina","Paraguay").shouldBeFalse()
            }
        }

        // 2.3
        describe("saber si 2 paises son potenciales aliados") {
            every { api.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(
                    "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                    listOf("PAR"), listOf(Language("Español")), listOf(RegionalBloc("Mercosur","MER"))
                )

            )
            every {api.buscarPaisesPorNombre("Francia")} returns listOf(Country(
                "Francia", "FRA", "Paris", "Europa", 40000000, 100000.0,
                listOf(), emptyList(), listOf(RegionalBloc("Mercosur","MER"))
            ))
            every { api.paisConCodigo("PAR") } returns Country(
                "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                listOf("ARG"), emptyList(), listOf(RegionalBloc("Mercosur","MER"))
            )
            every { api.buscarPaisesPorNombre("Paraguay") } returns listOf(
                Country(
                    "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                    listOf("ARG"),listOf(Language("Guarani"),Language("Español")),listOf(RegionalBloc("Mercosur","MER"))
                )
            )
            every {api.paisConCodigo("ARG")} returns Country(
                "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                listOf("PAR"), emptyList(), listOf(RegionalBloc("Mercosur","MER"))
            )
            it("Argentina y paraguay son potenciales aliados."){
                Observatorio.sonPotencialesAliados("Argentina","Paraguay").shouldBeTrue()
            }
            it("Argentina y mexico no son potenciales aliados."){
                Observatorio.sonPotencialesAliados("Argentina","Francia").shouldBeFalse()
            }
        }

        // 2.4
        describe("2.4 Y 2.5") {
            every { api.todosLosPaises() } returns listOf(
                Country(
                    "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                    listOf("PAR"), emptyList(), emptyList()
                ),
                Country(
                    "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                    listOf("ARG"), emptyList(), emptyList()
                ),
                Country(
                    "Francia", "FRA", "Paris", "Europa", 40000000, 100000.0,
                    listOf(), emptyList(), emptyList()
                ),
                Country("España", "ESP", "Madrid", "Europa", 100, 100.1, emptyList(), emptyList(), emptyList()),
                Country("Groenlandia", "GRO", "Nose", "Europa", 114, 100.1, emptyList(), emptyList(), emptyList()),
                Country("Italia", "ITA", "Roma", "Europa", 1000000, 100.1, emptyList(), emptyList(), emptyList()),
            )
            it("obtener los codigos ISO de los 5 paises con mayor densidad poblacional") {

                Observatorio.codigoIso5PaisesMasDensos().shouldContainExactly("ITA", "ARG", "PAR", "FRA", "GRO")
            }
            it("indicar el nombre del continente con mas países plurinacionales"){ //2.5
                every { api.buscarPaisesPorNombre("Paraguay") } returns listOf(
                    Country(
                        "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                        listOf("ARG"),listOf(Language("Guarani"),Language("Español")), emptyList()
                    )
                )
                Observatorio.continenteConMasPlurinacionales().shouldBe("America")
            }
        }

        // 2.6
        it("Promedio de densidad de paises isla siendo australia la unica isla."){
            every {api.todosLosPaises()}returns listOf(Country(
                "Paraguay", "PAR", "Asuncion", "America", 40000000, 100000.0,
                listOf("ARG"),listOf(Language("Guarani"),Language("Español")), emptyList()
            ),
                Country(
                    "Australia", "AUS", "Auckland", "Oceania", 40000000, 100000.0,
                    listOf(),listOf(Language("Ingles"),Language("Frances")), emptyList()
                ))
            every {api.paisConCodigo("ARG")} returns Country(
                "Argentina", "ARG", "Buenos Aires", "America", 40000000, 100000.0,
                listOf("PAR"), emptyList(), listOf(RegionalBloc("Mercosur","MER")))
            Observatorio.promedioDeDensidadDePaisesIsla().shouldBe(400.0)
        }
    }
})