package day6

import getInput
import java.util.regex.Pattern

fun main() {
    val input = getInput("day6", "input.txt")
    val inputPart1 = input.parseInputPart1()
    val inputPart2 = input.parseInputPart2()

    part1(inputPart1)
    part2(inputPart2)
}

fun part2(input: Pair<Long, Long>) {
    val result = getPossibleVictories(input)

    println("Part 2 result: $result")
}

private fun List<String>.parseInputPart2(): Pair<Long, Long> {
    val (timeLine, distanceLine) = this

    val availableTime = timeLine.split(":")[1].replace(" ", "").toLong()
    val bestDistance = distanceLine.split(":")[1].replace(" ", "").toLong()

    return availableTime to bestDistance
}

fun part1(input: List<Pair<Int, Int>>) {
    val result = input
        .map { getPossibleVictories(it.let { it.first.toLong() to it.second.toLong() }) }
        .reduce { acc, n -> acc * n }

    println("Part 1 result: $result")
}

fun getPossibleVictories(values: Pair<Long, Long>): Int {
    val (availableTime, distanceToBeat) = values

    var firstValue = availableTime / 2
    var secondValue = availableTime - firstValue

    var result = 0

    while (firstValue * secondValue > distanceToBeat) {
        result += 2

        firstValue--
        secondValue++
    }

    if (availableTime % 2 == 0L) result--

    return result
}

fun List<String>.parseInputPart1(): List<Pair<Int, Int>> {
    val (timeLine, distanceLine) = this

    val timesString = timeLine.split(":")[1]
    val distancesString = distanceLine.split(":")[1]

    val numberPattern = Pattern.compile("(\\d+)")

    val timesMatcher = numberPattern.matcher(timesString)
    val distancesMatcher = numberPattern.matcher(distancesString)

    return buildList {
        while (timesMatcher.find() && distancesMatcher.find()) {
            add(timesMatcher.group(1).toInt() to distancesMatcher.group(1).toInt())
        }
    }
}