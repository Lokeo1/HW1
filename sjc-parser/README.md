# Assignment 5: Scanner and Parser Generation Using ANTLR

In this module, you will generate a lexer and parser using ANTLR. The token definitions and grammar rules are specified in [src/main/java/sjc/parser/StaticJava.g4](src/main/java/sjc/parser/StaticJava.g4).

### Tasks

1. Generate a lexer and parser using the following command and examine the generated files.

```sh
sbt "sjcParser/runMain org.antlr.v4.Tool \
   -Dlanguage=Java -visitor -no-listener \
   -Xexact-output-dir -o sjc-parser/src/main/java/sjc/parser \
   sjc-parser/src/main/java/sjc/parser/StaticJava.g4"
```

2. Run JUnit tests in [src/test/java/sjc/test/SjcParserTest.java](src/test/java/sjc/test/SjcParserTest.java) and check that all 198 tests pass. You can execute them by typing the following command from the repository root:


```sh
sbt sjcParser/test
```

## What to Submit

* A zip file of your completed project. Regarding how to create the zip file, follow [this instruction](https://www.gitkraken.com/learn/git/github-download#how-to-download-a-github-repository).

### How to Submit

Submit the following file via BlackBoard.

* A zip file of your completed project

### Submission Deadline

**Due: October 29, 2026; 3:00pm KST**
