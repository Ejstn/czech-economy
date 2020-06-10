fun main() {

    val startingYear = 1990

    val result = StringBuilder()
    result.append("INSERT INTO gross_domestic_product (year, type, value_millions_crowns) VALUES \n")

    val values = listOf(
            674251,
            869888,
            976497,
            1201088,
            1370455,
            1586447,
            1818342,
            1958725,
            2146389,
            2242417,
            2379393,
            2568309,
            2681644,
            2810382,
            3062444,
            3264931,
            3512798,
            3840117,
            4024117,
            3930409,
            3962464,
            4033755,
            4059912,
            4098128,
            4313789,
            4595783,
            4767990,
            5047267,
            5323556,
            5652553
    )

    var currentYear = startingYear

    print("\n\n\n")

    values.forEachIndexed { index, value ->

        result.append("($currentYear, 'NOMINAL', $value)")

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
