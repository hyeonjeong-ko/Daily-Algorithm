import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import java.io.*;
import java.util.*;

public class 듣보잡 {
	public static void main(String[] args) throws IOException {
		// 입력 처리
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		Set<String> unheard = new HashSet<>();
		for (int i = 0; i < N; i++) {
			unheard.add(br.readLine());
		}

		Set<String> unseen = new HashSet<>();
		for (int i = 0; i < M; i++) {
			unseen.add(br.readLine());
		}

		// 교집합 계산
		unheard.retainAll(unseen); // unheard 에 교집합 저장

		// 결과 정렬
		List<String> result = new ArrayList<>(unheard);
		Collections.sort(result);


		// 출력
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		bw.write(result.size() + "\n");
		for (String name : result) {
			bw.write(name + "\n");
		}
		bw.flush();
		bw.close();
	}
}

// 투 포인터 풀이 ======================================================================
public class 듣보잡_투포인터 {
	public static void main(String[] args) throws IOException {
		// 입력 처리
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		String[] A = new String[N];
		String[] B = new String[M];

		for (int i = 0; i < N; i++) {
			A[i] = br.readLine();
		}
		for (int i = 0; i < M; i++) { // **오류 수정: `i < N` → `i < M`**
			B[i] = br.readLine();
		}

		// 투포인터를 위한 사전 정렬
		Arrays.sort(A);
		Arrays.sort(B);

		// 투포인터 알고리즘 적용
		int A_index = 0;
		int B_index = 0;
		int count = 0; // 교집합의 수를 세는 변수
		StringBuilder sb = new StringBuilder(); // 결과 저장용

		while (A_index < N && B_index < M) {
			String A_str = A[A_index];
			String B_str = B[B_index];

			if (A_str.equals(B_str)) { // 두 문자열이 같으면 교집합에 추가
				count++;
				sb.append(A_str).append("\n");
				A_index++;
				B_index++;
			} else if (A_str.compareTo(B_str) < 0) { // A가 사전순으로 더 앞이면 A의 포인터 이동
				A_index++;
			} else { // B가 사전순으로 더 앞이면 B의 포인터 이동
				B_index++;
			}
		}

		// 결과 출력
		bw.write(count + "\n"); // 교집합의 크기 출력
		bw.write(sb.toString()); // 교집합 명단 출력
		bw.flush();

		// 리소스 정리
		br.close();
		bw.close();
	}
}
