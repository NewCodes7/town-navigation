package com.Hserver.townrouting.domain;

public class Node implements Comparable<Node> {

    private int index;
    private double distance;

    public Node(int index, double distance) {
        this.index = index;
        this.distance = distance;
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