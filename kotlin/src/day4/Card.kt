package day4

data class Card(
    val gameNumber: Int,
    val winningNumbers: Set<Int>,
    val myNumbers: Set<Int>
)
