fun main() {
    val s = input("Day1")
        .readText()
        .split(lineSeparator(2))
        .map { it.lines().sumOf(String::toInt) }
        .toMutableList()

    (1..3).map { Heap(s, HeapMode.MAX).extract() }.also {
        println(it.first())
        println(it.sum())
    }
}