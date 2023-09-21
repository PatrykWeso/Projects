//Patryk Wesolowski R00177977
#include <stdio.h>
#include <stdlib.h>
#include <curses.h>
#include <string.h>
#define WALL 'w'
#define POTION '#'
#define Want_POTIONS 3

struct maze_print
{
	int row;
	int col;
};

struct maze
{
	char **a;	// 2D array supporting maze
	unsigned int w;	// width
	unsigned int h;	// height
	unsigned int cell_size;	// number of chars per cell; walls are 1 char
};

/**
 *Represents a cell in the 2D matrix.
 */
struct cell
{
	unsigned int x;

	unsigned int y;

};

/**
 *Stack structure using a list of cells.
 *At element 0 in the list we have NULL.
 *Elements start from 1 onwards.
 *top_of_stack represents the index of the top of the stack
 *in the cell_list.
 */
struct stack
{
	struct cell * cell_list;

	unsigned int top_of_stack;

	unsigned int capacity;

};


/**
 *Initialises the stack by allocating memory for the internal list
 */
void
init_stack(struct stack *stack, unsigned int capacity)
{
	stack->cell_list =
		(struct cell *) malloc(sizeof(struct cell) *(capacity + 1));

	stack->top_of_stack = 0;

	stack->capacity = capacity;

}

void

free_stack(struct stack *stack)
{
	free(stack->cell_list);

}

/**
 *Returns the element at the top of the stack and removes it
 *from the stack.
 *If the stack is empty, returns NULL
 */
struct cell
stack_pop(struct stack *stack)
{
	struct cell cell = stack->cell_list[stack->top_of_stack];

	if (stack->top_of_stack > 0)

		stack->top_of_stack--;

	return cell;

}

/**
 *Pushes an element to the top of the stack.
 *If the stack is already full (reached capacity), returns -1.
 *Otherwise returns 0.
 */
int
stack_push(struct stack *stack, struct cell cell)
{
	if (stack->top_of_stack == stack->capacity)

		return -1;

	stack->top_of_stack++;

	stack->cell_list[stack->top_of_stack] = cell;

	return 0;

}

int
stack_isempty(struct stack *stack)
{
	return stack->top_of_stack == 0;

}

//-----------------------------------------------------------------------------

void
mark_visited(struct maze *maze, struct cell cell)
{
	maze->a[cell.y][cell.x] = 'v';

}

/**
 *Convert a cell coordinate to a matrix index.
 *The matrix also contains the wall elements and a cell might span
 *multiple matrix cells.
 */
int
cell_to_matrix_idx(struct maze *m, int cell)
{
	return (m->cell_size + 1) *cell + (m->cell_size / 2) + 1;

}

/**
 *Convert maze dimension to matrix dimension.
 */
int
maze_dimension_to_matrix(struct maze *m, int dimension)
{
	return (m->cell_size + 1) *dimension + 1;

}

/**
 *Returns the index of the previous cell (cell - 1)
 */
int
matrix_idx_prev_cell(struct maze *m, int cell_num)
{
	return cell_num - (m->cell_size + 1);

}

/**
 *Returns the index of the next cell (cell + 1)
 */
int
matrix_idx_next_cell(struct maze *m, int cell_num)
{
	return cell_num + (m->cell_size + 1);

}

/**
 *Returns into neighbours the unvisited neighbour cells of the given cell.
 *Returns the number of neighbours.
 *neighbours must be able to hold 4 cells.
 */
int
get_available_neighbours(struct maze *maze, struct cell cell,
	struct cell *neighbours)
{
	int num_neighbrs = 0;

	// Check above
	if ((cell.y > cell_to_matrix_idx(maze, 0)) &&
		(maze->a[matrix_idx_prev_cell(maze, cell.y)][cell.x] != 'v'))

	{
		neighbours[num_neighbrs].x = cell.x;

		neighbours[num_neighbrs].y = matrix_idx_prev_cell(maze, cell.y);

		num_neighbrs++;
	}

	// Check left
	if ((cell.x > cell_to_matrix_idx(maze, 0)) &&
		(maze->a[cell.y][matrix_idx_prev_cell(maze, cell.x)] != 'v'))

	{
		neighbours[num_neighbrs].x = matrix_idx_prev_cell(maze, cell.x);

		neighbours[num_neighbrs].y = cell.y;

		num_neighbrs++;
	}

	// Check right
	if ((cell.x < cell_to_matrix_idx(maze, maze->w - 1)) &&
		(maze->a[cell.y][matrix_idx_next_cell(maze, cell.x)] != 'v'))

	{
		neighbours[num_neighbrs].x = matrix_idx_next_cell(maze, cell.x);

		neighbours[num_neighbrs].y = cell.y;

		num_neighbrs++;
	}

	// Check below
	if ((cell.y < cell_to_matrix_idx(maze, maze->h - 1)) &&
		(maze->a[matrix_idx_next_cell(maze, cell.y)][cell.x] != 'v'))

