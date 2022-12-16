fun main() {
    val template = "Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)".toRegex()
    val data = input("Day16").readLines()
    val nameToInd = data.mapIndexed { index, line ->
        val (from, _, _) = template.matchEntire(line)!!.destructured
        from to index
    }.toMap()

    val rates = mutableMapOf<Int, Int>()
    val distances = List(nameToInd.size) { i -> MutableList(nameToInd.size) { j -> if (i != j) Int.MAX_VALUE else 0 } }

    data.forEach { line ->
        val (from, rateStr, to) = template.matchEntire(line)!!.destructured
        val rate = rateStr.toInt()

        if (rate != 0) rates[nameToInd[from]!!] = rate

        to.split(", ").forEach {
            distances[nameToInd[from]!!][nameToInd[it]!!] = 1
        }
    }

    for (k in distances.indices) {
        for (i in distances.indices) {
            for (j in distances.indices) {
                distances[i][j] = minOf(distances[i][k].toLong() + distances[k][j], distances[i][j].toLong()).toInt()
            }
        }
    }

    fun startFlow(from: Int = nameToInd["AA"]!!, pressure: Int = 0, time: Int = 30, used: Set<Int> = setOf(), withElephant: Boolean = false): Int {
        if (used == rates.keys)
            return pressure

        return rates.filterKeys { to ->
            time - distances[from][to] - 1 > 0 && to !in used
        }.maxOfOrNull { (to, rate) ->
            val nextTime = time - distances[from][to] - 1
            maxOf(startFlow(
                to,
                pressure + nextTime * rate,
                nextTime,
                used + to,
                withElephant
            ), if (withElephant) startFlow(pressure = pressure, time = 26, used = used) else pressure)
        } ?: pressure

    }
    println(startFlow())
    println(startFlow(time = 26, withElephant = true))
}

