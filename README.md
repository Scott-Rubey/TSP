README

	This program tests efficiency and accuracy of several Traveling Salesperson Problem heuristics, including Nearest Neighbor and 2-Opt/Random Swaps.  Testing
  datasets were acquired from TSPLIB: http://elib.zib.de/pub/mp-testdata/tsp/tsplib/tsplib.html and converted to .txt files, which are contained in the Data directory.
  
  The command line arguments are as follows: filename.txt -r
  
  Filenames must include the '.txt' suffix.  The '-r' flag is optional, and signifies that the user would like to enable random swaps as part of the TwoOptSwap algorithm.

  One can 'tune' the Random Swap function, as described in the project report.  The user is prompted to enter a tune value if random swaps are enabled.  A tune value of 10 provided the best results on most datasets by a wide margin.

Note: the RandWalk class is a work in progress that is intended to implement the random walk heuristic.  It is yet to be integrated into the rest of the program.
