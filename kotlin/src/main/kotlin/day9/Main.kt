package day9

import getInput

fun main() {
    val input = getInput("day9", "input.txt").map { line ->
        line
            .split(" ")
            .map { it.toInt() }
            .let(::buildSequences)
    }

    part1(input)
    part2(input)
}

fun part2(input: List<List<List<Int>>>) {
    val result = input.sumOf { history ->
        history
            .map { it.first() }
            .reduce { acc, n -> n - acc }
    }

    println("Part 2 result: $result")
}

fun part1(input: List<List<List<Int>>>) {
    val result = input
        .map { it.reversed() }
        .sumOf { history -> history.sumOf { it.last() } }

    println("Part 1 result: $result")
}

fun buildSequences(line: List<Int>): List<List<Int>> {
    tailrec fun buildSequencesRec(line: List<Int>, result: List<List<Int>> = emptyList()): List<List<Int>> {
        if (line.all { it == 0 }) return result

        val newLine = line.zipWithNext { a, b -> b - a }
        val newResult = result + listOf(newLine)

        return buildSequencesRec(newLine, newResult)
    }

    return listOf(line) + buildSequencesRec(line)
}
