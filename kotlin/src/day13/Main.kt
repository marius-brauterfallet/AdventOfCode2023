package day13

import getInput

fun main() {
    val input = getInput("day13", "input.txt").parseInput()

    part1(input)
}

fun part1(input: List<List<List<Char>>>) {
    val result = findReflections(input).sumOf { (dir, linesToCount) ->
        if (dir == 'H') 100 * linesToCount else linesToCount
    }

    println("Part 1 result: $result")
}

/**
 * Returns a list of pairs where the first element is either 'H' for horizontal reflection, or 'V' for a vertical
 * reflection. The second element represents the index of the row to the right of the line of reflection in the case of
 * a vertical reflection, or under the line of reflection in the case of a horizontal reflection.
 */
fun findReflections(input: List<List<List<Char>>>) = input.map(::findReflection)

fun findReflection(map: List<List<Char>>): Pair<Char, Int> {
    val result = checkHorizontalReflection(map) ?: checkVerticalReflection(map)

    return result!!
}

fun checkVerticalReflection(map: List<List<Char>>): Pair<Char, Int>? {
    for (x in 1..<map.first().size) {
        if (checkVerticalLineOfReflection(map, x)) return 'V' to x
    }

    return null
}

fun checkVerticalLineOfReflection(map: List<List<Char>>, lineToCheck: Int): Boolean {
    var high = lineToCheck
    var low = lineToCheck - 1

    while (low >= 0 && high < map.first().size) {
        val line1 = map.map { it[low] }
        val line2 = map.map { it[high] }

        if (line1 != line2) return false

        low--
        high++
    }

    return true
}

fun checkHorizontalReflection(map: List<List<Char>>): Pair<Char, Int>? {
    for (y in 1..<map.size) {
        if (checkHorizontalLineOfReflection(map, y)) return 'H' to y
    }

    return null
}

fun checkHorizontalLineOfReflection(map: List<List<Char>>, lineToCheck: Int): Boolean {
    var high = lineToCheck
    var low = lineToCheck - 1

    while (low >= 0 && high < map.size) {
        if (map[low] != map[high]) return false

        high++
        low--
    }

    return true
}

private fun List<String>.parseInput(): List<List<List<Char>>> {
    val emptyIndices = this
        .mapIndexed { index, line -> if (line.isEmpty()) index else null }
        .filterNotNull()
        .let { it + size }

    return buildList {
        var startIndex = 0

        emptyIndices.forEach { endIndex ->
            add(this@parseInput.subList(startIndex, endIndex).map { it.toList() })

            startIndex = endIndex + 1
        }
    }
}
