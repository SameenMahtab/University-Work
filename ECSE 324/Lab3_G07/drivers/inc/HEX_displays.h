#ifndef _HEX_DISPLAYS
#define _HEX_DISPLAYS

	typedef enum{
		HEX0 = 0x000000001,
		HEX1 = 0x000000002,
		HEX2 = 0x000000004,
		HEX3 = 0x000000008,
		HEX4 = 0x000000010,
		HEX5 = 0x000000020
	} HEX_t;

	extern void HEX_clear_ASM(HEX_t hex);
	extern void HEX_flood_ASM(HEX_t hex);
	extern void HEX_write_ASM(HEX_t hex, char val);

#endif