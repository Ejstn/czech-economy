import java.util.*
import kotlin.collections.ArrayDeque

val startingYear = 1993

var result = StringBuilder()
result.append("INSERT INTO budget_balance (year, value_millions_crowns) VALUES \n")

val values = listOf(
        1081,
        10449,
        7230,
        -1562,
        -15717,
        -29331,
        -29634,
        -46061,
        -67705,
        -45716,
        -109053,
        -93684,
        -56338,
        -97580,
        -66392,
        -20003,
        -192394,
        -156416,
        -142771,
        -101000,
        -81264,
        -77782,
        -62804,
        61774,
        -6151,
        2944,
        -28515
)


var currentYear = startingYear

print("\n\n\n")

values.forEachIndexed { index, value ->

    result.append("($currentYear, $value)")

    if (values.lastIndex != index) {
        result.append(",")
    } else {
        result.append(";")
    }

    result.append("\n")

    currentYear++

}

println(result)

print("\n\n\n")
