fun main() {
    listOf(4, 14).forEach { size ->
        input("Day6").readText()
            .windowed(size, transform = CharSequence::toSet)
            .indexOfFirst {
                it.size == size
            }
            .also {
                println(it + size)
            }
    }
}