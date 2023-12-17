module AdventOfCodeFSharp.Common.GetInput

open System.IO

let getInput (day: string) (filename: string) =
    File.ReadAllLines($"../../../../Input/{day}/{filename}")