#include "rearrange.h"

int main(void) {
    int n_columns;
    int columns[MAX_COLS];
    char input[MAX_INPUT];
    char output[MAX_INPUT];

    n_columns = read_column_numbers(columns, MAX_COLS);

    while (fgets(input, MAX_INPUT, stdin) != NULL) {
        input[strcspn(input, "\n")] = '\0';
        printf("Original input: %s\n", input);
        rearrange(output, input, n_columns, columns);
        printf("Rearranged line: %s\n", output);
    }

    return (EXIT_SUCCESS);
}