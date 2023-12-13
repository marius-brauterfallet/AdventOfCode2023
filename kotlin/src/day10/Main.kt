package day10

import getInput
import kotlin.math.max
import kotlin.math.min

/*
| is a vertical pipe connecting north and south.
- is a horizontal pipe connecting east and west.
L is a 90-degree bend connecting north and east.
J is a 90-degree bend connecting north and west.
7 is a 90-degree bend connecting south and west.
F is a 90-degree bend connecting south and east.
. is ground; there is no pipe in this tile.
S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
*/

fun main() {
    val input = getInput("day10", "test.txt")

    val day10 = Day10(input)

    day10.part1()
    day10.part2()
}


class Day10(val input: List<String>) {

    private val startCoords: Pair<Int, Int>

    init {
        val startY = input.indexOfFirst { it.contains('S') }
        val startX = input[startY].indexOfFirst { it == 'S' }
        startCoords = startX to startY
    }

    fun part1() {
        val connections = getConnections(startCoords)

        val result = solvePart1(connections.first(), startCoords, 1) / 2

        println("Part 1 result: $result")
    }

    fun part2() {
        
    }

    private tailrec fun solvePart1(coords: Pair<Int, Int>, previous: Pair<Int, Int>, length: Int): Int {
        if (coords == startCoords) return length

        val next = getConnections(coords)
            .filterNot { it == previous }
            .single()

        return solvePart1(next, coords, length + 1)
    }

    private fun neighborCoords(coords: Pair<Int, Int>): List<Pair<Int, Int>> {
        return listOf(1 to 0, -1 to 0, 0 to 1, 0 to -1)
            .map { coords.first + it.first to coords.second + it.second }
            .filter { it.first >= 0 && it.second >= 0 && it.first < input.first().length && it.second < input.size }
    }

    private fun getConnections(coords: Pair<Int, Int>): List<Pair<Int, Int>> {
        return neighborCoords(coords).filter {
            connectsToCoords(coords, it).also(::println)
        }
    }

    // This assumes the neighbor is a neighbor of coords
    private fun connectsToCoords(coords: Pair<Int, Int>, neighbor: Pair<Int, Int>): Boolean {
        val xDiff = coords.first - neighbor.first // If negative, neighbor is to the right
        val yDiff = coords.second - neighbor.second // If negative, neighbor is underneath

        val coordValue = input[coords.second][coords.first]
        val neighborValue = input[neighbor.second][neighbor.first]

        val direction = when {
            xDiff == -1 && yDiff == 0 -> "right"
            xDiff == 1 && yDiff == 0 -> "left"
            yDiff == -1 && xDiff == 0 -> "down"
            yDiff == 1 && xDiff == 0 -> "up"
            else -> "invalid state"
        }

        val connectsRight = arrayOf('L', 'F', '-', 'S')
        val connectsLeft = arrayOf('7', 'J', '-', 'S')
        val connectsUp = arrayOf('|', 'L', 'J', 'S')
        val connectsDown = arrayOf('|', '7', 'F', 'S')

        // neighbor is to the left
        if (direction == "left" && neighborValue in connectsRight && coordValue in connectsLeft) {
            return true
        }

        // neighbor is to the right
        if (direction == "right" && neighborValue in connectsLeft && coordValue in connectsRight) {
            return true
        }

        // neighbor is above
        if (direction == "up" && neighborValue in connectsDown && coordValue in connectsUp) {
            return true
        }

        // neighbor is underneath
        if (direction == "down" && neighborValue in connectsUp && coordValue in connectsDown) {
            return true
        }

        return false
    }

    fun printAroundCoords(coords: Pair<Int, Int>, range: Int = 3) {
        val lowX = max(0, coords.first - range)
        val lowY = max(0, coords.second - range)
        val highX = min(input.first().length - 1, coords.first + range)
        val highY = min(input.size - 1, coords.second + range)

        val output = input
            .filterIndexed { index, s -> index in lowY .. highY }
            .map {
                it.substring(lowX, highX).toCharArray()
            }

        output.forEach(::println)
    }

}

