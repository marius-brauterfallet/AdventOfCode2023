package day3

import getInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = getInput("day3", "input.txt")

    part1(input)
    part2(input)
}

private fun part2(input: List<String>) {
    val result = input
        .flatMapIndexed { lineIndex, line ->
            line.mapIndexed { charIndex, char ->
                if (char == '*') {
                    checkGear(input, lineIndex, charIndex)
                } else null
            }
        }
        .filterNotNull()
        .sum()

    println("Part 2 result: $result")
}

private fun checkGear(input: List<String>, lineIndex: Int, charIndex: Int): Int? {
    val adjacentNumbers = mutableListOf<Int>()

    // A list of coordinates that belong to a checked number
    val checkedNumbers = mutableSetOf<Pair<Int, Int>>()

    for (verticalIndex in max(0, lineIndex - 1)..min(input.size - 1, lineIndex + 1)) {
        val line = input[verticalIndex]

        for (horizontalIndex in max(0, charIndex - 1)..min(input[0].length, charIndex + 1)) {
            val char = line[horizontalIndex]

            if (verticalIndex to horizontalIndex in checkedNumbers || !char.isDigit()) continue

            checkedNumbers.add(verticalIndex to horizontalIndex)

            var startIndex = horizontalIndex
            var endIndex = horizontalIndex + 1

            while (startIndex > 0 && line[startIndex - 1].isDigit()) {
                startIndex--
                checkedNumbers.add(verticalIndex to startIndex)
            }

            while (endIndex < line.length && line[endIndex].isDigit()) {
                checkedNumbers.add(verticalIndex to endIndex)
                endIndex++
            }

            adjacentNumbers.add(line.substring(startIndex, endIndex).toInt())
        }
    }

    if (adjacentNumbers.size == 2) {
        return adjacentNumbers[0] * adjacentNumbers[1]
    }

    return null
}

private fun part1(input: List<String>) {
    val result = input
        .flatMapIndexed { lineIndex, line ->
            line.mapIndexed { charIndex, char ->
                if (char.isDigit() && (charIndex == 0 || !line[charIndex - 1].isDigit())) {
                    checkPartNumber(input, lineIndex, charIndex)
                } else null
            }
        }
        .filterNotNull()
        .sum()

    println("Part 1 result: $result")
}

private fun checkPartNumber(input: List<String>, lineIndex: Int, charIndex: Int): Int? {
    val numberLine = input[lineIndex]
    var finalIndex = charIndex + 1

    while (finalIndex < numberLine.length && numberLine[finalIndex].isDigit()) finalIndex++

    val indicesToCheck = (max(0, charIndex - 1)..(min(numberLine.length - 1, finalIndex))).map { horizontalIndex ->
        (max(0, lineIndex - 1)..(min(input.size - 1, lineIndex + 1))).map { verticalIndex ->
            val charToCheck = input[verticalIndex][horizontalIndex]

            if (!charToCheck.isDigit() && charToCheck != '.') {
                return numberLine.substring(charIndex, finalIndex).toInt()
            }
        }
    }

    return null
}