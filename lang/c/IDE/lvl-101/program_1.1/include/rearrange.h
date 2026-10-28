#ifndef REARRANGE_H
#define REARRANGE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_COLS 20
#define MAX_INPUT 2000


int read_column_numbers(int column[], int max);
void rearrange(char *output, char const *input, int n_columns, int const columns[]);


#endif