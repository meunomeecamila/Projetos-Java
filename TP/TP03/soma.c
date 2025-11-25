#include <stdio.h>
int somadig(int n){
	if(n<10) return n;
	else return n%10 + somadig(n/10);
}

int main(){
	int n;
	while(scanf("%d",&n)){
		int soma = somadig(n);
		printf("%d\n",soma);
	}

return 0;
}