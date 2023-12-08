module fsharp.Day2.Part2

let part2 (input: array<Line>) =
    let result =
        input
        |> Array.map (fun line ->
            line.cubeSets
            |> Array.groupBy (fun cubeSet -> cubeSet.color)
            |> Array.map (fun group ->
                (snd group)
                |> Array.maxBy (fun cubeSet -> cubeSet.amount)
                |> (fun cubeSet -> cubeSet.amount))
            |> Array.reduce (fun acc n -> acc * n))
        |> Array.sum

    printfn $"Part 2 result: {result}"
