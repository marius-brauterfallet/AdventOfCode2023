module fsharp.Day1.Main

open fsharp.Util
open fsharp.Day1.Part1
open fsharp.Day1.Part2

let main: unit =
    let input = getInput "day1" "input.txt"

    part1 input
    part2 input