	{
		neighbours[num_neighbrs].x = cell.x;

		neighbours[num_neighbrs].y = matrix_idx_next_cell(maze, cell.y);

		num_neighbrs++;
	}

	return num_neighbrs;

}

/**
 *Removes a wall between two cells.
 */
void
remove_wall(struct maze *maze, struct cell a, struct cell b)
{
	int i;

	if (a.y == b.y)

	{
		for (i = 0; i < maze->cell_size; i++)

			maze->a[a.y - maze->cell_size / 2 + i][a.x -
				(((int) a.x -
					(int) b.x)) / 2
			] = ' ';
	}
	else

	{
		for (i = 0; i < maze->cell_size; i++)

			maze->a[a.y - (((int) a.y - (int) b.y)) / 2][a.x -

				maze->cell_size / 2 +

				i
			] = ' ';
	}
}

/**
 *Fill all matrix elements corresponding to the cell
 */
void
fill_cell(struct maze *maze, struct cell c, char value)
{
	int i, j;

	for (i = 0; i < maze->cell_size; i++)

		for (j = 0; j < maze->cell_size; j++)

			maze->a[c.y - maze->cell_size / 2 + i][c.x - maze->cell_size / 2 +
				j
			] =
			value;

}

/**
 *This function generates a maze of width x height cells.
 *Each cell is a square of cell_size x cell_size characters.
 *The maze is randomly generated based on the supplied rand_seed.
 *Use the same rand_seed value to obtain the same maze.
 *
 *The function returns a struct maze variable containing:
 *- the maze represented as a 2D array (field a)
 *- the width (number of columns) of the array (field w)
 *- the height (number of rows) of the array (field h).
 *In the array, walls are represented with a 'w' character, while
 *pathways are represented with spaces (' ').
 *The edges of the array consist of walls, with the exception
 *of two openings, one on the left side (column 0) and one on
 *the right (column w-1) of the array. These should be used
 *as entry and exit.
 */
struct maze generate_maze(unsigned int width, unsigned int height, unsigned int cell_size, int rand_seed){
    int row, col, i;
    struct stack stack;
    struct cell cell;
    struct cell neighbours[4];  // to hold neighbours of a cell
    int num_neighbs;
    struct maze maze;
    maze.w = width;
    maze.h = height;
    maze.cell_size = cell_size;
    maze.a = (char**)malloc(sizeof(char*)*maze_dimension_to_matrix(&maze, height));

    // Initialise RNG
    srandom(rand_seed);

    // Initialise stack
    init_stack(&stack, width*height);

    // Initialise the matrix with walls
    for (row=0;row<maze_dimension_to_matrix(&maze, height);row++){
        maze.a[row] = (char*)malloc(maze_dimension_to_matrix(&maze, width));
        memset(maze.a[row], WALL, maze_dimension_to_matrix(&maze, width));
    }

    // Select a random position on a border.
    // Border means x=0 or y=0 or x=2*width+1 or y=2*height+1
    cell.x = cell_to_matrix_idx(&maze,0);
    cell.y = cell_to_matrix_idx(&maze,random()%height);
    mark_visited(&maze, cell);
    stack_push(&stack, cell);

    while (! stack_isempty(&stack)){
        // Take the top of stack
        cell = stack_pop(&stack);
        // Get the list of non-visited neighbours
        num_neighbs = get_available_neighbours(&maze, cell, neighbours);
        if (num_neighbs > 0){
            struct cell next;
            // Push current cell on the stack
            stack_push(&stack, cell);
            // Select one random neighbour
            next = neighbours[random()%num_neighbs];
            // Mark it visited
            mark_visited(&maze, next);
            // Break down the wall between the cells
            remove_wall(&maze, cell, next);
            // Push new cell on the stack
            stack_push(&stack, next);
        }
    }

    // Finally, replace 'v' with spaces
    for (row=0;row<maze_dimension_to_matrix(&maze, height);row++)
        for (col=0;col<maze_dimension_to_matrix(&maze, width);col++)
            if (maze.a[row][col] == 'v'){
                cell.y = row;
                cell.x = col;
                fill_cell(&maze, cell, ' ');
            }

    // Select an entry point in the top right corner.
    // The first border cell that is available.
    for (row=0;row<maze_dimension_to_matrix(&maze, height);row++)
        if (maze.a[row][1] == ' ') { maze.a[row][0] = ' '; break; }

    // Select the exit point
    for (row=maze_dimension_to_matrix(&maze, height)-1;row>=0;row--)
        if (maze.a[row][cell_to_matrix_idx(&maze,width-1)] == ' ') {
            maze.a[row][maze_dimension_to_matrix(&maze, width)-1] = ' ';
            break;
        }

    maze.w = maze_dimension_to_matrix(&maze, maze.w);
    maze.h = maze_dimension_to_matrix(&maze, maze.h);

