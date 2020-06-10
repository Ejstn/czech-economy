fun main() {

    val startingYear = 1990

    var result = StringBuilder()
    result.append("INSERT INTO gross_domestic_product (year, type, value_millions_crowns) VALUES \n")

    val values = listOf(
            2734489,
            2469809,
            2414015,
            2445728,
            2502820,
            2655338,
            2767468,
            2751011,
            2741968,
            2781256,
            2899925,
            2984277,
            3033592,
            3142892,
            3297100,
            3512515,
            3753246,
            3963527,
            4069840,
            3874383,
            3962464,
            4032910,
            4000653,
            3981303,
            4089400,
            4306516,
            4412049,
            4604088,
            4735123,
            4856623
    )

    var currentYear = startingYear

    print("\n\n\n")

    values.forEachIndexed { index, value ->

        result.append("($currentYear, 'REAL_2010_PRICES', $value)")

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
