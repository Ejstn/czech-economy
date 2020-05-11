import java.util.*

val startingYear = 1997

var result = StringBuilder()
result.append("INSERT INTO inflation_rate (month, year, type, value_percent) VALUES \n")

val values = listOf(
        7.4,

        7.3,

        6.8,

        6.7,

        6.3,

        6.8,

        9.4,

        9.9,

        10.3,

        10.2,

        10.1,

        10.0,

        13.1,

        13.4,

        13.4,

        13.1,

        13.0,

        12.0,

        10.4,

        9.4,

        8.8,

        8.2,

        7.5,

        6.8,

        3.5,

        2.8,

        2.5,

        2.5,

        2.4,

        2.2,

        1.1,

        1.4,

        1.2,

        1.4,

        1.9,

        2.5,

        3.4,

        3.7,

        3.8,

        3.4,

        3.7,

        4.1,

        3.9,

        4.1,

        4.1,

        4.4,

        4.3,

        4.0,

        4.2,

        4.0,

        4.1,

        4.6,

        5.0,

        5.5,

        5.9,

        5.5,

        4.7,

        4.4,

        4.2,

        4.1,

        3.7,

        3.9,

        3.7,

        3.2,

        2.5,

        1.2,

        0.6,

        0.6,

        0.8,

        0.6,

        0.5,

        0.6,

        -0.4,

        -0.4,

        -0.4,

        -0.1,

        0.0,

        0.3,

        -0.1
        ,
        -0.1
        ,
        0.0
        ,
        0.4
        ,
        1.0
        ,
        1.0
        ,
        2.3
        ,
        2.3
        ,
        2.5
        ,
        2.3
        ,
        2.7
        ,
        2.9
        ,
        3.2
        ,
        3.4
        ,
        3.0
        ,
        3.5
        ,
        2.9
        ,
        2.8
        ,
        1.7
        ,
        1.7
        ,
        1.5
        ,
        1.6
        ,
        1.3
        ,
        1.8
        ,
        1.7
        ,
        1.7
        ,
        2.2
        ,
        2.6
        ,
        2.4
        ,
        2.2
        ,
        2.9
        ,
        2.8
        ,
        2.8
        ,
        2.8
        ,
        3.1
        ,
        2.8
        ,
        2.9
        ,
        3.1
        ,
        2.7
        ,
        1.3
        ,
        1.5
        ,
        1.7
        ,
        1.3
        ,
        1.5
        ,
        1.9
        ,
        2.5
        ,
        2.4
        ,
        2.5
        ,
        2.3
        ,
        2.4
        ,
        2.8
        ,
        4.0
        ,
        5.0
        ,
        5.4
        ,
        7.5
        ,
        7.5
        ,
        7.1
        ,
        6.8
        ,
        6.8
        ,
        6.7
        ,
        6.9
        ,
        6.5
        ,
        6.6
        ,
        6.0
        ,
        4.4
        ,
        3.6
        ,
        2.2
        ,
        2.0
        ,
        2.3
        ,
        1.8
        ,
        1.3
        ,
        1.2
        ,
        0.3
        ,
        0.2
        ,
        0.0
        ,
        -0.2
        ,
        0.5
        ,
        1.0
        ,
        0.7
        ,
        0.6
        ,
        0.7
        ,
        1.1
        ,
        1.2
        ,
        1.2
        ,
        1.9
        ,
        1.9
        ,
        2.0
        ,
        2.0
        ,
        2.0
        ,
        2.3
        ,
        1.7
        ,
        1.8
        ,
        1.7
        ,
        1.6
        ,
        2.0
        ,
        1.8
        ,
        1.7
        ,
        1.7
        ,
        1.8
        ,
        2.3
        ,
        2.5
        ,
        2.4
        ,
        3.5
        ,
        3.7
        ,
        3.8
        ,
        3.5
        ,
        3.2
        ,
        3.5
        ,
        3.1
        ,
        3.3
        ,
        3.4
        ,
        3.4
        ,
        2.7
        ,
        2.4
        ,
        1.9
        ,
        1.7
        ,
        1.7
        ,
        1.7
        ,
        1.3
        ,
        1.6
        ,
        1.4
        ,
        1.3
        ,
        1.0
        ,
        0.9
        ,
        1.1
        ,
        1.4
        ,
        0.2
        ,
        0.2
        ,
        0.2
        ,
        0.1
        ,
        0.4
        ,
        0.0
        ,
        0.5
        ,
        0.6
        ,
        0.7
        ,
        0.7,
        0.6, 0.1,
        0.1, 0.1, 0.2, 0.5, 0.7, 0.8, 0.5, 0.3, 0.4, 0.2, 0.1, 0.1,
        0.6, 0.5, 0.3, 0.6, 0.1, 0.1, 0.5, 0.6, 0.5, 0.8, 1.5, 2.0,
        2.2, 2.5, 2.6, 2.0, 2.4, 2.3, 2.5, 2.5, 2.7, 2.9, 2.6, 2.4,
        2.2, 1.8, 1.7, 1.9, 2.2, 2.6, 2.3, 2.5, 2.3, 2.2, 2.0, 2.0,
        2.5, 2.7, 3.0, 2.8, 2.9, 2.7, 2.9, 2.9, 2.7, 2.7, 3.1, 3.2,
        3.6, 3.7, 3.4
)


var currentYear = startingYear
var currentMonth = 1
var type = "THIS_MONTH_VS_PREVIOUS_YEARS_MONTH"

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

println(result)

print("\n\n\n")
