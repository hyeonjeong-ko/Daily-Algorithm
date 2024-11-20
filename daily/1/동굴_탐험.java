import java.util.*;

public class 동굴_탐험 {
	public boolean solution(int n, int[][] path, int[][] order) {
		// 1. 인접 리스트 생성 (그래프)
		List<Integer>[] graph = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
		}
		for (int[] p : path) {
			int a = p[0];
			int b = p[1];
			graph[a].add(b);
			graph[b].add(a);
		}

		// 2. 선행 조건 매핑 (post -> pre)
		// 기본값은 -1로 설정하여 선행 조건이 없는 방을 나타냄
		int[] prerequisites = new int[n];
		Arrays.fill(prerequisites, -1);
		for (int[] o : order) {
			int pre = o[0];
			int post = o[1];
			prerequisites[post] = pre;
		}

		// 3. 방문 상태 추적
		boolean[] visited = new boolean[n];

		// 4. 대기 리스트 초기화 (pre -> List<post>)
		List<Integer>[] waiting = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			waiting[i] = new ArrayList<>();
		}

		// 5. BFS 큐 초기화
		Queue<Integer> queue = new LinkedList<>();

		// 6. 0번 방의 선행 조건 확인
		if (prerequisites[0] != -1) {
			// 0번 방을 방문하기 전에 선행 조건이 있다면 불가능
			return false;
		}
		queue.offer(0);
		visited[0] = true;

		// 7. BFS 탐색 수행
		while (!queue.isEmpty()) {
			int current = queue.poll();

			// 현재 방이 선행 조건을 충족시키는 방이라면, 대기 리스트에 있는 방들을 방문 가능
			for (int post : waiting[current]) {
				if (!visited[post]) {
					queue.offer(post);
					visited[post] = true;
				}
			}
			// 대기 리스트 비우기
			waiting[current].clear();

			// 인접 방 탐색
			for (int neighbor : graph[current]) {
				if (visited[neighbor]) {
					continue;
				}
				if (prerequisites[neighbor] == -1) {
					// 선행 조건이 없으므로 바로 방문
					queue.offer(neighbor);
					visited[neighbor] = true;
				} else {
					int pre = prerequisites[neighbor];
					if (visited[pre]) {
						// 선행 조건이 이미 충족되었으므로 방문 가능
						queue.offer(neighbor);
						visited[neighbor] = true;
					} else {
						// 선행 조건이 아직 충족되지 않음, 대기 리스트에 추가
						waiting[pre].add(neighbor);
					}
				}
			}
		}

		// 8. 모든 방을 방문했는지 확인
		for (boolean v : visited) {
			if (!v) {
				return false;
			}
		}
		return true;
	}
}
