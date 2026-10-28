NAME    = rearrange
CC      = cc
CFLAGS  = -Wall -Wextra -Werror -Iinclude

SRC     = src/main.c \
          src/read_column_numbers.c \
          src/rearrange.c
OBJ     = $(SRC:src/%.c=obj/%.o)

all: $(NAME)

$(NAME): $(OBJ)
	$(CC) $(CFLAGS) $(OBJ) -o $(NAME)

obj/%.o: src/%.c include/rearrange.h
	@mkdir -p obj
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -rf obj

fclean: clean
	rm -f $(NAME)

re: fclean all

.PHONY: all clean fclean re