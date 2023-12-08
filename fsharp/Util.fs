module fsharp.Util

open System
open System.IO

let getInput (day: string) (filename: string) =
    let stream = new StreamReader $"{Environment.CurrentDirectory}/../../../../input/{day}/{filename}"
    stream.ReadToEnd().Trim().Split("\n")
    |> Array.map (fun s -> s.Trim())