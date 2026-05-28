# ex04 - FtStrcpy

| | |
|---|---|
| **Assignment name** | FtStrcpy |
| **Expected files** | `FtStrcpy.java` |
| **Allowed methods** | None |

---

## Instructions

Reproduce the behavior of the function `strcpy`.

Copies the string pointed to by `s2` into `s1` character by character until
the null terminator is reached. Returns `s1`.

Your method must be declared as follows:

```java
public static char[] ftStrcpy(char[] s1, char[] s2)
```

---

## Compilation & Usage

```bash
# Compile
mvn compile

# Run
java -cp level1/ex04/target/classes level1.ex04.FtStrcpy "hello"
```

---

## Examples

```bash
$> mvn compile
$> java -cp level1/ex04/target/classes level1.ex04.FtStrcpy "hello"
hello

$> java -cp level1/ex04/target/classes level1.ex04.FtStrcpy "42 is fun"
42 is fun

$>
```