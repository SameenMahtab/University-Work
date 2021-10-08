#include <stdio.h>

#include "./drivers/inc/int_setup.h"
#include "./drivers/inc/ISRs.h"
#include "./drivers/inc/HEX_displays.h"
#include "./drivers/inc/HPS_TIM.h"
#include "./drivers/inc/pushbuttons.h"
#include "./drivers/inc/address_map_arm.h"
#include "./drivers/inc/VGA.h"
#include "./drivers/inc/audio.h"


int main(){
	
	int x = 0;
	int y = 0;
		
    
    
	while(1){
		while(x < 240){ // 48K / 100 Hz / 2
			if (AUDIO_ASM(0x00FFFFFF))
				x ++;
		}
		while(y < 240){
			if (AUDIO_ASM(0x00000000))
				y ++;
		}
		
		x = 0;
		y = 0;
	}

	return 0;
}
