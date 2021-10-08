          .text   
		  .equ HEX03, 0xFF200020
		  .equ HEX45, 0xFF200030
          .global HEX_clear_ASM
          .global HEX_flood_ASM
          .global HEX_write_ASM

HEX_clear_ASM: 				
          LDR R1, =HEX03 // store address of 0-3 to R1
		  MOV R3, #1	 // initialize R3 (for comparison)
		  MOV R4, #0	 // counter
clear03:  CMP R4, #4	 // we only loop 4 times for HEX 0-3
		  BGE goto45     // change address to 4-5 if 0-3 already checked
		  TST R0, R3	 // AND R0 with R3
		  BNE clear		 // if not 0, clear
		  LSL R3, R3, #1 // left shift comparison value (one-hot encoding)
		  ADD R4, R4, #1 // update counter
		  B clear03      // check next HEX
clear:	  ADD R5, R4, R1 // get the next HEX location
		  MOV R6, #0      			
		  STRB R6, [R5]	 // clear the location
		  LSL R3, R3, #1 // left shift comparison value
		  ADD R4, R4, #1 // update counter
		  B clear03      // check the next HEX location
goto45:   LDR R2, =HEX45 // change address to 4-5	
		  MOV R4, #0	 // reset counter
clear45:  CMP R4, #2	 // check for max 2 times			
		  BXGE LR	     
		  TST R0, R3	 // AND with R3
		  BNE clear2	 // if not 0, clear
		  LSL R3, R3, #1 // left shift comparison value
		  ADD R4, R4, #1 // update counter
		  B clear45
clear2:	  ADD R5, R4, R2 // get the next HEX location
		  MOV R6, #0
		  STRB R6, [R5]	 // clear the location
		  LSL R3, R3, #1 // left shift the comparison value
		  ADD R4, R4, #1 // update counter
		  B clear45      // check for 5
					
HEX_flood_ASM:
		  LDR R1, =HEX03 // load the address for 0-3
		  MOV R3, #1	 // initialize comparison value 
		  MOV R4, #0	 // counter
flood03:  CMP R4, #4	 // max 4 loops for 0-3
		  BGE end03	     // go to 4-5 if 0-3 all checked
		  TST R0, R3	 // AND with R3
		  BNE flood		 // if not equal 0, flood
		  LSL R3, R3, #1 // left shift the comparison value
		  ADD R4, R4, #1 // update the counter
		  B flood03      // check the next HEX
flood:	  ADD R5, R4, R1 // get the next location
		  MOV R6, #255   // 11111111	
		  STRB R6, [R5]  // store the byte
		  LSL R3, R3, #1 // left shift the comparison value
		  ADD R4, R4, #1 // update the counter
		  B flood03      // check the next HEX
end03:    LDR R2, =HEX45 // load the address for 4-5
		  MOV R4, #0     // initialize the counter
flood45:  CMP R4, #2	 // max 2 loops in this case
		  BXGE LR
		  TST R0, R3     // AND with R3
		  BNE flood2     // if not 0, flood
		  LSL R3, R3, #1 // left shift the comparison value
		  ADD R4, R4, #1 // update counter
		  B flood45      // check the next HEX
flood2:	  ADD R5, R4, R2 // get the exact location
		  MOV R6, #255   // 11111111 
		  STRB R6, [R5]  // flood
		  LSL R3, R3, #1 // left shift the comparison value
		  ADD R4, R4, #1 // update the counter
		  B flood45      // check the next HEX
		  
HEX_write_ASM:         // R0 will be the destination and R1 will be the wanted value  为什么啊
check0:   LDR R2, =HEX03 // R2 stores address of HEX 0-3
          LDR R3, =HEX45 // R3 stores address of HEX 4-5
          MOV R4, #1     // R4 is the comparison value
          MOV R5, #0     // R5 is the counter
          CMP R1, #0   // check if the wanted value is 0
          BNE check1   // if not, go to check 1
		  MOV R1, #63  // the code we need is 0111111
          B write
