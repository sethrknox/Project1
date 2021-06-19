package com.revature.app;

import java.util.Scanner;

public class TemporaryDriver {

	private static Scanner s = new Scanner(System.in);
	
	public static void main(String[] args) {
		String S = "ngyzvavrtnjfwzxbhmczw";
		int a = 0;
		int b = S.length();
		int c = S.length();
		boolean check = true;

		while (c > 1) {
			String sub = S.substring(a,b);
			check = true;
			for (int i = a; i < b-1; i++) {
				for (int j = i+1; j < b; j++) {
					if (S.charAt(i) == S.charAt(j)) {
						check = false;
						i = b;
						j = b;
					}
				}
			}

			if (check == true) {
				System.out.println(c);
				return;
			}
			if (b == S.length()) {
				a = 0;
				c--;
				b = c;
			} else {
				a++;
				b++;
			}
		}
		// never found a substring more than one character
		System.out.println(1);
	}
}
