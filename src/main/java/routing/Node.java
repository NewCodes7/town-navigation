package routing;

class Node implements Comparable<Node> {

    private int index;
    private double distance;
    private int previous; // 이전 노드(부모 노드)를 기록하기 위한 변수

    public Node(int index, double distance) {
        this.index = index;
        this.distance = distance;
        this.previous = -1;
    }

    public void setPrevious(int c) {
        this.previous = c;
    }

    public int getPrevious() {
        return this.previous;
    }

    public int getIndex() {
        return this.index;
    }

    public double getDistance() {
        return this.distance;
    }

    // 거리(비용)가 짧은 것이 높은 우선순위를 가지도록 설정
    @Override
    public int compareTo(Node other) {
        if (this.distance < other.distance) {
            return -1;
        }
        return 1;
    }
}
