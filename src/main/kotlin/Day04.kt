fun main() {
    input("Day4")
        .readLines()
        .map { it.split(",").map { s -> s.split("-").map(String::toInt) }.map { (a, b) -> (a..b).toSet() } }
        .count { (a, b) ->
            a.containsAll(b) || b.containsAll(a)
        }.also {
            println(it)
        }

    input("Day4")
        .readLines()
        .map { it.split(",").map { s -> s.split("-").map(String::toInt) }.map { (a, b) -> (a..b).toSet() } }
        .count { (a, b) ->
            (a intersect b).isNotEmpty()
        }.also {
            println(it)
        }
}