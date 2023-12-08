module fsharp.Day1.Part1

open System

let rec calculateCalibration (line: string) =
    let digits =
        line.ToCharArray()
        |> Array.filter Char.IsNumber
        |> Array.map (fun c -> c.ToString())

    let first = digits[0]
    let last = digits[digits.Length - 1]

    Int32.Parse $"{first}{last}"

let getPart1Result input = input |> Array.map calculateCalibration |> Array.sum

let part1 (input: array<string>) =
    let result = getPart1Result input

    printfn $"Part 1 result: {result}"
