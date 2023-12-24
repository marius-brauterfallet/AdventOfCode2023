package day16

import getInput

val horizontalDirections = listOf('E', 'W')
val verticalDirections = listOf('N', 'S')
val directionValues = mapOf('N' to (0 to -1), 'S' to (0 to 1), 'W' to (-1 to 0), 'E' to (1 to 0))

fun main() {
    val input = getInput("day16", "input.txt").map { it.toList() }

    part1(input)
    part2(input)
}

fun part1(input: List<List<Char>>) {
    val result = findEnergizedSquares(input, 0 to 0, 'E')

    println("Part 1 result: $result")
}

fun part2(input: List<List<Char>>) {
    val result = List(input.size) {
        val east = findEnergizedSquares(input, 0 to it, 'E')
        val south = findEnergizedSquares(input, it to 0, 'S')
        val west = findEnergizedSquares(input, input.size - 1 to it, 'W')
        val north = findEnergizedSquares(input, it to input.size - 1, 'N')

        listOf(east, south, west, north).max()
    }.max()

    println("Part 2 result: $result")
}

fun findEnergizedSquares(input: List<List<Char>>, startSquare: Pair<Int, Int>, startDirection: Char): Int {
    val energizedSquares = mutableSetOf<Pair<Int, Int>>()
    val examinedSquares = mutableSetOf<Pair<Pair<Int, Int>, Char>>()

    tailrec fun part1rec(square: Pair<Int, Int> = startSquare, direction: Char = startDirection) {
        if (square to direction in examinedSquares
            || square.first < 0
            || square.second < 0
            || square.first >= input.first().size
            || square.second >= input.size
        ) {
            return
        }

        energizedSquares.add(square)
        examinedSquares.add(square to direction)

        val squareValue = input[square.second][square.first]

        if (squareValue == '.'
            || (squareValue == '-' && direction in horizontalDirections)
            || (squareValue == '|' && direction in verticalDirections)
        ) {
            val nextSquare = square.first + directionValues[direction]!!.first to square.second + directionValues[direction]!!.second

            return part1rec(nextSquare, direction)
        }

        if (squareValue == '-') {
            part1rec(square.first - 1 to square.second, 'W')
            return part1rec(square.first + 1 to square.second, 'E')
        }

        if (squareValue == '|') {
            part1rec(square.first to square.second - 1, 'N')
            return part1rec(square.first to square.second + 1, 'S')
        }

        val nextDirection = when (squareValue) {
            '/' -> {
                when(direction) {
                    'N' -> 'E'
                    'E' -> 'N'
                    'S' -> 'W'
                    'W' -> 'S'
                    else -> direction
                }
            }
            '\\' -> {
                when(direction) {
                    'N' -> 'W'
                    'E' -> 'S'
                    'S' -> 'E'
                    'W' -> 'N'
                    else -> direction
                }
            }
            else -> direction
        }

        val directionValue = directionValues[nextDirection]!!
        val nextSquare = square.first + directionValue.first to square.second + directionValue.second

        return part1rec(nextSquare, nextDirection)
    }

    part1rec()

    return energizedSquares.count()
}
