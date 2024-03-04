package routing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Navigator {
    private static List<List<Node>> graph = new ArrayList<>();
    public static final int INF = (int) 1e9;
    public static int n, m, start;
    public static double[] d;
    public static int[] prev;

    Navigator(List<List<Node>> graph) {
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

// 간선 정보에서 비용 정보도 얻어와야 함.
// 인접 리스트로 간선 정보 담아야 할 듯.

// 애초에 node 좌표를 가져오는 게 아니라 index를 가져왔어야 함.
// 다익스트라 알고리즘에서는 좌표에 대한 자세한 정보가 필요하지 않다. 그저 노드의 인덱스랑 간선 정보 담은 그래프만 있으면 돼.

