		.text
		.global _start
_start:
		LDR R5, =NUMBERS
		LDR R0,[R5]
		LDR R1, [R5, #4]
		

//push R0 first ,then R1 
//decrement the stack pointer by 4 (grow the stack) 
//then copy the word from R0 to the top of stack 
		SUB SP, SP, #4     
		STR R0, [SP]
		SUB SP, SP, #4
		STR R1, [SP]
//pop R1 first, store the value from the stack to R2
//then pop R2 , store the value from the stack to R3
//load (pop) the top value from the stack into register R2 
//then increment the stack pointer by 4 s.t. it points to the new element 
		LDR R2, [SP]
		ADD SP, SP,#4

		LDR R3, [SP]
		ADD SP,SP,#4
		
		
END: B END 

//RESULT: .word 0 
NUMBERS: .word 1, 3, 4, 7, 10 



