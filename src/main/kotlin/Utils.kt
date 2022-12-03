import java.io.File

fun input(fileName: String) = File("src/main/resources/$fileName.txt").bufferedReader(Charsets.UTF_8)

fun lineSeparator(count: Int) = System.lineSeparator().repeat(count)
