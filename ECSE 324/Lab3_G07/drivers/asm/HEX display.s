.text
.equ HEX03, 0xFF200020
.equ HEX45, 0XFF200030
.global HEX_clear_ASM
.global HEX_flood_ASM
.global HEX_write_ASM

HEX_clear_ASM:
    LDR R1, =HEX03     //load the address of HEX03 to R1
	MOV R3, #1
    MOV R4, #0          //R4 is a counter
    MOV R5, #0b00000000
Loop:
    CMP R4, #4      //only loop 4 times for HEX0 ~ HEX3
    BGE Clear45     //if HEX0 ~HEX3 are already checked, check HEX4 and HEX5
    TST R0, R3      // Do bitwise AND operation e.g. 0001 AND 0001    
    BNE Clear       //compare the result of AND operation with 0 . If not equal to 0, Clear; if equal to 0, go to next instruction 
    ADD R4, R4, #1        //update  the counter
    LSL R3, R3, #1     //left shift comparison value 
    B Loop


Clear:
    STRB R5, [R1, R4]      //store the value of R5 to the address of R1 + R4   //byte-addressable 
    ADD R4, R4, #1
    LSL R3, R3, #1
    B Loop


Clear45:
    LDR R2, =HEX45    //load address of HEX45 to R2
    MOV R4, #0        //reset counter
Loop2:
    CMP R4, #2     //only loop 2 times for HEX4 and HEX5
    BXGE LR
    TST R0, R3      //Do R0 AND R3
    BNE Clear2
    ADD R4, R4, #1
    LSL R3, R3, #1
    B Loop2

Clear2:
    STRB R5, [R2, R4]//i want to make the value stored in R5 to be 0
    ADD R4, R4, #1
    LSL R3, R3, #1
    B Loop2


HEX_flood_ASM:
    LDR R1, =HEX03
    MOV R3, #1
    MOV R4, #0
    MOV R5, #0b01111111

Loop_Flood:
    CMP R4, #4
    BGE Flood45
    TST R0, R3
    BNE Flood
    ADD R4, R4, #1
    LSL R3, R3, #1
    B Loop_Flood


Flood:
    STRB R5, [R1, R4]
    ADD R4, R4, #1
    LSL R3, R3, #1
    B Loop_Flood


Flood45:
    LDR R2, =HEX45
    MOV R4, #0

Loop2_Flood:
    CMP R4, #2
    BXGE LR
    TST R0, R3
    BNE Flood2
    ADD R4, R4, #1
    LSL R3, R3, #1
    B Loop2_Flood

Flood2:
    STRB R5, [R2, R4]
    ADD R4, R4, #1
    LSL R3, R3, #1
    B   Loop2_Flood


HEX_write_ASM:  
    LDR R2, =HEX03
    LDR R3, =HEX45
    MOV R4, #1
    MOV R5, #0
Check0:
    CMP R1, #0    //check if the wanted value is 0
    BNE Check1   //if not, go to Check1
    MOV R1, #63    //00111111
    B Loop_Write

Check1:
CMP R1, #1
BNE Check2
MOV R1, #6
B Loop_Write   //0000110

Check2:
CMP R1, #2
BNE Check3
MOV R1, #91
B Loop_Write

Check3:
CMP R1, #3
BNE Check4
MOV R1, #79 //1001111
B Loop_Write

Check4:
CMP R1, #4
BNE Check5
MOV R1, #102  //01100110
B Loop_Write

Check5:
CMP R1, #5
BNE Check6
MOV R1, #109   //01101101
B Loop_Write

Check6:
CMP R1, #6
BNE Check7
MOV R1, #125   //01111101
B Loop_Write

Check7:
CMP R1, #7
BNE Check8
MOV R1, #7
B Loop_Write   //0000111

Check8:
CMP R1, #8
BNE Check9
MOV R1, #127    //01111111
B Loop_Write

Check9:
CMP R1, #9
BNE CheckA
MOV R1, #103    //01100111
B Loop_Write

CheckA:
CMP R1, #10
BNE CheckB
MOV R1, #119   //01110111
B Loop_Write

CheckB:
CMP R1, #11
BNE CheckC
MOV R1, #124    //01111100
B Loop_Write

CheckC:
CMP R1, #12
BNE CheckD
MOV R1, #57     //00111001
B Loop_Write


CheckD:
CMP R1, #13
BNE CheckE
MOV R1, #94     //01011110
B Loop_Write

CheckE:
CMP R1, #14
BNE CheckF
MOV R1, #121   //01111001
B Loop_Write

CheckF:  //the only case left is F , move the number into R1
MOV R1, #113    //01110001
B Loop_Write

Loop_Write:
    CMP R5, #4
    BGE Write45
    TST R0, R4   
    BNE Write    
    ADD R5, R5, #1
    LSL R4, R4, #1
    B Loop_Write


Write:
    STRB R1, [R2, R5]
    ADD R5, R5, #1
    LSL R4, R4, #1
    B Loop_Write


Write45:
    MOV R5, #0

Loop2_Write:
    CMP R5, #2
    BXGE LR
    TST R0, R4
    BNE Write2
    ADD R5, R5, #1
    LSL R4, R4, #1
    B Loop2_Write

Write2:
    STRB R1, [R3, R5]
    ADD R5, R5, #1
    LSL R4, R4, #1
    B   Loop2_Write

.end

