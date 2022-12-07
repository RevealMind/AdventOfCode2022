import kotlin.math.min

class Dir(val name: String, val parent: Dir?) {
    val dirs = mutableListOf<Dir>()
    val files = mutableSetOf<File>()

    private fun getSize() = files.sumOf { it.size }

    fun getAllSize(): Long = dirs.sumOf { it.getAllSize() } + getSize()

    fun addDir(dir: Dir) {
        if (!dirs.map { it.name }.contains(dir.name))
            dirs.add(dir)
    }

    fun addFile(file: File) {
        if (!files.map { it.name }.contains(file.name))
            files.add(file)
    }

    fun findDir(name: String) =
        dirs.find { it.name == name }
}

data class File(val name: String, val size: Long)

fun getRoot(dir: Dir): Dir {
    var curDir = dir
    while (curDir.parent != null) {
        curDir = curDir.parent!!
    }
    return curDir
}

fun addData(to: Dir, data: List<String>) {
    data.map { it.split(" ") }.map { (type, name) ->
        when (type) {
            "dir" -> to.addDir(Dir(name, to))
            else -> to.addFile(File(name, type.toLong()))
        }
    }
}

fun move(command: String, curDir: Dir): Dir {
    val (_, name) = command.split(" ")
    return when (name) {
        "/" -> getRoot(curDir)
        ".." -> curDir.parent ?: curDir
        else -> curDir.findDir(name) ?: throw IllegalAccessError()
    }
}

fun bfs(dir: Dir): Long {
    val allSize = dir.getAllSize()
    val s = dir.dirs.sumOf {bfs(it)}
    return if (allSize <= 100000) {
        s + allSize
    } else {
        s
    }
}

fun bfs2(dir: Dir, min: Long): Long {
    val allSize = dir.getAllSize()
    val s = dir.dirs.minOfOrNull {bfs2(it, min)} ?: Long.MAX_VALUE
    return if (allSize < min) {
        s
    } else {
        min(s, allSize)
    }
}

fun main() {
    var curDir = Dir("/", null)
    val data = input("Day7").readText().split("$").map(String::trim).drop(1)

    data.forEach {
        val command = it.lines()
        when (command.first()) {
            "ls" -> addData(curDir, command.drop(1))
            else -> curDir = move(command.first(), curDir)
        }
    }
    val root = getRoot(curDir)

    println(bfs(root))
    println(bfs2(root, 30_000_000 - (70_000_000 - root.getAllSize())))
}