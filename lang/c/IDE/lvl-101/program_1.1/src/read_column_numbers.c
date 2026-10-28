#include "rearrange.h"

/*
** Read the list of column numbers,
** ignoring any beyond the specified maximum.
*/
int read_column_numbers(int column[], int max) {
    int num;
    int ch;

    num = 0;
    while (num < max && scanf("%d", &column[num]) == 1 && column[num] >= 0)
        num += 1;

    if (num % 2 != 0) {
        puts("Last column number is not paired.");
        exit(EXIT_FAILURE);
    }

    while ((ch = getchar()) != EOF && ch != '\n')
        ;
    return num;
}