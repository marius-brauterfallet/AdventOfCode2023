package day15

import getInput
import java.util.regex.Pattern

fun main() {
    val input = getInput("day15", "input.txt").first().split(",")

    part1(input)
    part2(input)
}

fun part2(input: List<String>) {
    val hashes = mutableMapOf<String, Int>()
    val boxes = Array(256) { mutableListOf<Pair<String, Int>>() }

    val pattern = Pattern.compile("(.*)[=-](.*)")

    input.forEach { step ->
        val matcher = pattern.matcher(step)

        matcher.find()

        val label = matcher.group(1)
        val focalLength = matcher.group(2).toIntOrNull()
        val hash = hashes.getOrElse(label) {
            val hash = hash(label)
            hashes[label] = hash
            hash
        }

        if (focalLength != null) {
            if (boxes[hash].any { (existingLabel, _) -> existingLabel == label }) {
                val index = boxes[hash].indexOfFirst { it.first == label }
                boxes[hash][index] = label to focalLength
            } else {
                boxes[hash].add(label to focalLength)
            }
        } else {
            boxes[hash].removeAll { it.first == label }
        }

//        boxes
//            .mapIndexed { index, list -> index to list }
//            .filter { (_, list) -> list.any() }
//            .forEach { println(it) }

//        println()
    }

    val result = boxes.mapIndexed { boxIndex, lenses ->
        val boxValue = boxIndex + 1

        lenses.mapIndexed { lensIndex, lens ->
            val lenseValue = lensIndex + 1
            val focalLength = lens.second

            lenseValue * focalLength * boxValue
        }.sum()
    }.sum()

    println("Part 2 result: $result")
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