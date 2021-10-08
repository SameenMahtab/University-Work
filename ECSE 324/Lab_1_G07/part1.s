			.text
			.global _start 

_start: 
			LDR R4, =RESULT     //R4 points to the result location //address of result loads to r4
			LDR R2, [R4, #4]	// R2 holds the number of elements in the list //contents at R4+4 load to R2 
			ADD R3, R4, #8		//R3 points to the first number    
			LDR R0, [R3]		//Maximum
          
LOOP: 		SUBS R2, R2, #1 	//decrement the loop counter
			BEQ DONE 			//end loop if counter has reached 0
			ADD R3, R3, #4		//R3 points to the next number in the list 
			LDR R1, [R3]		//R1 holds the next number in the list 
			CMP R0, R1    			//check if R1 is less than the minimum 
			BGE LOOP            //if no, branch back to the loop
			MOV R0, R1          //if yes,update the current min
			B LOOP              //branch back to the loop



DONE:    	
            STR R0, [R4]      //store the location to the memory location(maximum)

END:    	B END              //infinite loop

RESULT:   	.word   0    //memory assigned for result location
N:       	.word   7          //number of entries in the list
NUMBERS:  	.word   4,5,3,6    //the list data
         	.word   12,8,2


