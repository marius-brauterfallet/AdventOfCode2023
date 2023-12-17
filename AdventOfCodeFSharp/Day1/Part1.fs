module AdventOfCodeFSharp.Day1.Part1

open System

let getCalibrationValue (line: string): int =
    let rec getCalibrationValueRec (remainingLine: string) (firstDigit: char) (lastDigit: char): string =
        if remainingLine.Length = 0 then firstDigit.ToString() + lastDigit.ToString()
        
        else if (remainingLine[0] |> Char.IsDigit)
        then
            let first = if firstDigit |> Char.IsDigit then firstDigit else remainingLine[0]
            let last = remainingLine[0]
            getCalibrationValueRec (remainingLine.Substring 1) first last
        else getCalibrationValueRec (remainingLine.Substring 1) firstDigit lastDigit
        
    let resultString = getCalibrationValueRec line '?' '?'
    let result = resultString |> Int32.Parse
    result
    
let part1 (input: string array) =
    let result =
        input
        |> Array.map getCalibrationValue
        |> Array.sum
        
    printfn $"Part 1 result: {result}"