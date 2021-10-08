		.text
        .global _start

_start:
         LDR R0, =N   //R0 points to the value of N
         LDR R1, [R0] //R1 holds the value of N
         BL FIBLOOP      //Branch to subroutine
         STR R1, [R0, #-4]
END:     B END


FIBLOOP: 
         PUSH {R3, R4, LR}  //Push registers to be used in the subroutine onto the stack
         CMP R1, #2   //Compare the value of N with the number 2
         BGE LOOP2    //If greater than 2 branch to LOOP2
                      //Keep on running this subroutine until the value of R1 is less than 2
         BLT ELSE     //inside ELSE we are going to set R0 to hold 1


LOOP2:
         SUB R1, R1, #1   //R1 holds N-1
         SUB R3, R1, #1   //R4 holda N-2
         BL FIBLOOP       //branch to LOOP and check again to see if N-1 is       less than 2
         MOV R4, R1       //Make R5 hold fib(N-1)
         MOV R1, R3       //R1 now holds N-2, This will be used later to do fib(N-2)
         BL FIBLOOP
         ADD R1, R1, R4
         B RETURN



ELSE:   
         MOV R1, #1    //Set R0 to hold 1
         B RETURN 

RETURN: POP {R3, R4, LR}
        BX LR




RESULT: .word 0
N:      .word 10