    // Add the potions inside the maze at three random locations
    for (i=0;i<Want_POTIONS;i++){
        do{
            row = random()%(maze.h-1);
            col = random()%(maze.w-1);
        }while (maze.a[row][col] != ' ');
        maze.a[row][col] = POTION;
    }

    return maze;
}

void printMaze(struct maze maze, int potionsGot, struct maze_print currentPosition){
	int row, col;
	for (row = 0; row < maze.h; row++){
		for (col = 0; col < maze.w; col++){
			printf("%c", maze.a[row][col]);
		}
		printf("\n\n");
	}
}
//main maze 
void main(){
	int width;
	int height; 
	int cell; 
	int Seed; 
	int i; 
	int p; 
	int j;
	int potionsGot = 0;
	struct maze maze;

	printf("\nEnter width of maze: ");
	scanf("%d", &width);

	printf("\nEnter height of maze : ");
	scanf("%d", &height);

    while(1){
	    printf("\nEnter wall size: ");
	    scanf("%d", &cell);
        if (cell % 2 != 0 && cell > 0){
            break;
        }
    }

	printf("\nEnter a random seed: ");
	scanf("%d", &Seed);
	getchar();
// user input for the maze generation 
	maze = generate_maze(width, height, cell, Seed);
	struct maze_print startPrint;
	struct maze_print currentPosition;
	struct maze_print endIndex;

	for (i = 0; i < maze.h; i++){
		if (maze.a[i][0] != WALL){
			maze.a[i][0] = '*';
			startPrint.row = i;
			startPrint.col = 0;
			currentPosition.row = i;
			currentPosition.col = 0;
			break;
		}
	}

	for (j = 0; j < maze.h; j++){
		if (maze.a[j][maze.w - 1] != WALL){
			endIndex.row = j;
			endIndex.col = maze.w - 1;
			break;
		}
	}
	//printz maze
    printf("Potions: ###\n");
	printMaze(maze, potionsGot, currentPosition);
	char change;
    //if u leave maze youll get a notification
	while (1){
		if (currentPosition.row == endIndex.row && currentPosition.col == endIndex.col && potionsGot == Want_POTIONS){
			printMaze(maze, potionsGot, currentPosition);
			printf("Winner.");
			break;
		}
		putchar('\n');
		char move;
        move = getchar();
        //if you dont have all potions
		
		if (potionsGot < Want_POTIONS){
			printf("Potions: ");
			for (p = 0; (Want_POTIONS - potionsGot) - p; p++){
				putchar(POTION);
			}
			putchar('\n');
		}
		//once they are gotten
		else{
			printf("Now its time to escape :)");
			
		}
        //kinda got stuck but its meant to be the movement of the character 
		switch (move){
			case 'w':
				if (maze.a[currentPosition.row - 1][currentPosition.col] != WALL){
					
					if (maze.a[currentPosition.row - 1][currentPosition.col] == POTION){
						
						potionsGot++;
					}
					
					maze.a[currentPosition.row][currentPosition.col] = ' ';
				
					maze.a[currentPosition.row - 1][currentPosition.col] = '*';
					
					currentPosition.row = currentPosition.row - 1;
				}
				
				break;

			case 'a':
				if (maze.a[currentPosition.row][currentPosition.col - 1] != WALL){
					if (maze.a[currentPosition.row][currentPosition.col - 1] == POTION){
						
						potionsGot++;
					}
					if (currentPosition.row == startPrint.row && currentPosition.col - 1 == startPrint.col - 1){
					
						printMaze(maze, potionsGot, currentPosition);
						
						printf("Cant go where you cant see");
					
						break;
					}
					maze.a[currentPosition.row][currentPosition.col] = ' ';
					
					maze.a[currentPosition.row][currentPosition.col - 1] = '*';
				
					currentPosition.col = currentPosition.col - 1;
				}
				break;

			case 's':
				if (maze.a[currentPosition.row + 1][currentPosition.col] != WALL){
					
					if (maze.a[currentPosition.row + 1][currentPosition.col] == POTION){
					
						potionsGot++;
					}
				
					maze.a[currentPosition.row][currentPosition.col] = ' ';
				
					maze.a[currentPosition.row + 1][currentPosition.col] = '*';
					
					currentPosition.row = currentPosition.row + 1;
				}
				break;

			case 'd':
				if (maze.a[currentPosition.row][currentPosition.col + 1] != WALL){
				
					if (maze.a[currentPosition.row][currentPosition.col + 1] == POTION){
						
						potionsGot++;
					}
					
					if (currentPosition.row == endIndex.row && currentPosition.col + 1 == endIndex.col && potionsGot != Want_POTIONS){
					
						printMaze(maze, potionsGot, currentPosition);
						
						printf("Potions?");
					
						break;
					}
					
					maze.a[currentPosition.row][currentPosition.col] = ' ';
				
					maze.a[currentPosition.row][currentPosition.col + 1] = '*';
					
					currentPosition.col = currentPosition.col + 1;
				}
				break;

			default:
				printMaze(maze, potionsGot, currentPosition);
				break;
		}
	}
}

