			.text 
			.global _start 
_start: 		
			LDR R0, =N	//R0 points to address of N 
			LDR R4, [R0]		//load the contents of R0 to R4, R4 is 8 in this case 
			ADD R1, R0, #4   //R1 points to the first number 
			LDR R2, [R1]		//load the contents of R1 (the first number) to R2 
			ADD R7, R4, #1 	//set R7 as the counter for getting the sum of list of digits 
			ADD R9, R4, #1 	//set R9 as the counter for (signal-average )   //set R4+1 as the value of R7, and R9 
			MOV R3, #0    // set the contents of R3 and R5 to be 0 first 
			MOV R5, #0
ADDITION: 	
			SUBS R7, R7, #1    
			BEQ DIVISION
			ADD R3, R3, R2		   
			LDR R2 , [R1, #4]			// the next input number loads to R2 
			ADD R1, R1, #4 	//We add R1 by 4 every time we want to load the next number to R2  
			B ADDITION 
						  
DIVISION:	LSR R4, R4, #1 
			CMP R4, #1		//compare the value of R4 and 1
			BLT AVERAGE   	//if R4 is less than 1 , goes to average 
			ADD R5, R5, #1 //if R4 is still greater than 1 , counting the number of division , and run the loop again 
			B DIVISION

AVERAGE:	LSR R3, R3, R5
			B ADDBACK

ADDBACK:	
			SUBS R9, R9, #1 
			BEQ END
			LDR  R6, [R0, #4]  // load inputs into R6    [R0, #4]
			SUBS R8, R6, R3 
			STR R8, [R0, #4]  //store value of R8 in place 
			ADD R0, R0 , #4 	//
			B ADDBACK 

END: 		B END

N:				.word 8			
NUMBERS:		.word 2,3,-5,8,2,5,2,10

