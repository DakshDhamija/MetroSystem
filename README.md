# MetroSystem

This Java project simulates a metro system using object-oriented programming concepts. It includes classes to represent metro stops, metro lines, an AVL tree for efficient searching and traversal, as well as trip exploration functionalities.

Classes Overview
MetroStop: Represents a metro station with attributes such as stop name, next and previous stops, associated metro line, and fare.

MetroLine: Represents a metro line composed of metro stops. It provides methods to populate the line from a file, print the line, and compute total stops.

AVLNode and AVLTree: Implements an AVL tree structure where each node stores metro stops. AVL tree operations include insertion, balancing, and searching for metro stops based on stop names.

Trip: Represents a trip between metro stops, tracking fare and previous trip details.

Exploration: Manages a queue of trips for exploration purposes.

MetroSystem: Main class demonstrating the usage of the above classes to populate a metro line from a file, populate an AVL tree with metro stops, and perform a search operation.

Usage
The MetroSystem class in the main method demonstrates typical usage of the metro system functionalities:

Creates a metro line, populates it from a file (RedLine.txt).
Prints the metro line.
Populates an AVL tree with metro stops.
Performs a search for a metro stop ("Central").
Features
Data Structures: Utilizes classes and data structures like linked lists (for metro stops), AVL trees (for efficient searching), and queues (for trip exploration).

File Handling: Reads metro stop data from a text file to populate metro lines dynamically.

Search Operations: Provides efficient searching of metro stops using AVL tree-based searching techniques.

How to Use
To use the Metro System simulation:

Clone the repository.
Compile and run the MetroSystem.java class.
Ensure the RedLine.txt (or other line data) is in the correct format for populating metro lines.
