		.text
		.equ PS2_DATA, 0xFF200100
		.global read_PS2_data_ASM
		
read_PS2_data_ASM:
		MOV R1, #1        // R1 has 1
		LSL R1, R1, #15   // prepare R1 for TST
		LDR R2, =PS2_DATA // R2 has the address of PS2_Data
		LDR R2, [R2]      // R2 stores the content in PS2_DATA
		TST R1, R2        // check if the 16th bit is 0 (RVALID)
		BEQ INVALID       // if is not valid, go to INVALID
VALID:	STR R2, [R0]      // store the data into the target address
		MOV R0, #1        // and return 1
		BX LR
INVALID:MOV R0, #0        // simply return 0
		BX LR

		.end
		
