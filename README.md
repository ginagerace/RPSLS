# RPSLS
CS 342: Rock Paper Scissors Lizard Spock (Version 1). Classic Rock Paper Scissors game with two added choices. Networked game with a server equipped to handle 2 clients. Multiple GUIs with different scenes.

Write-up:
You will implement the game Rock, Paper, Scissors, Lizard, Spock. This is just an
augmented version of the traditional Rock, Paper, Scissors game. Your implementation
will be a two player game where each player is a separate client and the game is run by
a server. Your server and clients will use the same machine; with the server choosing a
port on the local host and clients knowing the local host and port number (just as was
demonstrated in class). Each round of the game will be worth one point. Games will be
played until one of the players has three points. At the end of each game, each user will
be able to play again or quit.
All networking must be done utilizing Java Sockets. The server must handle each client
on a separate thread. You may not use libraries or classes not included in a standard
JDK (java development kit).

Implementation Details:
You will create separate programs, each with a GUI created in JavaFX, for the server
and the client. 
