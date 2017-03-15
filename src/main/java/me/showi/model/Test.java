package me.showi.model;
/**
 * @author seamus
 * @date 2017年2月16日 下午5:50:43
 * @description
 */
public class Test {

	public static void main(String[] args) {
		int value = jc1(5);
		System.out.println(value);
	}
	
	public static int jc1(int a){
		return a<=1?1:a*jc1(a-1);
		
	}

	private static int jc(int i) {
		if (i==0||i==1) {
			return i;
		}
		
		int value=i;
		int next = i-1;
		do {
			value = value*next;
			i=i-1;
			next = i-1;
		} while (next-1>=1);
		
		return value;
	}
}

