package inflation

import java.io.File

fun main() {

    val startingYear = 2000

    var result = StringBuilder()
    var type = "THIS_YEAR_VS_LAST_YEAR"

    result.append("DELETE from inflation_rate WHERE type = '$type'; \n")
    result.append("INSERT INTO inflation_rate (month, year, type, value_percent) VALUES \n")

    val values = listOf(
            2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.9, 3.1, 3.3, 3.6, 3.8, 3.9,
            4.0, 4.0, 4.0, 4.1, 4.2, 4.3, 4.5, 4.6, 4.7, 4.7, 4.7, 4.7,
            4.6, 4.6, 4.6, 4.5, 4.3, 3.9, 3.5, 3.1, 2.7, 2.4, 2.1, 1.8,
            1.5, 1.1, 0.8, 0.5, 0.3, 0.2, 0.2, 0.1, 0.0, 0.0, 0.1, 0.1,
            0.3, 0.5, 0.8, 1.0, 1.2, 1.4, 1.7, 2.0, 2.2, 2.5, 2.7, 2.8,
            2.8, 2.7, 2.6, 2.6, 2.5, 2.4, 2.2, 2.1, 2.0, 2.0, 1.9, 1.9,
            2.0, 2.1, 2.2, 2.3, 2.4, 2.5, 2.6, 2.7, 2.8, 2.7, 2.6, 2.5,
            2.4, 2.3, 2.2, 2.2, 2.1, 2.1, 2.1, 2.0, 2.0, 2.2, 2.5, 2.8,
            3.4, 3.9, 4.3, 4.7, 5.0, 5.4, 5.8, 6.1, 6.4, 6.6, 6.5, 6.3,
            5.9, 5.4, 5.0, 4.6, 4.1, 3.7, 3.1, 2.6, 2.1, 1.6, 1.3, 1.0,
            0.9, 0.8, 0.7, 0.6, 0.6, 0.6, 0.8, 0.9, 1.1, 1.2, 1.4, 1.5,
            1.6, 1.7, 1.7, 1.8, 1.8, 1.9, 1.9, 1.9, 1.8, 1.9, 1.9, 1.9,
            2.1, 2.2, 2.4, 2.6, 2.7, 2.8, 2.9, 3.1, 3.2, 3.3, 3.3, 3.3,
            3.2, 3.0, 2.8, 2.7, 2.5, 2.3, 2.2, 2.0, 1.8, 1.6, 1.5, 1.4,
            1.3, 1.1, 1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.5, 0.5, 0.5, 0.4,
            0.3, 0.3, 0.3, 0.4, 0.4, 0.5, 0.5, 0.4, 0.4, 0.4, 0.3, 0.3,
            0.4, 0.4, 0.4, 0.4, 0.4, 0.3, 0.3, 0.3, 0.3, 0.4, 0.5, 0.7,
            0.8, 1.0, 1.2, 1.3, 1.5, 1.7, 1.8, 2.0, 2.2, 2.3, 2.4, 2.5,
            2.4, 2.4, 2.3, 2.3, 2.3, 2.3, 2.3, 2.3, 2.3, 2.2, 2.2, 2.1,
            2.2, 2.3, 2.4, 2.4, 2.5, 2.5, 2.6, 2.6, 2.6, 2.7, 2.7, 2.8,
            2.9, 3.0, 3.1, 3.1, 3.1
    )

    var currentYear = startingYear
    var currentMonth = 1

    print("\n\n\n")

    values.forEachIndexed { index, value ->

        result.append("($currentMonth, $currentYear, '$type',$value)")

        if (values.lastIndex != index) {
            result.append(",")
        } else {
            result.append(";")
        }

        result.append("\n")

        currentMonth++

        if (currentMonth >= 13) {
            currentMonth = 1
            currentYear++
        }

    }

    File("../../src/main/resources/db/migration/result.sql").printWriter().use { out ->
        out.write(result.toString())
    }

    println(result)

    print("\n\n\n")

}
