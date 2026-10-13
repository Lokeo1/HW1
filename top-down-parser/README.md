# Assignment 3: LL(1) Top-Down Parser

This module constructs a complete LL(1) predictive parsing table from a JSON grammar, then uses that table to parse input tokenized by `simple-lexer-subset`.

## Grammar

A grammar is provided as a json file. The following example shows [valid-grammar.json](./src/test/resources/topdownparser/valid-grammar.json).

```json
{
  "start": "E",
  "nonterminals": ["E", "E'", "T", "T'", "F"],
  "terminals": ["INT", "+", "*", "(", ")"],
  "productions": {
    "E": [["T", "E'"]],
    "E'": [["+", "T", "E'"], []],
    "T": [["F", "T'"]],
    "T'": [["*", "F", "T'"], []],
    "F": [["INT"], ["(", "E", ")"]]
  }
}
```

The above json file represents the following grammar:

```text
E → T E'
E' → + T E' | ε
T → F T'
T' → * F T' | ε
F → INT | "(" E ")"
```

Terminals must be supplied by `simple-lexer-subset`: use `IF`, `ID`, and `INT` for word-like tokens, and use the literal symbols `(`, `)`, `+`, and `*` for punctuation. `$` is reserved for the end-of-input column.

### Tasks

1. Complete the `build` method in [src/main/java/topdownparser/Ll1ParsingTable.java](src/main/java/topdownparser/Ll1ParsingTable.java). 
2. To test your solution, run JUnit tests in [src/test/java/topdownparser/TopDownParserTest.java](src/test/java/topdownparser/TopDownParserTest.java). You can execute them by typing the following command from the repository root:


```sh
sbt topDownParser/test
```

You can also print the parsing table for a given grammar by entering the following command from the repository root:

```sh
sbt "topDownParser/run ./top-down-parser/src/test/resources/topdownparser/valid-grammar.json"
```

## What to Submit

1. A document containing the following:
   1. The parsing table for [valid-grammar.json](./src/test/resources/topdownparser/valid-grammar.json).
   2. The parsing table for [invalid-grammar.json](./src/test/resources/topdownparser/invalid-grammar.json).
   3. The explanation for why [invalid-grammar.json](./src/test/resources/topdownparser/invalid-grammar.json) is invalid.

2. A zip file of your completed project. Regarding how to create the zip file, follow [this instruction](https://www.gitkraken.com/learn/git/github-download#how-to-download-a-github-repository).

### How to Submit

Submit the following files via BlackBoard.

1. A document file
2. A zip file of your completed project

### Submission Deadline

**Due: October 13, 2026; 3:00pm KST**
