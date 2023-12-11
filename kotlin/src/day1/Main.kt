package day1

import getInput

private val validNumberStrings = listOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun main() {
    val input = getInput("day1")

    part1(input)
    part2(input)
}

fun part1(input: List<String>) {
    val result = input.sumOf { line ->
        line
            .filter { char -> char.isDigit() }
            .let { "${it.first()}${it.last()}".toInt() }
    }

    println("Part 1 result: $result")
}

fun part2(input: List<String>) {
    val result = input.sumOf { line ->
        line
            .mapIndexed { index, char ->
                if (char.isDigit()) char.digitToInt()
                else {
                    substringToIntOrNull(line.substring(index))
                }
            }
            .filterNotNull()
            .let { "${it.first()}${it.last()}" }
            .toInt()
    }

    println("Part 2 result: $result")
}

private fun substringToIntOrNull(substring: String): Int? {
    validNumberStrings.forEach { (numberString, number) ->
        if (numberString.length > substring.length) return@forEach

        if (substring.substring(0, numberString.length) == numberString) return number
    }

    return null
}