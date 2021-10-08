#include <stdio.h>
#include "./drivers/inc/vga.h"
#include "./drivers/inc/pushbuttons.h"
#include "./drivers/inc/slider_switches.h"

void test_char(){
	int x,y;
	char c = 0;

	for(y=0 ; y<=59 ; y++)
		for(x=0 ; x<=79 ; x++)
			VGA_write_char_ASM(x,y,c++);
}

void test_byte(){
	int x,y;
	char c = 0;

	for(y=0 ; y<=59 ; y++)
		for(x=0 ; x<=79 ; x+=3)
			VGA_write_byte_ASM(x,y,c++);
}


void test_pixel(){
	int x,y;
	unsigned short colour = 0;

	for(y=0 ; y<=239 ; y++)
		for(x=0 ; x<=319 ; x++)
			VGA_draw_point_ASM(x,y,colour++);
}

int main(){
	
	
	VGA_clear_charbuff_ASM();
	VGA_clear_pixelbuff_ASM();
	while(1){
		if(read_PB_data_ASM()==1){
			if(read_slider_switches_ASM()>0){
				test_byte();
			}
			else if(read_slider_switches_ASM()==0){
				test_char();
			}
		}
		else if(read_PB_data_ASM()==2){
			test_pixel();
		}
		else if(read_PB_data_ASM()==4){
			VGA_clear_charbuff_ASM();
		}
		else if(read_PB_data_ASM()==8){
			VGA_clear_pixelbuff_ASM();
		}
	}	
	
	
	
