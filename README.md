# Sudoku Solver

A simple command line application that I wrote a few years ago for personal use, solving Sudoku puzzles.

## Usage

> java sudoku-solver.jar \[\<board file\>\]

If no board file is provided, the default board file 'board.txt' in your current directory is used.

The board file is a text file containing 9 lines of text, each line with 9 characters, containing the
known numbers from the board. Blank cells can be represented with spaces or dots.

Here is an example input file:

board.txt:
<pre>
.38......
...5.8...
..7....6.
....7..46
..9......
2...4..71
47.......
...7.98..
.....25..
</pre>

## Output

The program prints the solution, logging the intermediate steps to get there. Following the log file from the top
can provide useful hints when you are stuck trying to solve a puzzle by hand!

An example output:
<pre>

... lots of detail excluded ...

Iteration 14...
CHECKING FOR COMBI GROUPS
Iteration 14 complete
SOLUTION:
1  |  3|   #   |   |   #   |   | 2 
   |   |   #  6|   |   #4  | 5 |   
   |   | 8 #   |  9|7  #   |   |   
-----------------------------------
   | 2 |   #   |1  |   #   |   |  3
  6|   |4  # 5 |   |   #   |   |   
   |   |   #   |   | 8 #7  |  9|   
-----------------------------------
   |   |   # 2 |  3|   #1  |   |   
 5 |   |   #   |   |4  #   |  6|   
   |  9|7  #   |   |   #   |   | 8 
###################################
   |1  |  3#   |   |   # 2 |   |   
   |   |   #   |   | 5 #   |4  |  6
 8 |   |   #  9|7  |   #   |   |   
-----------------------------------
   |   |   #1  | 2 |   #  3|   |   
   |4  |   #   |   |  6#   |   | 5 
7  |   |  9#   |   |   #   | 8 |   
-----------------------------------
 2 |   |   #   |   |  3#   |   |1  
   | 5 |  6#   |4  |   #   |   |   
   |   |   # 8 |   |   #  9|7  |   
###################################
   |   |   #  3|   |1  #   | 2 |   
4  |   | 5 #   |   |   #  6|   |   
   |7  |   #   | 8 |   #   |   |  9
-----------------------------------
  3|   | 2 #   |   |   #   |1  |   
   |  6|   #   | 5 |   #   |   |4  
   |   |   #7  |   |  9# 8 |   |   
-----------------------------------
   |   |1  #   |   | 2 #   |  3|   
   |   |   #4  |  6|   # 5 |   |   
  9| 8 |   #   |   |   #   |   |7  
###################################
</pre>
