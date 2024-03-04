import cv2
import json

def detect_circles(image_path):
    # 이미지 불러오기
    image = cv2.imread(image_path)

    # 그레이스케일 변환
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    # 가우시안 블러 적용
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)

    # 이진화
    ret, binary = cv2.threshold(blurred, 117, 255, cv2.THRESH_BINARY)

    # 엣지 검출
    edges = cv2.Canny(binary, threshold1=10, threshold2=20)

    # 원 검출
    circles = cv2.HoughCircles(edges, cv2.HOUGH_GRADIENT, 1, 60, param1=10, param2=3, minRadius=2, maxRadius=15)

    return circles

# Node(원) 리스트 저장
def extract_circle_centers(image, circles):
    all_circle = []

    # 감지된 원의 정보에서 원의 중심 좌표를 추출하여 정수형으로 변환한 후 리스트에 추가
    for i in range(len(circles[0])):
        center_x = int(circles[0][i][0])
        center_y = int(circles[0][i][1])
        r = int(circles[0][i][2])
        tmp = [center_x, center_y]
        all_circle.append(tmp)
        cv2.circle(image, (center_x, center_y), r, (255, 0, 0), 2)

    return all_circle

# 간선 검출: 이미지 위에 하얀색이 포함되어 있는지 확인하는 함수
def has_white(image, x1, y1, x2, y2):
    # 간선의 시작점과 끝점을 이용하여 직선을 그리고, 직선 상에 흰색 픽셀이 있는지 확인합니다.
    points = cv2.line(image.copy(), (x1, y1), (x2, y2), (255, 255, 255), 1)
    return cv2.countNonZero(cv2.cvtColor(points, cv2.COLOR_BGR2GRAY)) > 0

# 간선 검출: 간선 저장 함수
def get_edges(image, all_circle):
    edges_list = {}

    for i in range(len(all_circle)):
        for j in range(i+1, len(all_circle)):
            # 간선의 시작점과 끝점 좌표
            x1, y1 = all_circle[i][0], all_circle[i][1]
            x2, y2 = all_circle[j][0], all_circle[j][1]

            # 두 원의 중심 사이의 거리 계산
            distance = round(((x1 - x2) ** 2 + (y1 - y2) ** 2) ** 0.5, 2)

            # 이미지 위에 하얀색을 포함하는지 확인
            # 인접한 원인지 확인하고, 일정 범위 내에 있다면 간선 생성
            if distance <= 130 and has_white(image, x1, y1, x2, y2):
                # 간선 리스트 저장
                if i not in edges_list:
                    edges_list[i] = []
                if j not in edges_list:
                    edges_list[j] = []
                edges_list[i].append([j, distance])
                edges_list[j].append([i, distance])
                cv2.line(image, (x1, y1), (x2, y2), (255, 0, 0), 3)

    return edges_list


image_path = 'sample5.png'
image = cv2.imread(image_path)
circles = detect_circles(image_path)

all_circle = extract_circle_centers(image, circles)
edges_list = get_edges(image, all_circle)

# Node 저장
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