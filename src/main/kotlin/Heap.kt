import java.util.Collections

enum class HeapMode {
    MIN, MAX
}

class Heap<T>(private val arr: MutableList<T>, private val mode: HeapMode) where T : Number, T : Comparable<T> {
    init {
        for (i in size / 2 downTo 0) {
            siftDown(i)
        }
    }

    private fun compare(a: Int, b: Int) = when (mode) {
        HeapMode.MIN -> arr[a] < arr[b]
        HeapMode.MAX -> arr[a] > arr[b]
    }

    private val size: Int
        get() = arr.size

    private fun siftDown(ind: Int) {
        var i = ind
        while (true) {
            var j = i
            val left = 2 * i + 1
            val right = 2 * i + 2

            if (left < size && compare(left, i)) {
                j = left
            }
            if (right < size && compare(right, i) && compare(right, left)) {
                j = right
            }
            if (i == j) {
                break
            }

            Collections.swap(arr, i, j)

            i = j
        }
    }

    private fun siftUp(ind: Int) {
        var i = ind
        while (i >= 0 && compare(i, (i - 1) / 2)) {
            Collections.swap(arr, i, (i - 1) / 2)
            i = (i - 1) / 2
        }
    }

    fun insert(x: T) {
        arr.add(x)
        siftUp(size - 1)
    }

    fun extract(): T {
        Collections.swap(arr, 0, size - 1)
        val res = arr.removeLast()
        siftDown(0)
        return res
    }
}