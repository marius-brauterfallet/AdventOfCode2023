package day7

import getInput

fun main() {
    val input = getInput("day7", "input.txt").map { line ->
        line.split(" ").let { it[0].parseHand() to it[1].toInt() }
    }

    part1(input)
    part2(input)
}

fun part1(input: List<Pair<List<Int>, Int>>) {
    val result = solveDay7(input, HandComparator(::getHandTypePart1))
    println("Part 1 result $result")
}

fun part2(input: List<Pair<List<Int>, Int>>) {
    val result = solveDay7(input.transformToPart2Input(), HandComparator(::getHandTypePart2))
    println("Part 2 result $result")
}

fun getHandTypePart2(hand: List<Int>): Int {
    return if (hand.any { it == 1 } && !hand.all { it == 1 }) {
        val mostCommonValue = hand
            .filter { it != 1 }
            .groupBy { it }
            .map { it.key to it.value.size }
            .maxBy { it.second }.first

        hand
            .map { if (it == 1) mostCommonValue else it }
            .let { getHandTypePart1(it) }
    } else {
        getHandTypePart1(hand)
    }
}


fun List<Pair<List<Int>, Int>>.transformToPart2Input() = map { (hand, bid) ->
    hand.map { if (it == 11) 1 else it } to bid
}

fun solveDay7(input: List<Pair<List<Int>, Int>>, handComparator: HandComparator): Int {
    val result = input.sortedWith(handComparator).mapIndexed { index, handAndBid ->
        val bid = handAndBid.second
        (index + 1) * bid
    }.sum()

    return result
}

class HandComparator(val getHandType: (List<Int>) -> Int) : Comparator<Pair<List<Int>, Int>> {
    override fun compare(o1: Pair<List<Int>, Int>?, o2: Pair<List<Int>, Int>?): Int {
        if (o1 == null || o2 == null) return 0

        val o1Type = getHandType(o1.first)
        val o2Type = getHandType(o2.first)

        if (o1Type != o2Type) {
            return o1Type - o2Type
        }

        o1.first.zip(o2.first).forEach {
            if (it.first != it.second) return it.first - it.second
        }

        return 0
    }
}

private fun getHandTypePart1(hand: List<Int>): Int {
    val uniqueCards = hand.toSet()
    val cardCounts = uniqueCards
        .map { card -> card to hand.count { it == card } }
        .sortedByDescending { it.second }

    return when (uniqueCards.size) {
        1 -> 6
        2 -> if (cardCounts.first().second == 4) 5 else 4
        3 -> if (cardCounts.first().second == 3) 3 else 2
        4 -> 1
        else -> 0
    }
}

fun String.parseHand(): List<Int> = map { card ->
    if (card.isDigit()) card.digitToInt() else when (card) {
        'T' -> 10
        'J' -> 11
        'Q' -> 12
        'K' -> 13
        'A' -> 14
        else -> 0
    }
}