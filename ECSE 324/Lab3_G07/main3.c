#include <stdio.h>

#include "./drivers/inc/HEX_displays.h"
#include "./drivers/inc/HPS_TIM.h"
#include "./drivers/inc/int_setup.h"
#include "./drivers/inc/ISRs.h"
#include "./drivers/inc/pushbuttons.h"

int main(){
	// set up timer and button interrupt
	int_setup(2, (int []){199, 73}); // 199 for reset, 73 for insert key
	enable_PB_INT_ASM(PB0|PB1|PB2);

	HPS_TIM_config_t hps_tim;
	hps_tim.tim = TIM0;
	hps_tim.timeout = 10000;   //timeout struct element is given in microseconds
	hps_tim.LD_en = 1;
	hps_tim.INT_en = 1;
	hps_tim.enable = 1;
	HPS_TIM_config_ASM(&hps_tim);
	

	
	int ms = 0;
	int sec = 0;
	int min = 0;
	int time = 0; 

	while(1){		
if(button_interrupt_flag == 0){	
			time = 1;
		}
		if(button_interrupt_flag == 1){	
			time = 0;
		}
		if(button_interrupt_flag == 2){
 			ms = 0;
            sec = 0;
            min = 0;
            time = 1;
            HEX_write_ASM(HEX0 | HEX1 | HEX2 | HEX3 | HEX4 | HEX5, 0);
}

		if(time){	
	if(hps_tim0_int_flag){	
		hps_tim0_int_flag = 0;	//make sure same or other signal does not cause further interrupts 
						
				ms += 10; //Timer is for 10 milliseconds
	
				//range for each time
				if (ms >= 1000) {
					ms -= 1000; 
					sec++;	
					if (sec >= 60) {
						sec -= 60;
						min++;
						if (min >= 60) {
							min = 0;
						}
					}
				}
			}
				//convert input to char
				HEX_write_ASM(HEX0, ((ms % 100)/10));		
				HEX_write_ASM(HEX1, (ms /100));
				HEX_write_ASM(HEX2, (sec % 10));
				HEX_write_ASM(HEX3, (sec / 10));
				HEX_write_ASM(HEX4, (min % 10));
				HEX_write_ASM(HEX5, (min / 10));
			}
		}


	return 0;
}
