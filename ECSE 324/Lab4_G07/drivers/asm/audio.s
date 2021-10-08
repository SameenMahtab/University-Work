		.text
		.equ CONTROL, 0xFF203040
		.equ FIFOSPACE, 0xFF203044
		.equ LEFTDATA, 0xFF203048
		.equ RIGHTDATA, 0xFF20304C
		.global AUDIO_ASM
	
AUDIO_ASM:
		LDR R1, =CONTROL
		LDR R2, =FIFOSPACE
		LDR R3, =LEFTDATA
		LDR R4, =RIGHTDATA // load all the relevant addresses
		LDR R5, [R2]       // R5 stores the address of Fifospace
		LSR R5, R5, #16    // R5 stores the address of WSLC and WSRC  //should be LSL
		CMP R5, #0         // compare with 0    //check the bit field of WSLC and WSRC
        //check whether the bit 16 to 31 is 0
        //if they are all 0, No space, there's no words to be used , return 0 
		BGT CHECKLEFT      // if there is space in WSRC, check WSLC
END:	MOV R0, #0         // if not, simply return 0
		BX LR
CHECKLEFT:
		LSR R5, R5, #8     // R5 stores the address of WSLC
		CMP R5, #0         // check if there is space in WSLC
		BEQ END            // if not, simply return 0
		STR R0, [R3]       // else, write data into Leftdata
		STR R0, [R4]       // also, write data into Rightdata
		MOV R0, #1         // at last, return 1
		BX LR

		.end
	
