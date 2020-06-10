import inflation.ThisYearVsLastYearInflation
import java.io.File
import java.time.LocalDate

fun main() {

    val fileName = generateVersionName()

    val result = StringBuilder()

    result.appendln("-- GENERATED ON ${LocalDate.now()}")
    result.appendln()

    addMigrations(result)

    File("$migrationsFolder/$fileName.sql").printWriter().use { out ->
        out.write(result.toString())
    }

}

fun addMigrations(stringBuilder: StringBuilder) {
    with(stringBuilder) {
        appendln(ThisMonthVsLastYearsMonthInflation.generate())
        appendln(ThisMonthVsPreviousMonthInflation.generate())
        appendln(ThisYearVsLastYearInflation.generate())
    }

}

val migrationsFolder = "./src/main/resources/db/migration"
val migrationName = "Manually_Add_Data"

fun generateVersionName(): String {

    return File(migrationsFolder).walk()
            .map { it.name }
            .filter { it.contains(".sql") }
            .map { it.split("__") }
            .map { it[0] }
            .map { it.substring(1) }
            .map { it.split('.') }
            .map { Triple(it[0].toInt(), it[1].toInt(), it[2].toInt()) }
            .sortedWith(Comparator { lhs, rhs ->
                if (lhs.first != rhs.first) {
                    return@Comparator lhs.first - rhs.first
                }
                if (lhs.second != rhs.second) {
                    return@Comparator lhs.second - rhs.second
                }
                if (lhs.third != rhs.third) {
                    return@Comparator lhs.third - rhs.third
                }
                return@Comparator 0
            })
            .last()
            .run {
                "V${this.first}.${this.second}.${this.third + 1}__$migrationName"
            }
            .also { print(it) }

}



