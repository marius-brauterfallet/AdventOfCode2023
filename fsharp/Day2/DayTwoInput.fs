namespace fsharp.Day2

type CubeSet = { color: string; amount: int }

type Line =
    { gameId: int
      cubeSets: array<CubeSet> }
