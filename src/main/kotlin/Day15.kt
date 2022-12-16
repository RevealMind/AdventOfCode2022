import kotlin.math.absoluteValue


const val ROW = 2000000
const val MAX_BORDER = 4000000

fun main() {
    val template = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()
    val data = input("Day15").readLines().map { line ->
        val (x1, y1, x2, y2) = template.matchEntire(line)!!.destructured
        Triple(
            Point(x1.toInt(), y1.toInt()),
            Point(x2.toInt(), y2.toInt()),
            (x1.toInt() - x2.toInt()).absoluteValue + (y1.toInt() - y2.toInt()).absoluteValue
        )
    }

    part1(data)
    println(part2(data))
}

private fun part1(
    data: List<Triple<Point, Point, Int>>
) {
    val s = data.flatMap { (s, b, _) -> listOf(s, b) }.toSet()

    data.filter { (s, _, d) -> ROW in (s.y - d)..(s.y + d) }.flatMap { (s, _, d) ->
            val dd = d - (s.y - ROW).absoluteValue
            Point(s.x - dd, ROW).to(Point(s.x + dd, ROW))
        }.toSet().filter { it !in s }.also {
            println(it.size)
        }
}

private fun part2(data: List<Triple<Point, Point, Int>>): Long {
    (0..MAX_BORDER).forEach { y ->
        var x = 0
        while (x < MAX_BORDER) {
            x = data.filter { (s, _, d) ->
                d >= ((s.y - y).absoluteValue + (s.x - x).absoluteValue)
            }.maxOfOrNull { (s, _, d) ->
                s.x + d - (s.y - y).absoluteValue + 1
            } ?: return (x.toLong() * MAX_BORDER + y.toLong())
        }
    }
    throw UnknownError()
}