README

	This program tests efficiency and accuracy of several Traveling Salesperson Problem heuristics, including Random Walk, Nearest Neighbor and 2-Opt/Random Swaps.  Testing
  datasets were acquired from TSPLIB: http://elib.zib.de/pub/mp-testdata/tsp/tsplib/tsplib.html and converted to .txt files, which are contained in the Data directory.
  
  Due to the fact that the algorithms tested for this project were implemented solely for empirical testing purposes, little attention was paid to the user interface.  
  The following notes are of certain value should the reader care to run this program.

- All datasets tested are included with this submission in .txt format.  One may enter the dataset they would like to process on Line 20 of the file Graph.java.  The default 
  is set to 'Berlin52.'
- One may enable/disable Random Swap on Line 12 of TwoOptSwap.java.  If initRand is set to 1, Random Swap is enabled (this is the default).  Set to any other value than 1,
  it is disabled.  
- When Random Swap is enabled, the user may choose how many times they would like to run complete iterations of 2-Opt Swap in hopes of achieving successively better results.  
  To do so, set the 'times' variable on Line 83 of TwoOptSwap.java.  By default, 'times' is set to 10.
- One can 'tune' the Random Swap function, as described in the project report.  Change the 'tune' variable on Line 16 of the file TwoOptSwap.java.  This defaults to 10, as 
  10 provided the best results on most datasets by a wide margin.
- Note: the RandWalk class is a work in progress that is intended to implement the random walk heuristic.  It is yet to be integrated into the rest of the program.
