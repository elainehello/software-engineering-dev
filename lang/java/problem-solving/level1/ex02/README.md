# ex02 - FizzBuzz

| | |
|---|---|
| **Assignment name** | FizzBuzz |
| **Expected files** | `FizzBuzz.java` |
| **Allowed methods** | `System.out.println` |

---

## Instructions

Write a program that prints the numbers from 1 to 100, each separated by a newline.

If the number is a multiple of 3, it prints `fizz` instead.

If the number is a multiple of 5, it prints `buzz` instead.

If the number is both a multiple of 3 and a multiple of 5, it prints `fizzbuzz` instead.

---

## Compilation & Usage

```bash
# Compile
mvn compile

# Run
java -cp level1/ex02/target/classes level1.ex02.FizzBuzz
```

---

## Examples

```bash
$> mvn compile
$> java -cp level1/ex02/target/classes level1.ex02.FizzBuzz
1
2
fizz
4
buzz
fizz
7
8
fizz
buzz
11
fizz
13
14
fizzbuzz
[...]
97
98
fizz
buzz
$>
```