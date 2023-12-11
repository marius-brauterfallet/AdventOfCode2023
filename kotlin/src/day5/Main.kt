package day5

import getInputAsSingleString
import java.util.regex.Pattern

fun main() {
    val (seeds, mappings) = getInputAsSingleString("day5", "test.txt").parseInput()

    part1(seeds, mappings)
    part2(seeds, mappings)
}

fun part2(seeds: List<Long>, mappings: List<Pair<Pair<String, String>, List<Triple<Long, Long, Long>>>>) {
    val allSeedRanges = seeds.toPart2SeedRanges()

    var result = -1L

    for (n in 0..Long.MAX_VALUE) {
        if (checkDestination(n, mappings, allSeedRanges)) {
            result = n
            break
        }
    }

    println("Part 2 result: $result")
}

fun checkDestination(
    n: Long,
    mappings: List<Pair<Pair<String, String>, List<Triple<Long, Long, Long>>>>,
    allSeedRanges: List<Pair<Long, Long>>,
    type: String = "location"
): Boolean {
    if (type == "seed") {
        return allSeedRanges.any { (start, end) -> n in start..<end }
    }

    val (names, numbers) = mappings.find { (names, _) -> names.second == type }!!

    val previousValue = numbers
        .find { (_, destinationStart, rangeLength) -> n >= destinationStart && n < destinationStart + rangeLength }
        ?.let { (sourceStart, destinationStart, _) ->
            sourceStart + (n - destinationStart)
        } ?: n

    return checkDestination(previousValue, mappings, allSeedRanges, names.first)
}

private fun List<Long>.toPart2SeedRanges(): List<Pair<Long, Long>> {
    return mapIndexedNotNull { index, _ ->
        if (index % 2 == 1) null
        else {
            val start = this[index]
            val end = start + this[index + 1]
            start to end
        }
    }
}

fun part1(seeds: List<Long>, mappings: List<Pair<Pair<String, String>, List<Triple<Long, Long, Long>>>>) {
    val result = solvePart1(seeds, mappings)

    println("Part 1 result $result")
}

fun solvePart1(seeds: List<Long>, mappings: List<Pair<Pair<String, String>, List<Triple<Long, Long, Long>>>>): Long {
    return findNext("seed", seeds, mappings).min()
}

fun findNext(
    sourceName: String,
    values: List<Long>,
    mappings: List<Pair<Pair<String, String>, List<Triple<Long, Long, Long>>>>
): List<Long> {
    val nextMap = mappings.find { (names, _) -> names.first == sourceName } ?: return values

    val (names, numbers) = nextMap
    val destinationName = names.second

    val newValues = values.map { number ->
        val triple = numbers.find { (sourceStart, _, rangeLength) ->
            number >= sourceStart && number < (sourceStart + rangeLength)
        }

        if (triple == null) {
            number
        } else {
            val (sourceStart, destinationStart, _) = triple
            destinationStart + (number - sourceStart)
        }
    }

    return findNext(destinationName, newValues, mappings)
}

private fun String.parseInput(): Pair<List<Long>, List<Pair<Pair<String, String>, List<Triple<Long, Long, Long>>>>> {
    val seedsPattern = Pattern.compile("seeds: ([\\d ]+)(.*)")
    val seedsMatcher = seedsPattern.matcher(this.replace('\n', ' '))

    seedsMatcher.find()

    val seeds = seedsMatcher.group(1).trim().split(" ").map { it.toLong() }
    val rest = seedsMatcher.group(2)

    val mapPattern = Pattern.compile("([\\w- ]+):([\\d ]+)")
    val mapMatcher = mapPattern.matcher(rest)

    val mappingsList = buildList {
        while (mapMatcher.find()) {
            val names = parseMapName(mapMatcher.group(1))
            val mappings = parseMapNumbers(mapMatcher.group(2))

            add(names to mappings)
        }
    }

    return seeds to mappingsList
}

fun parseMapNumbers(numbersGroup: String): List<Triple<Long, Long, Long>> {
    val numberGroupPattern = Pattern.compile("(\\d+) (\\d+) (\\d+)")
    val numberGroupMatcher = numberGroupPattern.matcher(numbersGroup)

    return buildList {
        while (numberGroupMatcher.find()) {
            val destinationStart = numberGroupMatcher.group(1).toLong()
            val sourceStart = numberGroupMatcher.group(2).toLong()
            val rangeLength = numberGroupMatcher.group(3).toLong()

            add(Triple(sourceStart, destinationStart, rangeLength))
        }
    }
}

private fun parseMapName(nameGroup: String): Pair<String, String> {
    val namesPattern = Pattern.compile("(\\w+)-to-(\\w+)")
    val namesMatcher = namesPattern.matcher(nameGroup)

    namesMatcher.find()

    return namesMatcher.group(1) to namesMatcher.group(2)
}