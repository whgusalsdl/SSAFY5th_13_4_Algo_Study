package week28.BOJ__1038_G5_감소하는수;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
	
	/* N 번쨰 수*/
	static int N;
	
	/* A[i][j] : j로 시작하는 (i+1)개의 길이를 가진 수의 갯수 */
	static int[][] A = new int[10][10];
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		
		/* 1023번째 이상부터는 0~9로 표현할 수 있는 감소하는 수가 존재하지 않음. */
		if(N >= 1023) System.out.println(-1);
		/* 1022번째 수는 유일하게 10개의 자리를 가지는 수 */
		else if(N==1022) System.out.println("9876543210");
		/* 그 외는 구할 수 있음. */
		else solve();
	}
	
	static void  solve() {
		
		/* 1개의 길이를 가진 수들은 1개씩 존재 (0~9) */
		for(int i=0; i<10; i++) {
			A[0][i] = 1;
		}
		
		/* 각 자릿수들의 범위 */
		int s = 0;
		for(int i=1; i<=10; i++) {
			
			A[i][i] = 1;
			
			int cnt = 1;
			
			for(int j=i+1; j<10; j++) {
				
				cnt += A[i-1][j-1];
				A[i][j] = cnt;
			}
			
			cnt += A[i-1][9];

//			System.out.println(i + "번째 자리수는 총 " + cnt + " 개이며,  범위는 " + s + " ~ " +  (s+cnt-1) + "입니다");
			
			// N번째 범위가 속한다면
			if(s <= N && N <= (s+cnt-1)) {
				System.out.println(find(i, s));
				break;
			}
			
			s += cnt;
		}
	}
	/**
	 * 
	 * @param len : 몇자리수인지
	 * @param start : 시작 범위
	 * @return N번째 감소하는 수
	 */
	static long find(int len, int start) {
		
		/* EX) N=18일때 len=2, start=10
		 * 수의 길이가 2이고, 2의 길이를 가진 수의 시작번호는 10번째임.
		 * 
		 * 따라서, 18번째 감소하는수는 2의 길이를 가진 수들 중 cnt=(18-10+1)=9번째 수이다. 
		 * 
		 * 
		 * A[][] =		1	1	1	1	1	1	1	1	1	1
		 * 				x	1	2	3	4	5	6	7	8	9
		 * 				x	x	1	3	6	10	15	21	28	36
		 * ------------------------------------------------------
		 * 
		 * [ 2번째 자리수 구하기 ]
		 * 2의 길이를 가진 수의 9번째 수는 4가 되는데, 그 이유는
		 * A[1][1] + A[1][2] + A[1][3] = 1 + 2 + 3 = 6
		 * A[1][1] + A[1][2] + A[1][3] + A[1][4] = 1 + 2 + 3 + 4
		 * 2의 길이를 가진 수들중 
		 * 		3으로 시작하는 수들중 제일 마지막 번호가 6번째이고,
		 *  	4로 시작하는 수들중 제일 마지막 번호가 10번째이다.
		 *  즉 9번째 수는 6 ~ 10 사이에 존재하기 때문에 맨 앞에자리가 3보다 큰 4임을 알 수 있다.
		 * 
		 * [ 1번째 자리수 구하기 ]
		 * (2의 길이를 가진 수의 번호) - (앞자리가 3인 숫자의 마지막 번호) = 9-6 = 3
		 * => 즉 1번째 자릿수는 길이가 1인 수들 중 3번째 수를 구하면 된다.
		 * 
		 * A[0][0] + A[0][1] + A[0][2] = 1 + 1 + 1 = 3
		 * 1의 길이를 가진 수들중 3번째 수는 2이다.
		 * 
		 * 따라서 답은 42이다.
		 *  */
		
		
		
		/* 몇 번째수 */
		int cnt = N-start+1;
		
		long result = 0;
		
		/* len 자리의 수를 맨앞자리 수부터 찾는다. */
		for(int i=len; i>0; i--) {
			
			/* j 번째 수 */
			int j=0;
			int sum=0;
			for(j=i-1; j<10; j++) {
				sum += A[i-1][j];
				
				if(cnt <= sum) break;
			}
			
			/* i번째 자리의 수는 j*/
			result *= 10;	// 자리하나 만들고
			result += j;	// j넣기
			
			// 그 다음 자릿수가 몇번째 수인지 계산
			cnt -= sum-A[i-1][j];
		}

		return result;
	}

}
