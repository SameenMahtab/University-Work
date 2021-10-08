.text
.equ LEDs_BASE, 0xFF200000
.global read_LEDs_ASM, write_LEDs_ASM

read_LEDs_ASM:
	LDR R1, =LEDs_BASE
	LDR R0, [R1]
	BX LR

write_LEDs_ASM:
	LDR R1, =LEDs_BASE
	STR R0, [R1]
	BX LR


	.end
