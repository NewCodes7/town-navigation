import cv2
import json

# 이미지 불러오기
image = cv2.imread('sample5.png')

# 그레이스케일 변환
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 가우시안 블러 적용
blurred = cv2.GaussianBlur(gray, (5, 5), 0)

# 이진화
ret, binary = cv2.threshold(blurred, 117, 255, cv2.THRESH_BINARY)

# 엣지 검출
edges = cv2.Canny(binary, threshold1=10, threshold2=20)

# HoughCircles 함수 호출 전에 이미지를 전처리합니다.
circles = cv2.HoughCircles(edges, cv2.HOUGH_GRADIENT, 1, 60, param1=10, param2=3, minRadius=2, maxRadius=15)

# circles와 circles2가 None이 아닌지 확인 후에만 반복문을 실행합니다.
if circles is not None:
    for i in circles[0]:
        cv2.circle(image, (int(i[0]), int(i[1])), int(i[2]), (255, 0, 0), 2)


all_circle = []

for i in range(len(circles[0])):
    tmp = [int(circles[0][i][0]), int(circles[0][i][1])]
    all_circle.append(tmp)


# original 출발점, connected 도착점 weight 사이 거리값
original = []
connected = []
weight = []


# print(len(all_circle))
# for i in range(len(all_circle)):
#     for j in range(i + 1, len(all_circle)):
#         cv2.line(image, (all_circle[i][0], all_circle[i][1]), (all_circle[j][0], all_circle[j][1]), (0, 0, 255), 3, 8,
#                  0)


# 이미지 위에 하얀색이 포함되어 있는지 확인하는 함수
def has_white(x1, y1, x2, y2):
    # 간선의 시작점과 끝점을 이용하여 직선을 그리고, 직선 상에 흰색 픽셀이 있는지 확인합니다.
    points = cv2.line(image.copy(), (x1, y1), (x2, y2), (255, 255, 255), 1)
    return cv2.countNonZero(cv2.cvtColor(points, cv2.COLOR_BGR2GRAY)) > 0

edges_list = {}


# 이미지 위에 그려진 간선 중 하얀색을 포함하지 않는 간선을 삭제하는 함수
def remove_non_white_edges():
    for i2 in range(len(all_circle)):
        for j in range(len(all_circle)):
            # 간선의 시작점과 끝점 좌표
            x1, y1 = all_circle[i2][0], all_circle[i2][1]
            x2, y2 = all_circle[j][0], all_circle[j][1]

            # 두 원의 중심 사이의 거리 계산
            distance = ((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5

            # 이미지 위에 하얀색을 포함하는지 확인
            # 인접한 원인지 확인하고, 일정 범위 내에 있다면 간선 생성
            if distance <= 130 and has_white(x1, y1, x2, y2):
                # 간선을 딕셔너리에 저장합니다.
                if i2 not in edges_list:
                    edges_list[i2] = []
                if j not in edges_list:
                    edges_list[j] = []
                edges_list[i2].append(j)
                edges_list[j].append(i2)
                cv2.line(image, (x1, y1), (x2, y2), (255, 0, 0), 3)


remove_non_white_edges()

graph_data = {"nodes": all_circle,
              "edges": edges_list}
print(graph_data)

# 노드와 간선 정보를 JSON 파일로 저장
with open('edges_data.json', 'w') as json_file:
    json.dump(graph_data, json_file)

# 이미지 출력
cv2.imshow("Contours", image)
cv2.waitKey(0)
cv2.destroyAllWindows()

# 이제 해야 할 건 원 사이의 간선긋기!!!!!


# 간선 긋는 건 잘했으나.. 비어있는 부분이 있음.
# 깔끔하게 원 검출되면 해결되는 문제임...
# binary까진 깔끔하게 전처리 됨. 문제는 edges?

# 노드 검출 더 명확히
# 간선 검출 더 명확히
# 우선 길찾기 알고리즘 수행하자..!! 전처리 나중에 하자. 우선 한바퀴 도는 게 중요함.