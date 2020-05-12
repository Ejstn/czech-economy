import java.util.*

val startingYear = 1993

var result = StringBuilder()
result.append("INSERT INTO unemployment_rate (quarter, year, value_percent) VALUES \n")

val values = listOf(
        2.0,
        2.1,
        1.9,
        2.0,

        2.0,
        2.3,
        2.2,
        2.4,

        2.4,
        2.8,
        3.0,
        3.4,

        3.6,
        4.0,
        3.9,
        4.3,

        4.5,
        4.8,
        4.9,
        6.0,

        5.7,
        5.9,
        6.0,
        6.8,

        6.7,
        6.9,
        6.7,
        7.4,

        7.2,
        7.0,
        6.7,
        7.1,

        6.4,
        6.6,
        6.7,
        7.2,

        6.9,
        7.1,
        7.1,
        8.0,

        7.2,
        7.3,
        6.3,
        5.8,

        4.4,
        4.3,
        4.2,
        4.7,

        4.8,
        5.1,
        5.3,
        6.0,

        6.5,
        7.0,
        7.1,
        8.0,

        7.8,
        7.8,
        7.8,
        8.4,

        8.2,
        8.2,
        8.2,
        8.7,

        8.1,
        8.0,
        7.5,
        7.6,

        7.3,
        7.2,
        7.0,
        7.7,

        7.8,
        8.2,
        8.0,
        8.5,

        8.3,
        8.5,
        8.7,
        9.5,

        9.0,
        9.0,
        8.4,
        8.4,

        7.3,
        6.8,
        5.9,
        5.9,

        5.4,
        5.0,
        4.5,
        4.3,

        4.1,
        4.0,
        3.8,
        3.7,

        3.7,
        4.0,
        4.0,
        4.3,

        4.3,
        4.5,
        4.2,
        4.2,

        4.2,
        4.3,
        4.3,
        4.5

).asReversed()


var currentYear = startingYear
var currentQuarter = 1

print("\n\n\n")

values.forEachIndexed { index, value ->

    result.append("($currentQuarter, $currentYear, $value)")

    if (values.lastIndex != index) {
        result.append(",")
    } else {
        result.append(";")
    }

    result.append("\n")

    currentQuarter++

    if (currentQuarter >= 5) {
        currentQuarter = 1
        currentYear++
    }

}

println(result)

print("\n\n\n")
