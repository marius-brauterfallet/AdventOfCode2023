package day11

import getInput
import kotlin.math.abs

fun main() {
    val input = getInput("day11", "input.txt")

    part1(input)
}

fun part1(input: List<String>) {
    val result = input
        .getGalaxiesWithExpansion()
        .allUniquePairs()
        .sumOf { (first, second) -> distanceBetweenGalaxies(first, second) }

    println("Part 1 result: $result")
}

private fun List<String>.getGalaxiesWithExpansion(): List<Pair<Int, Int>> {
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
        val newX = galaxy.first + xValuesWithoutGalaxies.count { galaxy.first > it }
        val newY = galaxy.second + yValuesWithoutGalaxies.count { galaxy.second > it }

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

fun distanceBetweenGalaxies(galaxy1: Pair<Int, Int>, galaxy2: Pair<Int, Int>): Int {
    return abs(galaxy1.first - galaxy2.first) + abs(galaxy1.second - galaxy2.second)
}