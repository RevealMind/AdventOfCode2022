import java.io.File
import kotlin.math.max
import kotlin.math.min

fun input(fileName: String) = File("src/main/resources/$fileName.txt").bufferedReader(Charsets.UTF_8)

fun lineSeparator(count: Int) = System.lineSeparator().repeat(count)

typealias Grid<T> = List<List<T>>

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

fun Point.to(other: Point) = (min(x, other.x)..max(x, other.x)).flatMap { x ->
    (min(y, other.y)..max(y, other.y)).map { y ->
        Point(x, y)
    }
}

val Iterable<Point>.border
    get() = run {
        val minX = this.minOf { it.x }
        val minY = this.minOf { it.y }
        val maxX = this.maxOf { it.x }
        val maxY = this.maxOf { it.y }
        Point(minX, minY) to Point(maxX, maxY)
    }
