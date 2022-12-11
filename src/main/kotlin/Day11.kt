data class Monkey(val items: ArrayDeque<Long>, val operation: (Long) -> Long, val test: (Long) -> Int)

interface Operation {
    fun evaluate(x: Long): Long
}

abstract class BinaryOperation(val left: Operation, val right: Operation) : Operation

class Sum(left: Operation, right: Operation) : BinaryOperation(left, right) {
    override fun evaluate(x: Long) = left.evaluate(x) + right.evaluate(x)
}

class Mul(left: Operation, right: Operation) : BinaryOperation(left, right) {
    override fun evaluate(x: Long) = left.evaluate(x) * right.evaluate(x)
}

class Variable : Operation {
    override fun evaluate(x: Long) = x
}

class Const(private val x: Long) : Operation {
    override fun evaluate(x: Long) = this.x
}

fun main() {
    var divider = 1L

    val monkeys =
        input("Day11").readText().split(lineSeparator(2))

    val data = monkeys.map {
        val items = ArrayDeque<Long>()
        var op: Operation? = null
        var div = 1L
        var onTrue = 0
        var onFalse = 0
        it.lines().drop(1).forEach { line ->
            val t = line.split(":").map(String::trim)
            when (t.first()) {
                "Starting items" -> items.addAll(t.last().split(", ").map(String::toLong))
                "Operation" -> op = createOperation(t.last().split(" ").drop(2).map(String::trim))
                "Test" -> div = t.last().split(" ").last().toLong()
                "If true" -> onTrue = t.last().split(" ").last().toInt()
                "If false" -> onFalse = t.last().split(" ").last().toInt()
                else -> throw UnsupportedOperationException()
            }
        }
        divider *= div
        Monkey(items, op!!::evaluate) { x -> if (x % div == 0L) onTrue else onFalse }
    }

    val inspectCount = MutableList(data.size) { 0L }

    repeat(10_000) {
        data.forEachIndexed { index, monkey ->
            while (monkey.items.isNotEmpty()) {
                inspectCount[index]++
                val item = monkey.items.removeFirst()
                val updatedItem = (monkey.operation(item)) % divider
                val ind = monkey.test(updatedItem)
                data[ind].items.addLast(updatedItem)
            }
        }
    }
    val (a, b) = inspectCount.sortedDescending()
    println(a * b)
}

fun createOperation(info: List<String>): Operation {
    val left = info.first().toValue()
    val right = info.last().toValue()

    return when (info[1]) {
        "+" -> Sum(left, right)
        "*" -> Mul(left, right)
        else -> throw UnsupportedOperationException()
    }
}

fun String.toValue() =
    when (this) {
        "old" -> Variable()
        else -> Const(this.toLong())
    }