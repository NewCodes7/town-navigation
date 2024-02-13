import cv2

# 이미지 불러오기
image = cv2.imread('sample5.png')

# 그레이스케일 변환
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# 가우시안 블러 적용
blurred = cv2.GaussianBlur(gray, (5, 5), 0)

# 이진화
ret, binary = cv2.threshold(blurred, 127, 255, cv2.THRESH_BINARY)

# 엣지 검출
edges = cv2.Canny(binary, threshold1=30, threshold2=100)

# HoughCircles 함수 호출 전에 이미지를 전처리합니다.
circles = cv2.HoughCircles(edges, cv2.HOUGH_GRADIENT, 1, 80, param1=30, param2=4, minRadius=5, maxRadius=7)


# circles와 circles2가 None이 아닌지 확인 후에만 반복문을 실행합니다.
if circles is not None:
    for i in circles[0]:
        cv2.circle(image, (int(i[0]), int(i[1])), int(i[2]), (255, 0, 0), 2)

cv2.imshow("Contours", image)
cv2.waitKey(0)
cv2.destroyAllWindows()


# 도로의 외곽선 그리기
src = cv2.imread("sample5.png")  # 원본 이미지
# cv_imshow(src)
dst = src.copy()
gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)

ret, binary = cv2.threshold(gray, 127, 255, cv2.THRESH_BINARY)
binary = cv2.bitwise_not(binary)

contours, hierarchy = cv2.findContours(binary, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_NONE)

for i in range(len(contours)):
    cv2.drawContours(dst, [contours[i]], 0, (0, 0, 0), 2)
    # cv2.putText(dst, str(i), tuple(contours[i][0][0]), cv2.FONT_HERSHEY_COMPLEX, 0.8, (0, 0, 0), 2)
    print(i, hierarchy[0][i])

    # cv2.waitKey(0)

cv2.imshow("Contours", src)
cv2.waitKey(0)
cv2.destroyAllWindows()



# 이제 해야 할 건 원 사이의 간선긋기!!!!!