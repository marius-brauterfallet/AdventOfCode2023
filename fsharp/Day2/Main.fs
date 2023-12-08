module fsharp.Day2.Main

open System
open System.Text.RegularExpressions
open fsharp.Day2.Part1
open fsharp.Day2.Part2
open fsharp.Util

let rec parseLine (line: string) =
    let gamePattern = "Game (\d+): ([\w ,;]+)"
    let gameMatch = Regex.Match(line, gamePattern)
    let gameId = gameMatch.Groups[1].Value |> Int32.Parse
    let rest = gameMatch.Groups[2].Value

    let cubeSetsPattern = "([\w ]+)[,; ]*"
    let cubeSetPattern = "([\d]+) ([\w]+)"
    let matches = Regex.Matches(rest, cubeSetsPattern)

    let cubeSets =
        matches
        |> Seq.map (fun cubeSet ->
            let cubeSetMatch = Regex.Match(cubeSet.Groups[1].Value, cubeSetPattern)
            let amount = cubeSetMatch.Groups[1].Value |> Int32.Parse
            let color = cubeSetMatch.Groups[2].Value
            { color = color; amount = amount })
        |> Seq.toArray

    { gameId = gameId; cubeSets = cubeSets }

let main: unit =
    let input = getInput "day2" "input.txt" |> Array.map parseLine

    part1 input
    part2 input
