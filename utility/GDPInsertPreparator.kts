import java.util.*
import kotlin.collections.ArrayDeque

val startingYear = 1995

var result = StringBuilder()
result.append("INSERT INTO gross_domestic_product (quarter, year, value_millions_crowns) VALUES \n")

val values = listOf(
        351159,
        391844,
        412120,
        431324,
        409856,
        452065,
        468311,
        488110,
        440911,
        485940,
        503631,
        528243,
        483920,
        542242,
        556440,
        563787,
        503675,
        563116,
        573882,
        601744,
        537494,
        596079,
        611036,
        634784,
        580217,
        640112,
        657814,
        690166,
        615939,
        676051,
        685726,
        703928,
        646079,
        704064,
        717983,
        742256,
        695854,
        754408,
        777041,
        835141,
        749693,
        816772,
        824172,
        874294,
        801890,
        869391,
        895247,
        946270,
        885011,
        949886,
        977949,
        1027271,
        929700,
        1015944,
        1040456,
        1038017,
        933028,
        982413,
        986655,
        1028313,
        917763,
        1002363,
        1003950,
        1038388,
        935801,
        1015093,
        1020757,
        1062104,
        957466,
        1021068,
        1021043,
        1060335,
        947380,
        1020805,
        1038854,
        1091089,
        990626,
        1076850,
        1107159,
        1139154,
        1058526,
        1155270,
        1175617,
        1206370,
        1108360,
        1208935,
        1209349,
        1241346,
        1158117,
        1267130,
        1287481,
        1334539,
        1229325,
        1332561,
        1351243,
        1410427,
        1306535,
        1412299,
        1445571,
        1488148
)


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
