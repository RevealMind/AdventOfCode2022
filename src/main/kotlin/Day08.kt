private fun part1(data: List<List<Int>>) {
    val (n, m) = data.size to data.first().size

    val list = List(n) { MutableList(m) { false } }

    for (i in 1 until n - 1) {
        var maxLeft = data[i].first()
        var maxRight = data[i].last()
        for (j in 1 until m - 1) {
            val i1 = data[i][j]

            if (i1 > maxLeft) {
                list[i][j] = true
                maxLeft = i1
            }

            val i2 = data[i][m - (j + 1)]

            if (i2 > maxRight) {
                list[i][m - (j + 1)] = true
                maxRight = i2
            }
        }
    }

    for (j in 1 until m - 1) {
        var maxTop = data.first()[j]
        var maxBottom = data.last()[j]
        for (i in 1 until n - 1) {
            val i1 = data[i][j]

            if (i1 > maxTop) {
                list[i][j] = true
                maxTop = i1
            }

            val i2 = data[n - (i + 1)][j]

            if (i2 > maxBottom) {
                list[n - (i + 1)][j] = true
                maxBottom = i2
            }
        }
    }

    println(list.flatten().count {it} + 2 * (n + m - 2))
}

private fun part2(data: List<List<Int>>) {
    val (n, m) = data.size to data.first().size

    val list = List(n) { MutableList(m) { 1 } }

    for (i in 1 until n - 1) {
        for (j in 1 until m - 1) {
            list[i][j] = with(data) { goLeft(i, j) * goRight(i, j) * goDown(i, j) * goUp(i, j) }
        }
    }

    println(list.flatten().maxOrNull())
}

private fun <T: Comparable<T>> Grid<T>.goLeft(i: Int, j: Int): Int {
    var shift = j - 1

    while (shift > 0 && this[i][shift] < this[i][j]) {
        shift--
    }

    return j - shift
}


private fun <T: Comparable<T>> Grid<T>.goRight(i: Int, j: Int): Int {
    var shift = j + 1

    while (shift < this[i].lastIndex && this[i][shift] < this[i][j]) {
        shift++
    }

    return shift - j
}

private fun <T: Comparable<T>> Grid<T>.goDown(i: Int, j: Int): Int {
    var shift = i - 1

    while (shift > 0 && this[shift][j] < this[i][j]) {
        shift--
    }

    return i - shift
}


private fun <T: Comparable<T>> Grid<T>.goUp(i: Int, j: Int): Int {
    var shift = i + 1

    while (shift < this.lastIndex && this[shift][j] < this[i][j]) {
        shift++
    }

    return shift - i
}

fun main() {
    val data = input("Day8").readLines().map { line -> line.chunked(1).map(String::toInt) }

    part1(data)
    part2(data)
}