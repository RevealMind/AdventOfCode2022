import kotlin.math.abs

private enum class Direction {
    R, U, D, L;

    fun go(rope: List<Pair<Int, Int>>, times: Int): Pair<Set<Pair<Int, Int>>, List<Pair<Int, Int>>> {
        val visited = mutableSetOf<Pair<Int, Int>>()
        val ropeState = rope.toMutableList()

        repeat(times) {
            ropeState[0] = move(ropeState.first())

            for (i in 1..ropeState.lastIndex) {
                ropeState[i] = ropeState[i] follow ropeState[i - 1]
            }

            visited.add(ropeState.last())
        }

        return visited to ropeState
    }

    fun move(start: Pair<Int, Int>): Pair<Int, Int> {
        var (iHead, jHead) = start

        when (this) {
            R -> jHead += 1
            U -> iHead -= 1
            D -> iHead += 1
            L -> jHead -= 1
        }

        return iHead to jHead
    }

    companion object {
        private infix fun Pair<Int, Int>.follow(start: Pair<Int, Int>): Pair<Int, Int> {
            val (iHead, jHead) = start
            val (iTail, jTail) = this

            val iDiff = iHead - iTail
            val jDiff = jHead - jTail

            return when (iDiff to jDiff) {
                0 to 2 -> iTail to jTail + 1
                0 to -2 -> iTail to jTail - 1
                2 to 0 -> iTail + 1 to jTail
                -2 to 0 -> iTail - 1 to jTail
                else ->
                    when {
                        abs(iDiff) <= 1 && abs(jDiff) <= 1 -> this
                        iDiff < 0 && jDiff < 0 -> iTail - 1 to jTail - 1
                        iDiff > 0 && jDiff < 0 -> iTail + 1 to jTail - 1
                        iDiff > 0 && jDiff > 0 -> iTail + 1 to jTail + 1
                        iDiff < 0 && jDiff > 0 -> iTail - 1 to jTail + 1
                        else -> throw IllegalStateException()
                    }
            }
        }
    }
}

fun main() {
    val data = input("Day9")
        .readLines()
        .map { it.split(" ") }
        .map { (a, b) -> Direction.valueOf(a) to b.toInt() }

    listOf(2, 10).forEach { ropeSize ->
        data.fold(Pair(setOf(0 to 0), List(ropeSize) { 0 to 0 })) { (visited, rope), (direction, times) ->
            direction.go(rope, times).run {
                Pair(first + visited, second)
            }
        }.first.count().also { println(it) }
    }
}