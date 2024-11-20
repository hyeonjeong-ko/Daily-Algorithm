import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class 전력망을_둘로_나누기 {
	static List<Integer>[] adj;
	static boolean[] visited;
	public int solution(int n, int[][] wires) {
		int answer = n;

		// 1. 트리를 인접리스트로 표현
		adj = new ArrayList[n];
		for (int i = 0; i <= n; i++) {
			adj[i] = new ArrayList<>();
		}
		for(int[] wire : wires) {
			int v1 = wire[0];
			int v2 = wire[1];
			adj[v1].add(v2);
			adj[v2].add(v1);
		}

		// 2. 모든 전선을 하나씩 끊어보기
		for (int[] wire : wires) {
			int v1 = wire[0];
			int v2 = wire[1];

			// 끊은 전선을 기준으로 두 트리 크기 계산
			visited = new boolean[n + 1];
			int size1 = dfs(v1, v2, visited);
			int size2 = n - size1;
			int diff = Math.abs(size1 - size2);

			if (diff < answer) answer = diff;
		}

		return answer;
	}

	private int dfs(int v1, int v2, boolean[] visited) {
		visited[v1] = true;
		int count = 1;

		for (int neighbor : adj[v1]) {
			if (neighbor == v2) continue;
			if (!visited[neighbor])
				count += dfs(neighbor, v2, visited);
		}
		return count;
	}


	// BFS 함수: current 노드에서 시작하여, ignore 노드와의 연결을 무시하고 탐색
	private int bfs(int start, int ignore, List<Integer>[] adj, boolean[] visited) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(start);
		visited[start] = true;
		int count = 0; // 트리의 크기

		while(!queue.isEmpty()) {
			int current = queue.poll();
			count++;

			for(int neighbor : adj[current]) {
				// 끊은 전선 (current <-> ignore)을 무시
				if((current == ignore && neighbor == start) || (current == start && neighbor == ignore)) {
					continue;
				}
				if(!visited[neighbor]) {
					visited[neighbor] = true;
					queue.offer(neighbor);
				}
			}
		}

		return count;
	}
}
