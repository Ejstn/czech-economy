package nationalbudget

fun main() {

    val startingYear = 1993

    var result = StringBuilder()

    result.append("INSERT INTO public_debt (year, value_millions_crowns) VALUES \n")

    val values = listOf(
            158800,
            157300,
            154400,
            155200,
            173141,
            194676,
            228356,
            289324,
            345045,
            395898,
            493185,
            592900,
            691176,
            802493,
            892300,
            999810,
            1178240,
            1344060,
            1499374,
            1667633,
            1683338,
            1663663,
            1672977,
            1613374,
            1624716,
            1622004,
            1640185
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

}