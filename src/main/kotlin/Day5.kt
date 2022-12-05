import java.util.*

fun init(): Pair<MutableList<Stack<String>>, String> {
    val list = mutableListOf<Stack<String>>()
    val (init, logic) = input("Day5").readText().split(lineSeparator(2))
    repeat(init.split(lineSeparator(1)).last().trim().last().digitToInt()) { list.add(Stack()) }
    init
        .split(lineSeparator(1))
        .dropLast(1)
        .reversed()
        .forEach {
            var s = it
            while (true) {
                s = s.dropWhile(Char::isWhitespace)
                val diff = (it.length - s.length) / 4
                if (s.isEmpty()) {
                    break
                }
                s = s.drop(1)
                list[diff].add(s.take(1))
                s = s.drop(2)
            }
        }

    return list to logic
}

fun part1() {
    val (list, logic) = init()

    logic
        .lines()
        .map {
            it
                .split("(move | from | to )".toRegex())
                .drop(1)
                .map(String::toInt)
        }
        .forEach { (times, from, to) ->
            repeat(times) {
                list[to - 1].push(list[from - 1].pop())
            }
        }

    list.forEach {
        print(it.peek())
    }
}


fun part2() {
    val (list, logic) = init()

    logic
        .lines()
        .map {
            it
                .split("(move | from | to )".toRegex())
                .drop(1)
                .map(String::toInt)
        }
        .forEach { (times, from, to) ->
            list[to - 1].addAll(list[from - 1].takeLast(times))
            repeat(times) { (list[from - 1].pop()) }
        }

    list.forEach {
        print(it.peek())
    }
}

fun main() {
    part1()
    part2()
}