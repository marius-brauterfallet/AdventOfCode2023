package day12

import getInput
import java.util.regex.Pattern

fun main() {
    val input = getInput("day12", "input.txt").parseInput()

    part1(input)
}

fun part1(input: List<Pair<String, List<Int>>>) {
    val result = input
//        .let { listOf(it[5]) }
        .map { getCombinations(it) }
//        .onEach { println(it) }
        .sum()

    println("Part 1 result: $result")
}

fun getCombinations(line: Pair<String, List<Int>>): Int {
    val (springString, springGroups) = line

    val startIndices = mutableListOf<Int>()

    println("\n$springString, $springGroups")

    fun getCombinationsRec(startIndex: Int = 0, remainingSprings: List<Int> = springGroups): Int {
        if (remainingSprings.isEmpty()) {
            val startIndicesAndLengths = startIndices.zip(springGroups)

            val combination = springString.mapIndexed { index, c ->
                if (startIndicesAndLengths.any { (start, length) -> index >= start && index < start + length }) {
                    '#'
                } else c
            }.joinToString("")

            println("$combination ${combination.replace('?', '_')}")

            return 1
        }

        val currentSpring = remainingSprings.first()

        var result = 0

        for (i in startIndex..<springString.length) {
            if (i + currentSpring > springString.length) {
                return result
            }

            if (i > 0 && springString[i - 1] == '#') {
                return result
            }

            if (springString.substring(i, i + currentSpring).all { it == '#' || it == '?' }
                && (i + currentSpring == springString.length || springString[i + currentSpring] != '#')) {

                startIndices.add(i)
                result += getCombinationsRec(i + currentSpring + 1, remainingSprings.drop(1))
                startIndices.removeLast()
            }
        }

        return result
    }

    val result = getCombinationsRec()
    println(result)
//    readln()
    return result
}

private fun List<String>.parseInput(): List<Pair<String, List<Int>>> {
    return map { line ->
        val (springString, springGroupsString) = line.split(" ")

        val springGroups = springGroupsString.split(",").map { it.toInt() }
        val simplifiedSpringString = simplifySpringString(springString)

        simplifiedSpringString to springGroups
    }
}

fun simplifySpringString(springString: String): String {
    val springStringMatcher = Pattern.compile("([?#]+)").matcher(springString)

    return buildString {
        while (springStringMatcher.find()) {
            append(springStringMatcher.group(1))
            append('.')
        }
    }.dropLast(1)
}
