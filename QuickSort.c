#include <stdio.h>

void Quick_sort (int left , int right , int a[] )  //small -> big 
{
	if ( left>=right )
		return ;
	int i = left ;
	int j = right ;
	int key = a[left] ;
	while ( i < j ) {
		while ( i<j && key>=a[j] ) {
			j--;
		}
		a[i] = a[j] ; 
		while ( i<j && key<=a[i] ) {
			i++;
		}
		a[j] = a[i];
	}
	a[i] = key ;
	Quick_sort ( left , i-1 , a );
	Quick_sort ( i+1 , right , a );
}

int main(void)
{
	int n ;
	scanf("%d" , &n);
	int i , m[100];
	for ( i=0 ; i<n ; i++) {
		scanf("%d" , &m[i]);
	}
	Quick_sort( 0 , n-1 , m );
	for ( i=0 ; i<n ; i++ ) {
		printf("%d " , m[i] );
	}
	return (0);
} 