check1:   CMP R1, #1   // check if the wanted value is 1
          BNE check2   // if not, go to check 1
		  MOV R1, #6   // the code we need is 0000110
          B write
check2:   CMP R1, #2   // check if the wanted value is 2
          BNE check3   // if not, go to check 1
		  MOV R1, #91  // the code we need is 1011011
          B write
check3:   CMP R1, #3   // check if the wanted value is 3
          BNE check4   // if not, go to check 1
		  MOV R1, #79  // the code we need is 1001111
          B write
check4:   CMP R1, #4   // check if the wanted value is 4
          BNE check5   // if not, go to check 1
		  MOV R1, #102 // the code we need is 0111111
          B write
check5:   CMP R1, #5   // check if the wanted value is 5
          BNE check6   // if not, go to check 1
		  MOV R1, #109 // the code we need is 1101101
          B write
check6:   CMP R1, #6   // check if the wanted value is 6
          BNE check7   // if not, go to check 1
		  MOV R1, #125 // the code we need is 1111101
          B write
check7:   CMP R1, #7   // check if the wanted value is 7
          BNE check8   // if not, go to check 1
		  MOV R1, #39  // the code we need is 0100111
          B write
check8:   CMP R1, #8   // check if the wanted value is 8
          BNE check9   // if not, go to check 1
		  MOV R1, #127 // the code we need is 1111111
          B write
check9:   CMP R1, #9   // check if the wanted value is 9
          BNE checkA   // if not, go to check 1
		  MOV R1, #111 // the code we need is 1101111
          B write
checkA:   CMP R1, #10  // check if the wanted value is A
          BNE checkB   // if not, go to check 1
		  MOV R1, #119 // the code we need is 1110111
          B write
checkB:   CMP R1, #11  // check if the wanted value is B
          BNE checkC   // if not, go to check 1
		  MOV R1, #124 // the code we need is 1111100
          B write
checkC:   CMP R1, #12  // check if the wanted value is C
          BNE checkD   // if not, go to check 1
		  MOV R1, #57  // the code we need is 0111001
          B write
checkD:   CMP R1, #13  // check if the wanted value is D
          BNE checkE   // if not, go to check 1
		  MOV R1, #94  // the code we need is 1011110
          B write
checkE:   CMP R1, #14  // check if the wanted value is E
          BNE checkF   // if not, go to check 1
		  MOV R1, #121 // the code we need is 1111001
          B write
checkF:   MOV R1, #113 // the only case left is F and the code we need is 1110001
          B write		  
write:	  CMP R5, #4	  // check the counter, 
		  BGE END1	      // if >= 4, go to HEX 4-5
		  TST R0, R4	  // AND the HEX location with comparison value 
		  BNE write03	  // overwrite if not equal to 0 (means it needs update)
		  LSL R4, R4, #1  // left shift the comparison value
		  ADD R5, R5, #1  // update the counter
		  B write		  // check the next HEX
write03:  ADD R6, R5, R2  // in HEX 0-3, go to the target HEX location
		  STRB R1, [R6]	  // overwrite the target location
		  LSL R4, R4, #1  // left shift the comparison value
		  ADD R5, R5, #1  // update the counter
		  B write		  // check the next HEX
END1:	  MOV R5, #0	  // reset the counter
write2:	  CMP R5, #2	  // check the counter
		  BXGE LR		  // if >= 2, return 
		  TST R0, R4	  // AND the HEX location with comparison value
		  BNE write45	  // overwrite if not equal to 0 (means it needs update)
		  LSL R4, R4, #1  // left shift the compaison value
		  ADD R5, R5, #1  // update the counter
		  B write2		  // check the next HEX
write45:  ADD R6, R5, R3  // in HEX 0-3, go to the target HEX location
		  STRB R1, [R6]	  // overwrite the target location
		  LSL R4, R4, #1  // left shift the comparison value
	      ADD R5, R5, #1  // update the counter
		  B write		  // check the next HEX
END2:     BX LR	
          
		  .end
