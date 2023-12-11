import java.io.File
import kotlin.io.path.Path

fun getInput(day: String, filename: String = "input.txt"): List<String> {
    val path = Path("../input/$day/$filename")

    return File(path.toUri()).readLines()
}

fun getInputAsSingleString(day: String, filename: String = "input.txt"): String {
    val path = Path("../input/$day/$filename")

    return File(path.toUri()).readText()
}