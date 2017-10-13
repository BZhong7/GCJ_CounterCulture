//Brandon Zhong - CISC 3150 Summer 2017
//Google Code Jam - Counter Culture

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CounterCulture {
	
	public static long flipIt(long num) {
		StringBuilder origAns = new StringBuilder();
		long newAnswer;
		String flippedNum;
		
		origAns.append(num);
		origAns.reverse();
		flippedNum = origAns.toString();		
		newAnswer = Long.valueOf(flippedNum);
		
		return newAnswer;
	}
	
	public static String flipIt(String num) {
		StringBuilder origAns = new StringBuilder();
		String flippedNum;
		
		origAns.append(num);
		origAns.reverse();
		flippedNum = origAns.toString();		
		
		return flippedNum;
	}
	
	public static boolean isOneFollowedByZeros(String num) {
		int numDigits = num.length();
		
		if(num.charAt(0) != '1')
			return false;
		
		for(int i = 1; i < numDigits; i++) {
			if(num.charAt(i) != '0')
				return false;
		}
			
		return true;
	}
	
	public static boolean isAllZeros(String num) {
		int numDigits = num.length();
		
		for(int i = 0; i < numDigits; i++) {
			if(num.charAt(i) != '0')
				return false;
		}
		
		return true;
	}
	
	public static long solveCounter(long number, long[] powTen) {
		long answer = 1L; //How many numbers have been counted.
		long N = 1L;	//the current number the function is on.
		String stringNum; //target number as a String.
		
		stringNum = Long.toString(number);		
		int numDigits = stringNum.length();
		
		//For any number below 21, best to count directly to it.
		if(number < 21)
			return Long.valueOf(stringNum);
		
		//Quickly counts up to a power of ten with the same number of digits as numDigits.
		for(int i = 1; i < numDigits; i++) {
			N += powTen[(i + 1) / 2] - 1;
			answer += powTen[(i + 1) / 2] - 1;

			if(i > 1) {
				N = flipIt(N);
				answer++;
			}
			
			N += powTen[i / 2] - 1;
			answer += powTen[i / 2] - 1;
		}
		
		//Exceptional case; If target number is a power of ten, no more counting is needed.
		if(number == N)
			return answer;
		
		String leftHalf = stringNum.substring(0, numDigits/2);
		String rightHalf = stringNum.substring(numDigits/2);
					
		if(isAllZeros(rightHalf)) {
			int tempIntLeftHalf = Integer.valueOf(leftHalf);
			tempIntLeftHalf--;
			String tempLeftHalf = String.valueOf(tempIntLeftHalf); //leftHalf - 1;
			
			//If true, just count up to target number.
			if(isOneFollowedByZeros(tempLeftHalf)) {
				answer += (number - N);
				
				return answer;
			}
			//Count up to (reversed)leftHalf - 1, flip the number, then count up to target number.
			else {
				N += flipIt((Long.valueOf((leftHalf)) - 1));
				answer += flipIt((Long.valueOf((leftHalf)) - 1));
				N = flipIt(N);
				answer++;			
				answer += (number - N);

				return answer;
			}
		}
		//If true, count up to target number.
		else if(isOneFollowedByZeros(leftHalf)) {
			answer += Long.valueOf(rightHalf);
			
			return answer;
		}
		
		//Count up to (reversed)leftHalf, flip the number, then count to target number.
		answer += Long.valueOf(flipIt(leftHalf));
		answer++;		
		answer += (Long.valueOf(rightHalf) - 1);
				
		return answer;
	}
	
	public static void main(String arg[]) {
		int T; //Number of test cases to go through.
		long number; //Number to count up to.
		int countTestCase = 1; //Current test case.
		long answer;
		
		try {
			Scanner cin = new Scanner(new File("A-large-practice.in"));
			FileWriter file = new FileWriter("CounterCulture-large.txt");
			BufferedWriter cout = new BufferedWriter(file);
			T = cin.nextInt();
			cin.nextLine();
			
			long[] powTen = new long[14];
			powTen[0] = 1L;
			for(int i = 1; i < 14; i++)
				powTen[i] = powTen[i-1] * 10;
			
			while(countTestCase <= T) {
				number = cin.nextLong();
				
				
				answer = solveCounter(number, powTen);

				cout.write("Case #" + countTestCase + ": " + answer);
				cout.newLine();
				countTestCase++;
			}
			cin.close();
			cout.close();
		}
		catch (IOException e) {
			System.err.println("Problem finding/writing file.");
		}
	}
}
