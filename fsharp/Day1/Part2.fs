module fsharp.Day1.Part2

open System

let getNumberChar (s: string) =
    let numberStrings =
        [ ("one", 1)
          ("two", 2)
          ("three", 3)
          ("four", 4)
          ("five", 5)
          ("six", 6)
          ("seven", 7)
          ("eight", 8)
          ("nine", 9) ]

    let numberTuple =
        numberStrings
        |> List.tryFind (fun numberTuple ->
            let numberString = fst numberTuple
            let sub = s.Substring(0, min numberString.Length s.Length)
            sub = numberString)

    if numberTuple.IsSome then
        (snd numberTuple.Value).ToString()[0]
    else
        s[0]

let convertNumberStrings (line: string) =
    line
    |> String.mapi (fun i c ->
        let sub = line.Substring(i)
        getNumberChar sub)

let part2 (input: array<string>) : unit =
    let result =
        input
        |> Array.map convertNumberStrings
        |> Part1.getPart1Result
        
    printfn $"Part 2 result: {result}"
