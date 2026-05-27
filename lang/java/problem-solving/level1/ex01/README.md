# ex01 - FirstWord

| | |
|---|---|
| **Assignment name** | FirstWord |
| **Expected files** | `FirstWord.java` |
| **Allowed methods** | `System.out.print`, `System.out.println` |

---

## Instructions

Write a program that takes a string and displays its first word, followed by a newline.

A word is a section of string delimited by spaces/tabs or by the start/end of the string.

If the number of parameters is not 1, or if there are no words, simply display a newline.

---

## Compilation & Usage

```bash
# Compile
javac FirstWord.java

# Run
java -cp . level1.ex01.FirstWord "FOR PONY"
```

---

## Examples

```bash
$> javac FirstWord.java
$> java -cp . level1.ex01.FirstWord "FOR PONY"
FOR

$> java -cp . level1.ex01.FirstWord "this        ...    is sparta, then again, maybe    not"
this

$> java -cp . level1.ex01.FirstWord "   "

$> java -cp . level1.ex01.FirstWord "a" "b"

$> java -cp . level1.ex01.FirstWord "  lorem,ipsum  "
lorem,ipsum

$>
```