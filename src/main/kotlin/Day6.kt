fun main() {
    val size1 = 4
    input("Day6")
        .readText()
        .windowed(size1)
        .map(String::toSet)
        .indexOfFirst {
             it.size == size1
        }
        .also {
            println(it + size1)
        }

    val size2 = 14
    input("Day6")
        .readText()
        .windowed(size2)
        .map(String::toSet)
        .indexOfFirst {
             it.size == size2
        }
        .also {
            println(it + size2)
        }
}