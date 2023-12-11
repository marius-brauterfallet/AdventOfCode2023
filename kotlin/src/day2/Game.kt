package day2

data class Game(
    val id: Int,
    val rounds: List<Round>
)

data class Round(
    val cubeSets: List<Pair<String, Int>>
)
