# Assignment 1: Simple Lexer

## Problem Description

Your task is to complete the lexer implementation. Our lexer should recognize the following regular expression:

- `IF | ID | INT`
    - `IF` is defined as `'if'`
    - `ID` is defined as `[A-Za-z_] [A-Za-z0-9_]*`
    - `INT` is defined as `0 | [1-9] [0-9]*`

### Tasks

1. Draw an NFA to recognize the regular expression `IF | ID | INT`
2. Convert the NFA to a DFA.
3. Complete the `transition` method in [src/main/java/simplelexer/SimpleLexer.java](src/main/java/simplelexer/SimpleLexer.java) based on the DFA.
4. To test your solution, run JUnit tests in [src/test/java/simplelexer/SimpleLexerTest.java](src/test/java/simplelexer/SimpleLexerTest.java). You can execute them by typing the following command from the repository root:


```sh
sbt simpleLexer/test
```

### What to Submit

1. The NFA for the regular expression `IF | ID | INT`
2. The DFA converted from the NFA
3. A zip file of your completed project. Regarding how to create the zip file, follow [this instruction](https://www.gitkraken.com/learn/git/github-download#how-to-download-a-github-repository).

### How to Submit

Submit the following two files via BlackBoard.

1. A pdf file containing the NFA and DFA for the regular expression `IF | ID | INT`
2. A zip file of your completed project

### Submission Deadline

**Due: September 15, 2026; 3:00pm KST**


### Evaluation Methods

1. If your implementation passes all 10 JUnit tests and does not simply hard-code the expected results for each test case, you will receive full points (10 points).
2. During the class on September 16, 2026, some students may be asked to demonstrate their solutions. Those who successfully demonstrate their solutions will receive 5 additional points.

