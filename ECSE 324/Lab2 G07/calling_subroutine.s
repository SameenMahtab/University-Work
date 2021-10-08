			.text
            .global _start

_start:
            LDR R4, =RESULT   //R4 points to the result location
            LDR R2, [R4, #4]  //R2 holds the number of elements   //Value of R2 is 7 
            ADD R3, R4, #8  //R3 points to the first number   //R3 
            LDR R0, [R3]        //R0 has the value of first nubmer, the first maximum	//R0's value is 4 at the beginning 		
			PUSH {LR}  //store the LR into the stack 
			BL LOOP   //go to subroutine LOOP
            POP {LR}
			STR R0, [R4]
			B END
 
            


LOOP:       PUSH {LR}  //store the LR into the stack 
			SUBS R2, R2, #1     //decrement the loop counter
            BEQ DONE             //end loop if counter has reached 0
            ADD R3, R3, #4        //R3 points to the next number in the list
            LDR R1, [R3]        //R1 holds the next number in the list
            CMP R0, R1                //check if R1 is less than the minimum
            BGE LOOP            //if no, branch back to the loop
            MOV R0, R1          //if yes,update the current min
            B LOOP              //branch back to the loop

DONE:
            POP {LR}
            BX LR

END:        B END              //infinite loop

RESULT:       .word   0    //memory assigned for result location
N:           .word   7          //number of entries in the list
NUMBERS:      .word   4,5,3,6    //the list data
.word   12,8,2
