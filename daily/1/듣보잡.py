# set 교집합

N, M = map(int, input().split())

set1 = set()
for _ in range(N):
    set1.add(input())

set2 = set()
for _ in range(M):
    set2.add(input())

set3 = set1 & set2
li = list(set3)
li.sort()

print(len(li))
for i in li:
    print(i)
