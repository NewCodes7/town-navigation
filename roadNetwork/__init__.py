import cv2
import numpy as np
import json

# 이미지 불러오기
image = cv2.imread('sample4.png')

# 그레이스케일로 변환
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 가우시안 블러 적용
blurred = cv2.GaussianBlur(gray, (5, 5), 0)


# Canny 엣지 검출 수행
edges = cv2.Canny(blurred, threshold1=1, threshold2=30)
# 엣지를 선분으로 간주함. 두 번째 파라미터는 엣지를 검출하는 정도. 세 번째 파라미터는 각 엣지를 이어주는 정도.

# 허프 변환을 사용하여 선분 검출
lines = cv2.HoughLinesP(edges, 1, np.pi / 180, threshold=10, minLineLength=1, maxLineGap=4)
# 값을 최대한 낮춰서 우선 해결 (향후 최적화 해야 함)

# 노드와 간선 정보 저장을 위한 리스트
nodes = []
edges_list = []

# 각 선분에 대해 노드와 간선 추출
for line in lines:
    x1, y1, x2, y2 = line[0]

    # 노드 추가
    nodes.append((int(x1), int(y1)))
    nodes.append((int(x2), int(y2)))

    # 간선 추가
    edges_list.append([(int(x1), int(y1)), (int(x2), int(y2))])

# 노드와 간선 출력
print("Nodes:", nodes)
print("Edges:", edges_list)
print("Number of nodes:", len(nodes))
print("Number of edges:", len(edges_list))

# 이미지에 노드와 간선 그리기
for node in nodes:
    cv2.circle(image, node, 5, (0, 0, 255), -1)

for edge in edges_list:
    cv2.line(image, edge[0], edge[1], (0, 255, 0), 2)


# 이미지에서 추출한 노드와 간선 정보 저장을 위한 딕셔너리
graph_data = {"nodes": nodes,
              "edges": edges_list}
print(graph_data)

# 노드와 간선 정보를 JSON 파일로 저장
with open('graph_data.json', 'w') as json_file:
    json.dump(graph_data, json_file)

# 이미지 표시
cv2.imshow('Image with Nodes and Edges', image)
cv2.waitKey(0)
cv2.destroyAllWindows()

