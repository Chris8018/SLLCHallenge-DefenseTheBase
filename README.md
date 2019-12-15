# Defense the Base
**Implementation By David Akhihiero in SoloLearn Community**

The objective of this project is to build a defense system, which will hit and destroy enemy objects the radar tracks flying objects and displays the data on a given NxN board
Each detected object is represented as a rectangle on the board, taking one or more cells.

/   | 0   | 1   | 2   | 3   | 4   | 5
--- | --- | --- | --- | --- | --- | ---
0   |     |     | Q   | Q   | Q   |
1   |     |     | Q   | Q   | Q   |
2   |     |     |     |     |     |
3   |     |     |     |     |     |
4   |     |     |     |     |     | A

(Table) demonstrates 2 objects detected by the radar: one covering 6 cells form [0][1] to [1][3], the other one covering the cell [4][5]. Each object has a speed of movement, which is represented by the number of cells the objects moves in a given direction in a single timeframe.

Eg: let's suppose the bigger object moves down with speed of 1, while the smaller object move left with a speed of 3

/   | 0   | 1   | 2   | 3   | 4   | 5
--- | --- | --- | --- | --- | --- | ---
0   |     |     |     |     |     |
1   |     |     | Q   | Q   | Q   |
2   |     |     | Q   | Q   | Q   |
3   |     |     |     |     |     |
4   |     |     |     | A   |     |

(Table) This is their position after a single move. Each object has a given movement pattern represented by the letter L, R, U D. Identify enemy object by color: BLUE objects are enemy, GREEN objects are allies
.Given LIMITED number of missiles, you gave to create a defense system to destroy enemy.

MISSILE (Speed, Power)
*Missile can be launched form ANY CELL of the LAST ROW of the board. Each missile can have a movement pattern associated with it. Your defense system has to calculate the most effective paths for the missiles to destroy the largest number of enemy objects. You might need to hit an object multiple times in order to destroy it completely.

The program should take the size of the board and number of missiles as input, generate the objects and their  properties randomly (Speed, Color, Movement pattern, Coordinates on the board) and output the launch position of the missiles along with their movement patterns

==> This approach feels somewhat wrong. Also, I have created bug during re-implement
