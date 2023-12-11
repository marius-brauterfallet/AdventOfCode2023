package day8

import getInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = getInput("day8", "input.txt").parseInput()

    part1(input)
    part2(input)
}

fun part2(input: Pair<Sequence<Char>, Map<String, Pair<String, String>>>) {
    val (route, nodes) = input

    val startingNodes = nodes
        .filter { node -> node.key.last() == 'A' }
        .map { it.key }

    val result = startingNodes.map { node ->
        var currentNode = node

        var length = 0L

        run {
            route.forEachIndexed { index, dir ->
                currentNode = if (dir == 'L') nodes[currentNode]!!.first else nodes[currentNode]!!.second

                if (currentNode.last() == 'Z') {
                    length = index + 1L
                    return@run
                }
            }
        }
        length
    }.let { leastCommonMultiple(it) }

    println("Part 2 result: $result")
}

fun leastCommonMultiple(numbers: List<Long>): Long {
    return numbers.reduce { acc, l ->
        leastCommonMultiple(acc, l)
    }
}

fun leastCommonMultiple(num1: Long, num2: Long): Long {
    return num1 * num2 / greatestCommonDivisor(num1, num2)
}

tailrec fun greatestCommonDivisor(num1: Long, num2: Long): Long {
    if (num1 == num2) return num1

    val high = max(num1, num2)
    val low = min(num1, num2)

    return greatestCommonDivisor(high - low, low)
}

fun part1(input: Pair<Sequence<Char>, Map<String, Pair<String, String>>>) {
    val (route, nodes) = input

    var currentNode = "AAA"

    route.forEachIndexed { index, c ->
        currentNode = when (c) {
            'L' -> nodes[currentNode]!!.first
            'R' -> nodes[currentNode]!!.second
            else -> return
        }

        if (currentNode == "ZZZ") {
            println("Part 1 result: ${index + 1}")
            return
        }
    }

}

fun List<String>.parseInput(): Pair<Sequence<Char>, Map<String, Pair<String, String>>> {
    val route = first()
    val rest = this.subList(2, size)

    val nodes = buildMap {
        rest.forEach { line ->

            val (node, leftAndRight) = line.split('=').map { it.trim() }

            val (left, right) = leftAndRight
                .replace('(', ' ')
                .replace(')', ' ')
                .split(',')
                .map { it.trim() }

            put(node, left to right)
        }
    }

    val routeSequence = sequence {
        while (true) {
            route.forEach { c ->
                yield(c)
            }
        }
    }

    return routeSequence to nodes
}