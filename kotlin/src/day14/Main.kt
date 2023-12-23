package day14

import getInput

fun main() {
    val input = getInput("day14", "input.txt").parseInput()

//    part1(input)
    part2(input)
}

fun part2(input: List<List<Char>>) {
    var platform = input.let { rotatePlatform(it, 270) }

    val previousLoads = mutableMapOf<Int, Pair<Int, Int>>()

    for (i in 1..1000000) {
        for (j in 0..<4) {
            platform = tiltPlatform(platform)
            platform = rotatePlatform(platform, 90)
        }

        val platformHash = platform.joinToString { line -> line.joinToString("") }.hashCode()

        if (platformHash in previousLoads) {
            val (previousRound, _) = previousLoads[platformHash]!!
            val cycleLength = i - previousRound

            val loadsInCycle = previousLoads
                .filter { (_, entry) -> entry.first >= previousRound }
                .map { (_, entry) -> entry.first % cycleLength to entry.second }

            println("Part 2 result: ${loadsInCycle.find { (round, _) -> round == 1000000000 % cycleLength }!!.second}")

            return
        }

        previousLoads[platformHash] = i to rotatePlatform(platform, 90)
            .mapIndexed { index, line -> (input.size - index) * line.count { it == 'O' } }.sum()
    }

    val result = rotatePlatform(platform, 90)
        .mapIndexed { index, line -> (input.size - index) * line.count { it == 'O' } }
        .sum()

    println("Part 2 result: $result")
}

fun part1(input: List<List<Char>>) {
    val result = input
        .let { rotatePlatform(it, 270) }
        .let(::tiltPlatform)
        .let { rotatePlatform(it, 90) }
        .mapIndexed { index, line -> (input.size - index) * line.count { it == 'O' } }
        .sum()

    println("Part 1 result: $result")
}

// Tilts the platform to the west
fun tiltPlatform(platform: List<List<Char>>): List<List<Char>> {
    return platform.map(::tiltLine)
}

fun tiltLine(line: List<Char>): List<Char> {
    tailrec fun tiltLineRec(remainingLine: List<Char>, tiltedLine: List<Char> = emptyList()): List<Char> {
        if (remainingLine.isEmpty() || remainingLine.all { it == '#' }) return tiltedLine

        val firstPart = remainingLine.takeWhile { it != '#' }
        val secondPart = remainingLine.subList(firstPart.size, remainingLine.size).takeWhile { it == '#' }

        val roundBoulders = firstPart.count { it == 'O' }
        val emptySpots = firstPart.size - roundBoulders

        val roundBouldersList = List(roundBoulders) { 'O' }
        val emptyList = List(emptySpots) { '.' }

        val res = tiltedLine + roundBouldersList + emptyList + secondPart

        return tiltLineRec(remainingLine.subList(firstPart.size + secondPart.size, remainingLine.size), res)
    }

    return tiltLineRec(line)
}

fun rotatePlatform(platform: List<List<Char>>, degrees: Int): List<List<Char>> {
    val simpleDegrees = degrees % 360
    val sideLength = platform.size

    if (simpleDegrees == 90) {
        return platform.mapIndexed { y, line ->
            List(line.size) { x ->
                platform[sideLength - 1 - x][y]
            }
        }
    }

    if (simpleDegrees == 180) {
        return platform.mapIndexed { y, line ->
            List(line.size) { x ->
                platform[sideLength - 1 - y][sideLength - 1 - x]
            }
        }
    }

    if (simpleDegrees == 270) {
        return platform.mapIndexed { y, line ->
            List(line.size) { x ->
                platform[x][sideLength - 1 - y]
            }
        }
    }

    return platform
}

private fun List<String>.parseInput() = map { line -> line.toList() }
