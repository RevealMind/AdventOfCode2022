private sealed interface Command

private class Add(val value: Int) : Command
private object Noop : Command

private fun List<String>.toCommand(): Command {
    return when (this[0]) {
        "addx" -> Add(this[1].toInt())
        "noop" -> Noop
        else -> throw UnsupportedOperationException()
    }
}


fun main() {
    part1()
    part2()

}

private fun part2() {
    dataPrepare(239).sumOfEachElements()
        .chunked(40) { line ->
            line.mapIndexed { index, i ->
                if (index + 1 in i..i + 2) "#" else "."
            }
        }
        .forEach {
            println(it.joinToString(""))
        }
}

private fun part1() {
    dataPrepare(219).sumOfEachElements()
        .also {
            val result = listOf(20, 60, 100, 140, 180, 220).sumOf { ind ->
                it[ind - 1] * ind
            }

            println(result)
        }
}

private fun List<Int>.sumOfEachElements() = reversed()
    .windowed(size, partialWindows = true) { it.sum() }
    .reversed()

private fun dataPrepare(size: Int) = input("Day10")
    .readLines()
    .map { it.split(" ") }
    .map { it.toCommand() }
    .flatMap { command ->
        when (command) {
            is Add -> listOf(0, command.value)
            is Noop -> listOf(0)
        }
    }
    .run {
        listOf(1, *take(size).toTypedArray())
    }