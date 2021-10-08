#include <stdio.h>

#include "./drivers/inc/int_setup.h"
#include "./drivers/inc/ISRs.h"
#include "./drivers/inc/HEX_displays.h"
#include "./drivers/inc/HPS_TIM.h"
#include "./drivers/inc/pushbuttons.h"
#include "./drivers/inc/address_map_arm.h"
#include "./drivers/inc/VGA.h"
#include "./drivers/inc/ps2_keyboard.h"


int main(){
	
	char* data = NULL;
	int x = 0;
	int y = 0;
	VGA_clear_charbuff_ASM();
	VGA_clear_pixelbuff_ASM();   //clear all the pixel buffer and character buffer
	while(1){
		if (read_PS2_data_ASM(data)){   //use this line to check whether the input data is valid
			VGA_write_byte_ASM(x, y, *data); //if yes, use this line to display the corresponding characters at
            //at corresponding coordinates on the screen
			x += 3;
			if (x >= 79){  //if X reaches the boundary (79) , value of X will be reset and Y increases by 1 
				y ++;
				x = 0;
			}
		}
	}
	
	return 0;
}
