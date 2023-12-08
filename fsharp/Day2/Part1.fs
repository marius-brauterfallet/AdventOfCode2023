module fsharp.Day2.Part1

let part1 (input: array<Line>) =
    let maxAmounts =
        [ { color = "red"; amount = 12 }
          { color = "green"; amount = 13 }
          { color = "blue"; amount = 14 } ]

    let result =
        input
        |> Array.filter (fun line ->
            line.cubeSets
            |> Array.forall (fun cubeSet ->
                let maxAmount =
                    maxAmounts |> List.find (fun maxCubeSet -> maxCubeSet.color = cubeSet.color)

                cubeSet.amount <= maxAmount.amount))
        |> Array.sumBy (fun line -> line.gameId)

    printfn $"Part 1 result: {result}"
