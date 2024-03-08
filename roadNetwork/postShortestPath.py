import requests
import json
import cv2

# JSON 파일 경로 설정
file_path = "edges_data.json"

# 파일 열기
with open(file_path, "r") as json_file:
    # JSON 파일을 파이썬 객체로 로드
    data = json.load(json_file)

# 각 노드를 인덱스를 기반으로 딕셔너리로 저장
nodes_dict = {}
for i, node in enumerate(data["nodes"]):
    nodes_dict[i] = node


# 요청을 보낼 URL 설정
url = 'http://localhost:8080/api/navigation/shortest-path'

# 보낼 데이터 설정 (JSON 형식)
data = {
  "start": 1,
  "end": 150
}
# POST 요청 보내기
response = requests.post(url, json=data)

# 응답 확인
if response.status_code == 200:
    print('요청 성공:', response.json())
else:
    print('요청 실패:', response.status_code)


image = cv2.imread('sample5.png')

path = response.json()
dist = path.pop()

before = -1
for index in path:
    point = nodes_dict.get(index)
    cv2.circle(image, point, 5, (0, 0, 255), -1)  # 빨간색으로 점 그리기
    if before != -1:
        cv2.line(image, before, point, (0, 0, 0), 3)
    before = point

# 이미지 화면에 표시
cv2.imshow("Image with points", image)
cv2.waitKey(0)
cv2.destroyAllWindows()

# 이미지 저장
cv2.imwrite("image_with_points.jpg", image)