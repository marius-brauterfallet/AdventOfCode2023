package day4

import getInput
import java.util.regex.Pattern
import kotlin.math.pow

fun main() {
    val input = getInput("day4", "input.txt").toCards()

    part1(input)
    part2(input)
}

private fun part2(input: List<Card>) {
    val wonCards = IntArray(input.size) { 1 }

    input.forEach { card ->
        val winningNumbers = card.myNumbers.intersect(card.winningNumbers)

        winningNumbers.forEachIndexed { index, _ ->
            wonCards[card.gameNumber + index] += wonCards[card.gameNumber - 1]
        }
    }

    val result = wonCards.sum()

    println("Part 2 result: $result")
}

private fun part1(input: List<Card>) {
    val result = input.sumOf { card ->
        val myWinningNumbers = card.myNumbers.intersect(card.winningNumbers)

        if (myWinningNumbers.any()) {
            2.0.pow(myWinningNumbers.count() - 1).toInt()
        } else 0
    }

    println("Part 1 result: $result")
}

private fun List<String>.toCards(): List<Card> {
    val cardNumberPattern = Pattern.compile("Card +(\\d+): ([\\d |]+)")

    return mapNotNull { line ->
        val cardNumberMatcher = cardNumberPattern.matcher(line)
        if (cardNumberMatcher.find()) {
            val cardNumber = cardNumberMatcher.group(1).toInt()
            val (winning, my) = parseNumbers(cardNumberMatcher.group(2))

            Card(cardNumber, winning, my)
        } else null
    }
}

private fun parseNumbers(numbers: String): Pair<Set<Int>, Set<Int>> {
    val (winningString, myString) = numbers.split("|").map { it.trim() }

    val numberPattern = Pattern.compile("(\\d+)")
    val winningMatcher = numberPattern.matcher(winningString)
    val myMatcher = numberPattern.matcher(myString)

    val winning = buildSet {
        while (winningMatcher.find()) {
            add(winningMatcher.group(1).toInt())
        }
    }

    val myNumbers = buildSet {
        while (myMatcher.find()) {
            add(myMatcher.group(1).toInt())
        }
    }

    return winning to myNumbers
}