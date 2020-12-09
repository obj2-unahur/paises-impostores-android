package ar.edu.unahur.obj2.claseBonusKotlinAndroid

import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.Country
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.Language
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RegionalBloc
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.apis.RestCountriesAPI
import ar.edu.unahur.obj2.claseBonusKotlinAndroid.domain.Observatorio
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.containAll
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ObservatorioTest : DescribeSpec({
    describe("Observatorio") {

        val apiNueva = mockk<RestCountriesAPI>()
        val observatorio = Observatorio(apiNueva)

        it ("Argentina y Brazil son limítrofes") {
            every { apiNueva.paisConCodigo("BRA") } returns
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                )
            every { apiNueva.paisConCodigo("ARG") } returns
                Country(name = "Argentina",
                    alpha3Code = "ARG",
                    capital = "Buenos Aires",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf("BRA"),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                )
            every { apiNueva.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(name = "Argentina",
                    alpha3Code = "ARG",
                    capital = "Buenos Aires",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf("BRA"),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                )
            )
            every { apiNueva.buscarPaisesPorNombre("Brazil") } returns listOf(
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "206135893",
                    population = 43590400,
                    borders = mutableListOf("ARG"),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                )
            )

            observatorio.relacionEntre("Argentina", "Brazil").sonLimitrofes
                .shouldBeTrue()
        }

        it ("Argentina y Bolivia son potencialmente aliados") {
            every { apiNueva.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(name = "Argentina",
                    alpha3Code = "ARG",
                    capital = "Buenos Aires",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Spanish")),
                    regionalBlocs = mutableListOf(RegionalBloc(acronym="USAN", name="Union of South American Nations"))
                )
            )
            every { apiNueva.buscarPaisesPorNombre("Bolivia") } returns listOf(
                Country(name = "Bolivia",
                    alpha3Code = "BOL",
                    capital = "Sucre",
                    region = "Americas",
                    population = 11400000,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Spanish")),
                    regionalBlocs = mutableListOf(RegionalBloc(acronym="USAN", name="Union of South American Nations"))
                )
            )
            every { apiNueva.buscarPaisesPorNombre("Brazil") } returns listOf(
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "206135893",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Portuguese")),
                    regionalBlocs = mutableListOf(RegionalBloc(acronym="USAN", name="Union of South American Nations"))
                )
            )

            observatorio.relacionEntre("Argentina", "Bolivia").sonPotencialmenteAliados
                .shouldBeTrue()
        }

        it ("Argentina y Brazil no son potencialmente aliados") {
            every { apiNueva.buscarPaisesPorNombre("Argentina") } returns listOf(
                Country(name = "Argentina",
                    alpha3Code = "ARG",
                    capital = "Buenos Aires",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Spanish")),
                    regionalBlocs = mutableListOf(RegionalBloc(acronym="USAN", name="Union of South American Nations"))
                )
            )
            every { apiNueva.buscarPaisesPorNombre("Brazil") } returns listOf(
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "206135893",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Portuguese")),
                    regionalBlocs = mutableListOf(RegionalBloc(acronym="USAN", name="Union of South American Nations"))
                )
            )

            observatorio.relacionEntre("Argentina", "Brazil").sonPotencialmenteAliados
                .shouldBeFalse()
        }

        it ("Nigeria y China necesitan traducción") {
            every { apiNueva.buscarPaisesPorNombre("Nigeria") } returns listOf(
                Country(name = "Nigeria",
                    alpha3Code = "NGA",
                    capital = "Abuja",
                    region = "Africa",
                    population = 186988000,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("English")),
                    regionalBlocs = mutableListOf()
                )
            )
            every { apiNueva.buscarPaisesPorNombre("China") } returns listOf(
                Country(name = "China",
                    alpha3Code = "CHN",
                    capital = "Beijing",
                    region = "Asia",
                    population = 1377422166,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Chinese")),
                    regionalBlocs = mutableListOf()
                )
            )

            observatorio.relacionEntre("Nigeria", "China").necesitanTraduccion
                .shouldBeTrue()
        }

        it ("Brazil y Portugal no necesitan traducción") {
            every { apiNueva.buscarPaisesPorNombre("Brazil") } returns listOf(
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "206135893",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Portuguese")),
                    regionalBlocs = mutableListOf()
                )
            )
            every { apiNueva.buscarPaisesPorNombre("Portugal") } returns listOf(
                Country(name = "Portugal",
                    alpha3Code = "PRT",
                    capital = "Lisbon",
                    region = "Europe",
                    population = 10374822,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Portuguese")),
                    regionalBlocs = mutableListOf()
                )
            )
            observatorio.relacionEntre("Brazil", "Portugal").necesitanTraduccion
                .shouldBeFalse()
        }

        it("Países con más poblacion") {
            every { apiNueva.todosLosPaises() } returns listOf(
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "Americas",
                    population = 206135893,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Argentina",
                    alpha3Code = "ARG",
                    capital = "Buenos Aires",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Nigeria",
                    alpha3Code = "NGA",
                    capital = "Abuja",
                    region = "Africa",
                    population = 186988000,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Bolivia",
                    alpha3Code = "BOL",
                    capital = "Sucre",
                    region = "Americas",
                    population = 11400000,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "China",
                    alpha3Code = "CHN",
                    capital = "Beijing",
                    region = "Asia",
                    population = 1377422166,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Chinese")),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Portugal",
                    alpha3Code = "PRT",
                    capital = "Lisbon",
                    region = "Europe",
                    population = 10374822,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                )
            )

            observatorio.paisesConMasPoblacion().shouldBe(containAll("Brazil", "China", "Nigeria", "Argentina", "Bolivia"))
            observatorio.paisesConMasPoblacion().size.shouldBe(5)
        }

        it("El continente con más poblacion es Asia") {
            every { apiNueva.todosLosPaises() } returns listOf(
                Country(name = "Brazil",
                    alpha3Code = "BRA",
                    capital = "Brasilia",
                    region = "Americas",
                    population = 206135893,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Argentina",
                    alpha3Code = "ARG",
                    capital = "Buenos Aires",
                    region = "Americas",
                    population = 43590400,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Nigeria",
                    alpha3Code = "NGA",
                    capital = "Abuja",
                    region = "Africa",
                    population = 186988000,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Bolivia",
                    alpha3Code = "BOL",
                    capital = "Sucre",
                    region = "Americas",
                    population = 11400000,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "China",
                    alpha3Code = "CHN",
                    capital = "Beijing",
                    region = "Asia",
                    population = 1377422166,
                    borders = mutableListOf(),
                    languages = mutableListOf(Language("Chinese")),
                    regionalBlocs = mutableListOf()
                ),
                Country(name = "Portugal",
                    alpha3Code = "PRT",
                    capital = "Lisbon",
                    region = "Europe",
                    population = 10374822,
                    borders = mutableListOf(),
                    languages = mutableListOf(),
                    regionalBlocs = mutableListOf()
                )
            )

            observatorio.continenteConMasHabitantes().shouldBe(("Asia"))
        }
    }
})