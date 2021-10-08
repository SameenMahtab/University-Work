.text 
			.global _start 
_start:
		LDR   R9, =FALSE    
		LDR   R1, [R9]    //store the FALSE in R1  //R1 has the contents of 0 now 
		LDR   R10 ,=N  		//points towards the number N in R10   
		LDR   R2, [R10]    //store the number N in R2 
		ADD   R12, R10, #4    
		LDR   R3, [R12]  //store NUMBERS in R3    
		
LOOP: 	
		CMP R1, #0  //check true or false ; if true, program ends ; if false, update R1 to 1 and go to LOOP2 
		BGT   DONE
	  	MOV R1, #1
		B    LOOP2

LOOP2: 
		SUBS  R2, R2, #1 //scan over the list of NUMBERS
		BEQ   LOOP    //reach the end 
		LDR   R4, [R3]  //load the first number in R4 
		LDR   R5, [R3, #4]  //load the next number in R5 
		ADD   R3, R3, #4 // Update the pointer R3 
		CMP   R4, R5 
		BLT    LOOP2    //If the later number is smaller than the previous one, no need to stop , go back to the inner loop and check next pair
		B     SWAP 


SWAP:
		STR R5, [R3, #-4]
		STR  R4, [R3]
		LDR R3 , =NUMBERS
		LDR R2, [R10]
		MOV R1, #0
		B LOOP

DONE: 	B DONE

N: 			.word  	8
NUMBERS: 	.word	4, 5, 6,1, 18, 7,3, 10
FALSE:		.word 	0 




			
