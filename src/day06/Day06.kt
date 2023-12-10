package day06

data class Race(val time: Int, val distance: Long)

class PartA {
//    private val input = listOf(
//        Race(7, 9),
//        Race(15, 40),
//        Race(30, 200)
//    )
    private val input = listOf(
        Race(38, 241),
        Race(94, 1549),
        Race(79, 1074),
        Race(70, 1091)
    )

    private fun calcDistance(hold: Int, max: Int): Int {
        return hold * (max - hold)
    }

    private fun testRun(race: Race): Int {
        var count = 0
        for (i in 0..race.time) {
            val distance = calcDistance(i, race.time)
            if (distance > race.distance) {
                count++
            }
        }
        return count
    }

    fun run() {

        val winningList = input.map {
            testRun(it)
        }

        val total = winningList.reduce { acc, i -> acc * i }
        println("total: ${ total }")

    }
}

class PartB {
//    private val race = Race(71530, 940200)
    private val race = Race(38947970, 241154910741091)

    private fun calcDistance(hold: Long, max: Int): Long {
        return hold * (max - hold)
    }

    private fun testRun(race: Race): Int {
        var count = 0
        for (i in 0..race.time) {
            val distance = calcDistance(i.toLong(), race.time)
            if (distance > race.distance) {
                count++
            }
        }
        return count
    }

    fun run() {
        val result = testRun(race)

        println("result: ${ result }")
    }
}

fun main() {
//    PartA().run()
    PartB().run()
}