package day13

import getInput

fun main() {
    val input = getInput("day13", "input.txt").parseInput()

    part1(input)
    part2(input)
}

fun part2(input: List<List<List<Char>>>) {
    val result = findReflections(input, 1).sumOf { (dir, linesToCount) ->
        if (dir == 'H') 100 * linesToCount else linesToCount
    }

    println("Part 2 result: $result")
}

fun part1(input: List<List<List<Char>>>) {
    val result = findReflections(input, 0).sumOf { (dir, linesToCount) ->
        if (dir == 'H') 100 * linesToCount else linesToCount
    }

    println("Part 1 result: $result")
}

/**
 * Returns a list of pairs where the first element is either 'H' for horizontal reflection, or 'V' for a vertical
 * reflection. The second element represents the index of the row to the right of the line of reflection in the case of
 * a vertical reflection, or under the line of reflection in the case of a horizontal reflection.
 */
fun findReflections(input: List<List<List<Char>>>, targetFaults: Int = 0) = input.map { findReflection(it, targetFaults)}

fun findReflection(map: List<List<Char>>, targetFaults: Int): Pair<Char, Int> {
    val result = checkHorizontalReflection(map, targetFaults) ?: checkVerticalReflection(map, targetFaults)

    return result!!
}

fun checkVerticalReflection(map: List<List<Char>>, targetFaults: Int): Pair<Char, Int>? {
    for (x in 1..<map.first().size) {
        if (checkVerticalLineOfReflection(map, x) == targetFaults) return 'V' to x
    }

    return null
}

fun checkVerticalLineOfReflection(map: List<List<Char>>, lineToCheck: Int): Int {
    var high = lineToCheck
    var low = lineToCheck - 1

    var faults = 0

    while (low >= 0 && high < map.first().size) {
        val line1 = map.map { it[low] }
        val line2 = map.map { it[high] }

        faults += line1.zip(line2).count {
            it.first != it.second
        }

        low--
        high++
    }

    return faults
}

fun checkHorizontalReflection(map: List<List<Char>>, targetFaults: Int): Pair<Char, Int>? {
    for (y in 1..<map.size) {
        if (checkHorizontalLineOfReflection(map, y) == targetFaults) return 'H' to y
    }

    return null
}

fun checkHorizontalLineOfReflection(map: List<List<Char>>, lineToCheck: Int): Int {
    var high = lineToCheck
    var low = lineToCheck - 1

    var faults = 0

    while (low >= 0 && high < map.size) {
        faults += map[low].zip(map[high]).count {
            it.first != it.second
        }

        high++
        low--
    }

    return faults
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
