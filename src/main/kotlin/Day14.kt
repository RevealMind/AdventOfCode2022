private class Map(val rocks: Set<Point>, val start: Point) {
    val border = (rocks + start).border
    private var blocks = rocks.toMutableSet()

    fun draw() {
        val (min, max) = (blocks + start).border
        println("=".repeat(max.x - min.x + 1))
        (min.y..max.y).map { y ->
            (min.x..max.x).map { x ->
                when (Point(x, y)) {
                    in rocks -> "#"
                    in blocks -> "o"
                    start -> "+"
                    else -> "."
                }
            }
        }.also { println(it.joinToString("\n") { line -> line.joinToString("") }) }
        println("=".repeat(max.x - min.x + 1))
    }

    private fun Point.isInBorder() =
        x in border.first.x..border.second.x && y in border.first.y..border.second.y

    fun simulate1() {
        var curPosition = start
        blocks = rocks.toMutableSet()
        var cnt = 0
        while (true) {
            val down = curPosition + DOWN
            val left = curPosition + LEFT_DIAG
            val right = curPosition + RIGHT_DIAG
            curPosition = if (down !in blocks) {
                if (!down.isInBorder()) break
                down
            } else if (left !in blocks) {
                if (!left.isInBorder()) break
                left
            } else if (right !in blocks) {
                if (!right.isInBorder()) break
                right
            } else {
                blocks.add(curPosition)
                cnt++
                if (curPosition == start) break
                start
            }
        }
        draw()
        println("Sands: $cnt")
    }

    private fun Point.isOnFloor() =
        y == border.second.y + 2

    fun simulate2() {
        var curPosition = start
        blocks = rocks.toMutableSet()
        var cnt = 0
        while (true) {
            val down = curPosition + DOWN
            val left = curPosition + LEFT_DIAG
            val right = curPosition + RIGHT_DIAG
            curPosition = if (down !in blocks && !down.isOnFloor()) {
                down
            } else if (left !in blocks && !left.isOnFloor()) {
                left
            } else if (right !in blocks && !right.isOnFloor()) {
                right
            } else {
                blocks.add(curPosition)
                cnt++
                if (curPosition == start) break
                start
            }
        }
        draw()
        println("Sands: $cnt")
    }

    companion object {
        val DOWN = Point(0, 1)
        val LEFT_DIAG = Point(-1, 1)
        val RIGHT_DIAG = Point(1, 1)
    }
}


fun main() {
    val rocks = mutableSetOf<Point>()
    input("Day14").readLines().forEach { line ->
        rocks += line.split(" -> ", ",").chunked(2) { (x, y) -> Point(x.toInt(), y.toInt()) }
            .windowed(2) { (left, right) ->
                left.to(right)
            }.flatten()
    }

    Map(rocks, Point(500, 0)).also {
        it.simulate1()
        it.simulate2()
    }
}