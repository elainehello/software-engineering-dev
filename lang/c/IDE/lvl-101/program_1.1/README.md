# rearrange

Reads lines from standard input and prints a new line built from selected column ranges of each one.

## Build

```sh
make        # build ./rearrange (objects go in obj/)
make clean  # remove obj/
make fclean # remove obj/ and the binary
make re     # rebuild from scratch
```

## Usage

The first input line holds pairs of column numbers (`start end`, 0-based, inclusive), ended by a negative number. Every line after that gets rearranged: the selected ranges are copied in order and joined with no separator.

```sh
echo "0 3 10 12 -1
Hello world, this is a test" | ./rearrange
```

```
Original input: Hello world, this is a test
Rearranged line: Helld, 
```

`0 3` copies `Hell`, and `10 12` copies `d, ` (with a trailing space):

```
H e l l o   w o r l d ,   t
0 1 2 3 4 5 6 7 8 9 10 11 12
```

Ranges can be given in any order to reorder the text:

```sh
echo "6 10 5 5 0 4 -1
Hello world, this is a test" | ./rearrange
# Rearranged line: world Hello
```

## Limits

- Up to `MAX_COLS` (20) column numbers; an odd count is an error.
- Lines up to `MAX_INPUT` (2000) characters.

## Structure

```
include/rearrange.h   constants and prototypes
src/                  main, read_column_numbers, rearrange
Makefile
```