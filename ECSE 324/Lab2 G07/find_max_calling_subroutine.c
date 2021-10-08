	
extern int MAX_2(int x, int y) ;
int main () 
 {
		int a[5]={1,20,3,4,5};
		int current_val= a[0];
		int i;
		for (i=1; i<5;i++) {
		current_val=MAX_2 (current_val, a[i]);
		}
		return current_val; 

}
