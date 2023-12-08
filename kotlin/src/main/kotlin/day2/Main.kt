package day2

import getInput
import java.util.regex.Pattern

fun main() {
    val input = getInput("day2", "input.txt")

    val games = parseInput(input)
    val maxCubes = listOf("red" to 12, "green" to 13, "blue" to 14)

    part1(games, maxCubes)
    part2(games)
}

fun part1(games: List<Game>, maxCubes: List<Pair<String, Int>>) {
    val result = games.filter { game ->
        game.rounds.all { round ->
            round.cubeSets.none { cubeSet ->
                cubeSet.second > maxCubes.find { it.first == cubeSet.first }!!.second
            }
        }
    }.sumOf { game -> game.id }

    println("Part 1 result: $result")
}

fun part2(games: List<Game>) {
    val result = games.map { game ->
        game.rounds
            .flatMap { it.cubeSets }
            .groupBy { it.first }
            .map { entry -> entry.value.maxBy { it.second }.second }
            .reduce { acc, n -> n * acc }
    }.sum()

    println("Part 2 result: $result")
}

fun parseRound(input: String): Round {
    val cubeSetPattern = Pattern.compile("(\\d+) (\\w+)")

    val matcher = cubeSetPattern.matcher(input)

    val cubeSets = buildList<Pair<String, Int>> {
        while (matcher.find()) {
            add(matcher.group(2) to matcher.group(1).toInt())
        }
    }

    return Round(cubeSets)
}

fun parseGame(id: Int, input: String): Game {
    val roundPattern = Pattern.compile("([\\w ,]+)")
    val matcher = roundPattern.matcher(input)

    val rounds = buildList {
        while (matcher.find()) {
            add(parseRound(matcher.group(1)))
        }
    }

    return Game(id, rounds)
}

fun parseInput(input: List<String>): List<Game> {
    val gamePattern = Pattern.compile("Game (\\d+): ([\\w ,;]+)")

    return input.mapNotNull { line ->
        val matcher = gamePattern.matcher(line)

        if (matcher.find()) {
            parseGame(matcher.group(1).toInt(), matcher.group(2))
        } else null
    }
}