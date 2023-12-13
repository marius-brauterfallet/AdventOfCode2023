package day11

import getInput
import kotlin.math.abs

fun main() {
    val input = getInput("day11", "input.txt")

    part1(input)
    part2(input)
}

fun part2(input: List<String>) {
    val result = getResult(input, 999999)

    println("Part 2 result: $result")
}

fun part1(input: List<String>) {
    val result = getResult(input)

    println("Part 1 result: $result")
}

fun getResult(input: List<String>, expansionFactor: Int = 1): Long {
    return input
        .getGalaxiesWithExpansion(expansionFactor.toLong())
        .allUniquePairs()
        .sumOf { (first, second) -> distanceBetweenGalaxies(first, second) }
}

private fun List<String>.getGalaxiesWithExpansion(expansionFactor: Long): List<Pair<Long, Long>> {
    val xValuesWithoutGalaxies = first().mapIndexedNotNull { index, _ ->
        if (all { it[index] == '.' }) index else null
    }

    val yValuesWithoutGalaxies = mapIndexedNotNull { index, line ->
        if (line.all { it == '.' }) index else null
    }

    val galaxiesWithoutExpansion = mapIndexedNotNull { y, line ->
        line.mapIndexedNotNull { x, cell ->
            if (cell == '#') x to y else null
        }
    }.flatten()

    val galaxiesWithExpansion = galaxiesWithoutExpansion.map { galaxy ->
        val newX = galaxy.first + (xValuesWithoutGalaxies.count { galaxy.first > it } * expansionFactor)
        val newY = galaxy.second + (yValuesWithoutGalaxies.count { galaxy.second > it } * expansionFactor)

        newX to newY
    }

    return galaxiesWithExpansion
}

fun <T> List<T>.allUniquePairs(): List<Pair<T, T>> {
    return flatMapIndexed { i, first ->
        drop(i + 1).map { second ->
            first to second
        }
    }
}

fun distanceBetweenGalaxies(galaxy1: Pair<Long, Long>, galaxy2: Pair<Long, Long>): Long {
    return abs(galaxy1.first - galaxy2.first) + abs(galaxy1.second - galaxy2.second)
}