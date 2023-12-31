# Java-chess-engine

### Create a new game board with fen string:
Example starting fen - "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1" this is a standard chess notation widely
```java
    engine.Board board = new engine.Board() // creates a new board with the starting chess FEN
    engine.Board board = new engine.Board(fenString); //create board with custom FEN
```
Takes the standard chess notation FEN as input and creates a board in that position.


### Print board to console:

```java
    board.prettyPrintBoard();
```
#### Example output:
```
8 | r | n | b | q | k | b | n | r |
7 | p | p | p | p | p | p | p | p | 
6 |   |   |   |   |   |   |   |   | 
5 |   |   |   |   |   |   |   |   |
4 |   |   |   |   |   |   |   |   |
3 |   |   |   |   |   |   |   |   |
2 | P | P | P | P | P | P | P | P |
1 | R | N | B | Q | K | B | N | R |
    a   b   c   d   e   f   g   h
```

### Get fen string of current board position:
```java
    board.convertBoardToFen();
```

### engine.Board legal moves:

#### Return a List<engine.Move> of all the legal moves in the boards current position:
```java
    board.boardLegalMoves();
```
Returns a list of engine.Move objects.

### Perform a legal move to the current position:
#### engine.Move can be performed by passing the starting square and ending square to the doLegalMove method:
```java 
    int[] move1 = new int[] {engine.Util.convertSquarePosToBoardIndex("a2"), engine.Util.convertSquarePosToBoardIndex("a4")};
    board.doLegalMove(move1);
```
This will return true if the move was legal  and if so the board will be correctly updated and alternate the side;
Otherwise, this will return false and the board will not be updated.
Pass in array with {startsquare, endsquare} instead of move object - engine.Move object is only used for evaluating the legal moves and board positions.

### Undo the previous move on the board
```java
    board.undoLastMove();
```
This will remove the last played move from the stack and undo it, updated all the board attributes to the previous position.

### Converting chess square to correct index on the board:
#### engine.Board is represented as  1d array of length 64 so methods in engine.Util class can convert between chess notation and board index - e.g "a1" -> 56
```java
    engine.Util.convertSquarePosToBoardIndex("a1"); //returns 56
```
#### Likewise this can go in the reverse direction:
```java 
    engine.Util.convertBoardIndexToSquare(56); //returns a1
```

### engine.Move object:

```java
    //Constructor for move object - represents important properties of a move so that the move can be undone when removed from move stack
    public engine.Move(int startSquare, int endSquare, char piece, char capturedPiece, boolean isEnPassant, int enPassantTargetSquare, boolean isDoublePawnMove){
        this.startSquare = startSquare;
        this.endSquare = endSquare;
        this.piece = piece;
        this.capturedPiece = capturedPiece;
        this.isEnPassant = isEnPassant;
        this.enPassantTargetSquare = enPassantTargetSquare;
        this.isDoublePawnMove = isDoublePawnMove;
    }

    //example creation of a move - in this case it is an en passant capture move
    engine.Move exampleMove =  new engine.Move(36,43, 'p', 'P' , true, board.getEnPassantTargetSquare(), false);
```
engine.Move object has an overridden .equals method so that a int[] move can be compared to a move object. This simplifies the process of making a legal move as a engine.Move object doesnt have to be created for each move request.
***See doLegalMove method for clarification.

### Castling now correctly implemented -- can now play full chess game by running the engine.Main class in terminal.
### engine.Board is correctly updated on each move and a stack is used to track the moves
### Aim is to implement a minimax chess ai once all the move logic has been completed

---

Now deployed to maven central repository. To include add below to pom.xml:
```
    <dependency>
        <groupId>io.github.purgz</groupId>
        <artifactId>java-chess-engine</artifactId>
        <version>1</version>
    </dependency>
```
Can also find on maven central repository under group io.github.purgz:
https://search.maven.org/artifact/io.github.purgz/java-chess-engine/1/jar
