import kotlin.math.max

private interface Data

private class Element(val element: Int) : Data {
    override fun toString() = "$element"
}

private class ListElement : Data {
    val list = mutableListOf<Data>()

    fun add(e: Data) {
        list.add(e)
    }

    override fun toString(): String {
        return list.joinToString(prefix = "[", postfix = "]")
    }
}

private fun toData(str: String, ind: Int = 0): Pair<ListElement, Int> {
    val res = ListElement()
    var e = ""
    var i = ind
    while (i < str.length) {
        when (str[i]) {
            '[' -> {
                val (list, newI) = toData(str, i + 1)
                i = newI
                res.add(list)
                if (i + 1 >= str.length) {
                    return list to i
                }
            }
            ']' -> {
                if (e.isNotEmpty()) res.add(Element(e.toInt()))
                return res to i
            }
            in '0'..'9' -> e += str[i]
            ',' -> {
                if (e.isNotEmpty())
                    res.add(Element(e.toInt()))
                e = ""
            }
        }
        i++
    }
    return res to i
}

private enum class State {
    IN_RIGHT, NOT_IN_RIGHT, CONTINUE
}

private fun check(a: Data, b: Data): State {
    if (a is Element && b is Element) {
        return when {
            a.element == b.element -> State.CONTINUE
            a.element < b.element -> State.IN_RIGHT
            else -> State.NOT_IN_RIGHT
        }
    } else if (a is ListElement && b is ListElement) {
        for (i in 0..max(a.list.lastIndex, b.list.lastIndex)) {
            return when (i) {
                a.list.size -> {
                    State.IN_RIGHT
                }
                b.list.size -> {
                    State.NOT_IN_RIGHT
                }
                else -> {
                    val state = check(a.list[i], b.list[i])
                    if (state == State.CONTINUE) continue
                    state
                }
            }
        }
        return State.CONTINUE
    } else if (a is Element && b is ListElement) {
        val left = ListElement().apply { add(a) }
        return check(left, b)
    } else if (a is ListElement && b is Element) {
        val right = ListElement().apply { add(b) }
        return check(a, right)
    }
    throw UnsupportedOperationException()
}

fun main() {
    val data = input("Day13").readText().split(lineSeparator(2))
    val pairs: List<List<ListElement>> = data.map { it.lines().map { line -> toData(line).first } }
    pairs.map { check(it.first(), it.last()) }.also { println(it) }
        .foldIndexed(0) { ind, acc, it -> acc + if (it == State.IN_RIGHT) ind + 1 else 0 }.also { println(it) }

    val firstSeparator = toData("[[2]]").first
    val secondSeparator = toData("[[6]]").first
    val newPairs: List<ListElement> = pairs.flatten() + firstSeparator + secondSeparator

    newPairs.sortedWith { o1, o2 ->
        when (check(o1, o2)) {
            State.IN_RIGHT -> -1
            State.NOT_IN_RIGHT -> 1
            State.CONTINUE -> 0
        }
    }.also {
        println((it.indexOf(firstSeparator) + 1) * (it.indexOf(secondSeparator) + 1))
    }
}
