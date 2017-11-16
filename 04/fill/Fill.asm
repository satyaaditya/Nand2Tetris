// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

//PUT SCREEN ADDRESS IN 0 LOCATION
// PUT UR FILLING VALUE IN 1 LOCATION 

(START)
	@SCREEN 
	D = A 
	@0
	M = D
//---------------SCREEN LOCATION STORED----------------------
(CHECKKBD)
	@KBD
	D = M 
	@BLACK
	D;JGT      //-----------GOTO BLACK IF KEY IS PRESSED
	@WHITE
	D;JEQ      //----------ELSE GOTO WHITE

@CHECKKBD
0;JMP 

//------------------------------------------------------------	
(WHITE)
	@1
	M = 0
	@CHANGESCREEN
	0;JMP
	
(BLACK)
	@1
	M = -1
	@CHANGESCREEN
	0;JMP
//-----------------------------------------------------------
(CHANGESCREEN)
@1
D = M
@0
//--VERIFY--
A = M
M = D 

@0
D = M + 1 

@KBD
D = A - D

@0
M = M + 1
A = M 

@CHANGESCREEN
D;JGT
//------------------------------------------------------------
@START 
0;JMP












