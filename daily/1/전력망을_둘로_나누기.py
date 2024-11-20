from collections import deque, defaultdict

def solution(n, wires):
    answer = -1
    
    graph = defaultdict(list)
    for v1, v2 in wires:
        graph[v1].append(v2)
        graph[v2].append(v1)
    
    def bfs(s, removed_edge):
        visited = set()
        q = deque([s])
        visited.add(s)
        
        while q:
            cur = q.popleft()
            for neighbor in graph[cur]:
                if (cur, neighbor) == removed_edge or (neighbor, cur) == removed_edge:
                    continue
                    
                if neighbor not in visited:
                    visited.add(neighbor)
                    q.append(neighbor)
        return len(visited)
    
    min_diff = n # 초기 최소 차이를 최대값으로 설정
    
    for wire in wires:
        v1,v2 = wire
        
        # 전선을 끊었을 때의 트리 계산
        size1 = bfs(v1, (v1, v2))
        size2 = n - size1
        diff = abs(size1 - size2)
        min_diff = min(min_diff, diff)
        
    
    return min_diff
