package com.Hserver.townrouting.service;

import com.Hserver.townrouting.domain.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import org.springframework.stereotype.Service;

@Service
public class DijkstraService {
    private static List<List<Node>> graph = new ArrayList<>();
    public static final int INF = (int) 1e9;
    public static int n, m, start;
    public static double[] d;
    public static int[] prev;

    public DijkstraService(List<List<Node>> graph) {
        this.graph = graph;
    }

    public static void dijkstra(int start, int end) {
        int V = graph.size();
        d = new double[V];
        prev = new int[V];
        Arrays.fill(prev, -1);
        Arrays.fill(d, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));
        d[start] = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            double currentDist = node.getDistance();
            int now = node.getIndex();

            if (d[now] < currentDist) continue;

            // 현재 노드와 연결된 다른 인접한 노드들을 확인
            for (Node neighbor : graph.get(now)) {
                double cost = d[now] + neighbor.getDistance();
                // 현재 노드를 거쳐서, 다른 노드로 이동하는 거리가 더 짧은 경우
                if (cost < d[neighbor.getIndex()]) {
                    d[neighbor.getIndex()] = cost;
                    prev[neighbor.getIndex()] = now;
                    pq.offer(new Node(neighbor.getIndex(), cost));
                }
            }
        }
        System.out.println(d[4]);
        System.out.println(d[5]);
        System.out.println(d[6]);
        System.out.println(d[7]);
        System.out.println(d[100]);


        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        Collections.reverse(path);
        System.out.println("Shortest Path: " + path);
        System.out.println("Shortest Distance: " + d[end]);
    }
}
