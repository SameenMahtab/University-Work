#include <stdio.h>
#include "./drivers/inc/HEX_displays.h"
#include "./drivers/inc/LEDs.h"
#include "./drivers/inc/slider_switches.h"
#include "./drivers/inc/pushbuttons.h"

int main() {
	while(1){
		write_LEDs_ASM(read_slider_switches_ASM());
		HEX_flood_ASM(HEX4|HEX5);
		HEX_clear_ASM(HEX0|HEX1|HEX2|HEX3);

		/*
		switch(PB_data_is_pressed_ASM(read_PB_data_ASM())){		    // get button number that is pressed
			case 0:
				HEX_write_ASM(HEX0, read_slider_switches_ASM());	// display on HEX 0
				break;
			case 1:
				HEX_write_ASM(HEX1, read_slider_switches_ASM());	// display on HEX 1
				break;
			case 2:
				HEX_write_ASM(HEX2, read_slider_switches_ASM());	// display on HEX 2
				break;
			case 3:
				HEX_write_ASM(HEX3, read_slider_switches_ASM());	// display on HEX 3
				break;	
		}
		*/

		if(PB_data_is_pressed_ASM(read_PB_data_ASM())==0){
		HEX_write_ASM(HEX0, read_slider_switches_ASM());
		continue;
		}
		else if(PB_data_is_pressed_ASM(read_PB_data_ASM())==1){
		HEX_write_ASM(HEX1, read_slider_switches_ASM());
		continue;
		}
		else if(PB_data_is_pressed_ASM(read_PB_data_ASM())==2){
		HEX_write_ASM(HEX2, read_slider_switches_ASM());
		continue;
		}
		else if(PB_data_is_pressed_ASM(read_PB_data_ASM())==3){
		HEX_write_ASM(HEX3, read_slider_switches_ASM());
		continue;
		}
		if (read_slider_switches_ASM() >= 512){
			HEX_clear_ASM(HEX0|HEX1|HEX2|HEX3|HEX4|HEX5);
		}
	}

	return 0;
}
