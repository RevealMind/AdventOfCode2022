fun main() {
    val data = input("Day12").readLines().map { it.toCharArray() }
    val n = data.size
    val m = data[0].size
    val vertexes = List<MutableList<Int>>(n * m) { mutableListOf() }
    var startV = 0
    val starts = mutableListOf<Int>()
    var endV = 0
    var curV = 0

    for (i in 0 until n) {
        for (j in 0 until m) {
            data[i][j] = when (data[i][j]) {
                'a' -> {
                    starts.add(toVertexInd(i, j, m))
                    'a'
                }
                'S' -> {
                    startV = toVertexInd(i, j, m)
                    starts.add(startV)
                    'a'
                }
                'E' -> {
                    endV = toVertexInd(i, j, m)
                    'z'
                }
                else -> data[i][j]
            }
        }
    }

    for (i in 0 until n) {
        for (j in 0 until m) {
            if (j + 1 < m && data[i][j] - data[i][j + 1] <= 1)
                vertexes[curV] += toVertexInd(i, j + 1, m)

            if (j - 1 >= 0 && data[i][j] - data[i][j - 1] <= 1)
                vertexes[curV] += toVertexInd(i, j - 1, m)

            if (i + 1 < n && data[i][j] - data[i + 1][j] <= 1)
                vertexes[curV] += toVertexInd(i + 1, j, m)

            if (i - 1 >= 0 && data[i][j] - data[i - 1][j]  <= 1)
                vertexes[curV] += toVertexInd(i - 1, j, m)

            curV++
        }
    }

    val distance = dijkstra(m, n, endV, vertexes)

    println(distance[startV])

    println(starts.minOf { distance[it]})
}

private fun dijkstra(
    m: Int,
    n: Int,
    startV: Int,
    vertex: List<MutableList<Int>>
): MutableList<Int> {
    val used = MutableList(m * n) { false }
    val distance = MutableList(m * n) { Int.MAX_VALUE }
    distance[startV] = 0
    for (i in vertex.indices) {
        var from = -1
        for (j in vertex.indices) {
            if (!used[j] && (from == -1 || distance[j] < distance[from]))
                from = j
        }
        if (distance[from] == Int.MAX_VALUE) break
        used[from] = true

        for (to in vertex[from]) {
            if (distance[from] + 1 < distance[to]) {
                distance[to] = distance[from] + 1
            }
        }
    }
    return distance
}

fun toVertexInd(i: Int, j: Int, m: Int) = i * m + j