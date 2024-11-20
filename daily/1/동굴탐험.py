from collections import defaultdict, deque


def solution(n, path, order):
    # 인접리스트 생성
    graph = defaultdict(list)
    for a, b in path:
        graph[a].append(b)
        graph[b].append(a)

    # 선행 조건 매핑
    prerequisites = {}  # post:prev
    for pre, post in order:
        prerequisites[post] = pre

    # 방문 상태 추적
    visited = [False] * n

    waiting = defaultdict(list)  # idx: [idx를 사전에 방문해야하는 노드들]

    # BFS
    q = deque()

    # 0번 방 방문
    if 0 in prerequisites:
        # 0번 방을 방문하기 전에 선행 조건이 있다면 불가능
        return False
    q.append(0)
    visited[0] = True

    while q:
        cur = q.popleft()

        # 탐색중인 현재 방이 선행조건에 맞는다면, 대기 리스트에 있는 방 탐색가능
        if cur in waiting:
            for post in waiting[cur]:
                q.append(post)
                visited[post] = True
            del waiting[cur]

        # 인접 방 탐색
        for neighbor in graph[cur]:
            if visited[neighbor]:
                continue
            if neighbor in prerequisites:
                # 인접 방(neighbor)이 사전에 방문해야하는 노드가 있을때
                prev = prerequisites[neighbor]
                if not visited[prev]:
                    # 선행 조건이 아직 충족되지 않음, 대기 리스트에 추가
                    waiting[prev].append(neighbor)
                else:
                    # 선행 조건이 충족되었으므로 방문 가능
                    q.append(neighbor)
                    visited[neighbor] = True
            else:
                # 선행 조건 없다면 바로 방문
                q.append(neighbor)
                visited[neighbor] = True
    return all(visited)  # 모든 방 방문했는지 확인
