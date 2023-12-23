package day15

import getInput

fun main() {
    val input = getInput("day15", "input.txt").first().split(",")

    part1(input)
}

fun part1(input: List<String>) {
    val result = input.sumOf { step ->
        hash(step)
    }

    println("Part 1 result: $result")
}

fun hash(string: String): Int {
    tailrec fun hashRec(index: Int = 0, res: Int = 0): Int {
        if (index == string.length) return res

        val newRes = ((res + string[index].code) * 17) % 256

        return hashRec(index + 1, newRes)
    }

    return hashRec()
}