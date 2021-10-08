          .text   
		  .equ DATA, 0xFF200050
		  .equ INTER, 0xFF200058
		  .equ EDGE, 0xFF20005C
		  .global read_PB_data_ASM
		  .global PB_data_is_pressed_ASM
		  .global read_PB_edgecap_ASM
		  .global PB_edgecap_is_pressed_ASM
		  .global PB_clear_edgecp_ASM
		  .global enable_PB_INT_ASM
		  .global disable_PB_INT_ASM

read_PB_data_ASM:	
          LDR R1, =DATA
          LDR R0, [R1]		  
		  BX LR
		    
read_PB_edgecap_ASM:
          LDR R1, =EDGE
          LDR R0, [R1]		  
		  BX LR		
		
PB_data_is_pressed_ASM: 
          MOV R1, #4   // initialize R1, temporary storage for return value
          CMP R0, #8   // check if it is PB3
          MOVEQ R1, #3 // if yes, R1 = 3
		  CMP R0, #4   // check if it is PB2
		  MOVEQ R1, #2 // if yes, R1 = 2 
		  CMP R0, #2   // check if it is PB1
		  MOVEQ R1, #1 // if yes, R1 = 1
		  CMP R0, #1   // check if it is PB0
	      MOVEQ R1, #0 // if yes, R1 = 0
		  MOV R0, R1   // R0 (return value) = R1
		  BX LR	

	
		  
PB_edgecap_is_pressed_ASM:
          MOV R1, #4   // initialize R1, temporary storage for return value
          CMP R0, #8   // check if it is PB3
          MOVEQ R1, #3 // if yes, R1 = 3
		  CMP R0, #4   // check if it is PB2
		  MOVEQ R1, #2 // if yes, R1 = 2 
		  CMP R0, #2   // check if it is PB1
		  MOVEQ R1, #1 // if yes, R1 = 1
		  CMP R0, #1   // check if it is PB0
	      MOVEQ R1, #0 // if yes, R1 = 0
		  MOV R0, R1   // R0 (return value) = R1
		  BX LR		
		  
PB_clear_edgecp_ASM:
		  LDR R0, =EDGE
		  
		//MOV R0, #0x00000000
			MOV R1, #0xFFFFFFFF
		  STR R1, [R0]
		  BX LR
		  
enable_PB_INT_ASM:
          LDR R1, =INTER // R1 = address interrupt value
		  LDR R2, [R1]   // R2 = interrupt value
		  ORR R2, R0, R2 // OR with R2, store the result to R2
          STR R2, [R1]   // store the new value back to memory
		  BX LR

		  
disable_PB_INT_ASM:
          LDR R1, =INTER // get the address
		  LDR R1, [R1]   // get the interrupt value
		  MVN R2, R0	 // get complement of R0
		  AND R2, R2, R1 // AND with the interrupt value
		  STR R2, [R1]	 // store the new value back to memory
	      BX LR
          
		  .end
