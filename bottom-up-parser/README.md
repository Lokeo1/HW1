# Assignment 4: Bottom-Up Parsers

This module builds a bottom-up parser using one of three configurable algorithms:

- `SLR(1)`
- `LR(1)`
- `LALR(1)`

It reads exactly the JSON grammar format used by `top-down-parser` and tokenizes source text with
`simple-lexer-subset`. Terminals `IF`, `ID`, and `INT` refer to lexer token kinds; `(`, `)`, `+`,
`*`, and `=` refer to punctuation. `$` is reserved as the end marker. The following example shows [valid-grammar.json](./src/test/resources/bottomupparser/valid-grammar.json).

```json
{
  "start": "E",
  "nonterminals": ["E", "T", "F"],
  "terminals": ["INT", "+", "*", "(", ")"],
  "productions": {
    "E": [["E", "+", "T"], ["T"]],
    "T": [["T", "*", "F"], ["F"]],
    "F": [["INT"], ["(", "E", ")"]]
  }
}
```

### Tasks

1. Complete [src/main/java/bottomupparser/BottomUpParser.java](src/main/java/bottomupparser/BottomUpParser.java). 
2. To test your solution, run JUnit tests in [src/test/java/bottomupparser/BottomUpParserTest.java](src/test/java/bottomupparser/BottomUpParserTest.java). You can execute them by typing the following command from the repository root:


```sh
sbt bottomUpParser/test
```

You can also print the parsing table for a given grammar by entering the following command from the repository root:

```sh
sbt "bottomUpParser/run LR(1) ./bottom-up-parser/src/test/resources/bottomupparser/valid-grammar.json"
```

To print the parsing table for SLR(1) or LALR(1), replace `LR(1)` with `SLR(1)` or `LALR(1)`.

## What to Submit

1. A document containing the following:
   1. The SLR(1), LR(1), and LALR(1) parsing tables for [valid-grammar.json](./src/test/resources/bottomupparser/valid-grammar.json).
   2. The explanation for how each table is constructed.

2. A zip file of your completed project. Regarding how to create the zip file, follow [this instruction](https://www.gitkraken.com/learn/git/github-download#how-to-download-a-github-repository).

### How to Submit

Submit the following files via BlackBoard.

1. A document file
2. A zip file of your completed project

### Submission Deadline

**Due: October 20, 2026; 3:00pm KST**
