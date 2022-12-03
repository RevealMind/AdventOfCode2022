fun main() {
    input("Day3")
        .useLines { it.toList() }
        .sumOf { items ->
            val (first, second) = items.chunked(items.length / 2).map { it.toSet() }
            (first intersect second).single().run {
                if (this in 'a'..'z') {
                    this - 'a' + 1
                } else this - 'A' + 27
            }
        }.also {
            println(it)
        }

    input("Day3")
        .useLines { it.toList() }
        .chunked(3) { it.map(String::toSet) }
        .sumOf { (first, second, third) ->
            (first intersect second intersect third).single().run {
                if (this in 'a'..'z')
                    this - 'a' + 1
                else this - 'A' + 27
            }
        }.also {
            println(it)
        }
}