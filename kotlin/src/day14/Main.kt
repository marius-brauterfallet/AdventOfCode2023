package day14

import getInput

fun main() {
    val input = getInput("day14", "input.txt").parseInput()

    part1(input)
}

fun part1(input: List<List<Char>>) {
    val result = input
        .let { rotatePlatform(it, 270) }
        .map { tiltLine(it) }
        .let { rotatePlatform(it, 90) }
        .mapIndexed { index, line -> (input.size - index) * line.count { it == 'O' } }
        .sum()

    println("Part 1 result: $result")
}

fun tiltLine(line: List<Char>): List<Char> {
    tailrec fun tiltLineRec(remainingLine: List<Char>, tiltedLine: List<Char> = emptyList()): List<Char> {
        if (remainingLine.isEmpty() || remainingLine.all { it == '#' }) return tiltedLine

        val firstPart = remainingLine.takeWhile { it != '#' }
        val secondPart = remainingLine.subList(firstPart.size, remainingLine.size).takeWhile { it == '#' }

        val roundBoulders = firstPart.count { it == 'O' }

        val res = tiltedLine + buildList {
            for (i in 0 until roundBoulders) {
                add('O')
            }

            for (i in roundBoulders until firstPart.size) {
                add('.')
            }

        } + secondPart

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
