package day10

val connectsRight = arrayOf('S', '-', 'L', 'F')
val connectsLeft = arrayOf('S', '-', '7', 'J')
val connectsUp = arrayOf('S', '|', 'L', 'J')
val connectsDown = arrayOf('S', '|', 'F', '7')

val corners = arrayOf('F', '7', 'J', 'L')

fun part2(input: List<String>) {
    val result = input
        .toTwoDimensionalArray()
        .let { map ->
            val path = findPath(map).toSet()

            map.mapIndexed { y, line ->
                line.mapIndexed { x, c ->
                    if (c == 'S') {
                        getNewStartChar(map, x to y)
                    } else if (x to y in path) c else '.'
                }
            }
        }
        .map { checkLine(it) }
//        .also { map -> map.forEach { println(it.joinToString(" ")) } }
        .sumOf { it.count { c -> c == 'I' } }

    println("Part 2 result: $result")
}

fun checkLine(line: List<Char>): List<Char> {
    var isInside = false
    var previousCornerVerticalDirection: String? = null

    return line.map {
        if (isInside && it == '.') {
            return@map 'I'
        }

        if (it in corners) {
            if (previousCornerVerticalDirection != null) {
                // Checks if the corners connecting to the horizontal wall connects vertically in the same direction or not
                if ((previousCornerVerticalDirection == "up" && it in connectsDown) || (previousCornerVerticalDirection == "down" && it in connectsUp)) {
                    isInside = !isInside
                }

                previousCornerVerticalDirection = null
            } else {
                previousCornerVerticalDirection = if (it in connectsUp) { "up" } else { "down" }
            }
        }

        if (it == '|') {
            isInside = !isInside
        }

        it
    }
}

fun List<String>.toTwoDimensionalArray(): Array<Array<Char>> {
    return map { line ->
        line
            .map { it }
            .toTypedArray()
    }.toTypedArray()
}

fun findPath(map: Array<Array<Char>>): List<Pair<Int, Int>> {
    val startCoordinates = map.findStartCoordinates()

    tailrec fun findPathRec(
        coords: Pair<Int, Int>,
        previousCoords: Pair<Int, Int>,
        result: List<Pair<Int, Int>>
    ): List<Pair<Int, Int>> {
        if (coords == startCoordinates) return result

        val nextCoords = findConnections(map, coords).first { it != previousCoords }

        return findPathRec(nextCoords, coords, result + coords)
    }

    val connectsToStart = findConnections(map, startCoordinates)

    return findPathRec(connectsToStart.first(), startCoordinates, listOf(startCoordinates))
}

fun getNewStartChar(map: Array<Array<Char>>, startCoordinates: Pair<Int, Int>): Char {
    val connectsToStart = findConnections(map, startCoordinates)
    val (x, y) = startCoordinates

    return when {
        (listOf(x + 1 to y, x - 1 to y).containsAll(connectsToStart)) -> '-'
        (listOf(x + 1 to y, x to y + 1).containsAll(connectsToStart)) -> 'F'
        (listOf(x + 1 to y, x to y - 1).containsAll(connectsToStart)) -> 'L'
        (listOf(x to y + 1, x to y - 1).containsAll(connectsToStart)) -> '|'
        (listOf(x to y + 1, x - 1 to y).containsAll(connectsToStart)) -> '7'
        (listOf(x - 1 to y, x to y - 1).containsAll(connectsToStart)) -> 'J'
        else -> 'S'
    }
}

fun Array<Array<Char>>.findStartCoordinates(): Pair<Int, Int> {
    val y = indexOfFirst { it.contains('S') }
    val x = this[y].indexOfFirst { it == 'S' }

    return x to y
}

fun findConnections(map: Array<Array<Char>>, coords: Pair<Int, Int>): List<Pair<Int, Int>> {
    val (x, y) = coords

    val thisChar = map[y][x]

    return buildList {
        if (x > 0 && map[y][x - 1] in connectsRight && thisChar in connectsLeft) {
            add(x - 1 to y)
        }

        if (x + 1 < map.first().size && map[y][x + 1] in connectsLeft && thisChar in connectsRight) {
            add(x + 1 to y)
        }

        if (y > 0 && map[y - 1][x] in connectsDown && thisChar in connectsUp) {
            add(x to y - 1)
        }

        if (y + 1 < map.size && map[y + 1][x] in connectsUp && thisChar in connectsDown) {
            add(x to y + 1)
        }
    }
}