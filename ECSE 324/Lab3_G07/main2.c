#include <stdio.h>


#include "./drivers/inc/HEX_displays.h"
#include "./drivers/inc/HPS_TIM.h"
#include "./drivers/inc/pushbuttons.h"

int main(){
		HPS_TIM_config_t hps_tim;
		hps_tim.tim = TIM0;
		hps_tim.timeout = 10000;  //10^-6  *10000=10^-2   10millisceond   //timeout struct element is given in microseconds
		hps_tim.LD_en = 1;
		hps_tim.INT_en = 1;
		hps_tim.enable = 1;
		HPS_TIM_config_ASM(&hps_tim); 

		HPS_TIM_config_t hps_tim_pb;
		hps_tim_pb.tim = TIM1;
		hps_tim_pb.timeout = 1000;
		hps_tim_pb.LD_en = 1;
		hps_tim_pb.INT_en = 1;
		hps_tim_pb.enable = 1;
		HPS_TIM_config_ASM(&hps_tim_pb);

		HEX_write_ASM(HEX0|HEX1|HEX2|HEX3|HEX4|HEX5, 0); //set all to 0
		//int push_buttons = 0;
		int ms = 0;
		int sec = 0;
		int min = 0;
		int time = 0; 
	
		while(1) {
			if (HPS_TIM_read_INT_ASM(TIM0) && time) {
				HPS_TIM_clear_INT_ASM(TIM0);
				ms += 10; //Timer is for 10 milliseconds
	
				//range for each time
				if (ms >= 1000) {
					ms -= 1000;  //ms=ms-1000
					sec++;	
					if (sec >= 60) {
						sec -= 60;
						min++;
						if (min >= 60) {
							min = 0;
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

			if (HPS_TIM_read_INT_ASM(TIM1)) { //Timer to read push buttons
				HPS_TIM_clear_INT_ASM(TIM1);

				if (PB_data_is_pressed_ASM(read_PB_data_ASM()) == 0) { //Start timer
					time = 1;
				} if (PB_data_is_pressed_ASM(read_PB_data_ASM()) == 1) { //Stop timer
					time = 0;
				} if (PB_data_is_pressed_ASM(read_PB_data_ASM()) == 2) { //Reset timer
					ms = 0;
					sec = 0;
					min = 0;
					time = 0; //Stop timer
					PB_clear_edgecp_ASM(PB1);
					//Set every number to 0
					HEX_write_ASM(HEX0 | HEX1 | HEX2 | HEX3 | HEX4 | HEX5, 0);

				}
			}
		}	return 0;
}

