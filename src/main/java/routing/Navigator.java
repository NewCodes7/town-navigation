package routing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Navigator {
    private static List<List<Node>> graph = new ArrayList<>();

    public static final int INF = (int) 1e9; // 무한을 의미하는 값으로 10억을 설정
    // 노드의 개수(N), 간선의 개수(M), 시작 노드 번호(Start)
    // 노드의 개수는 최대 100,000개라고 가정
    public static int n, m, start;
    // 각 노드에 연결되어 있는 노드에 대한 정보를 담는 배열
    // 최단 거리 테이블 만들기
    public static int[] d = new int[100001];

    Navigator(List<List<Node>> graph) {
        this.graph = graph;
    }

    public static void dijkstra(int start) {
        Arrays.fill(d, INF);
        PriorityQueue<Node> pq = new PriorityQueue<>();
        // 시작 노드로 가기 위한 최단 경로는 0으로 설정하여, 큐에 삽입
        pq.offer(new Node(start, 0));
        d[start] = 0;

        while (!pq.isEmpty()) { // 큐가 비어있지 않다면
            // 가장 최단 거리가 짧은 노드에 대한 정보 꺼내기
            Node node = pq.poll();
            int dist = node.getDistance(); // 현재 노드까지의 비용
            int now = node.getIndex(); // 현재 노드
            // 현재 노드가 이미 처리된 적이 있는 노드라면 무시

            if (d[now] < dist)
                continue;

            // 현재 노드와 연결된 다른 인접한 노드들을 확인
            for (int i = 0; i < graph.get(now).size(); i++) {

                int cost = d[now] + graph.get(now).get(i).getDistance();
                // 현재 노드를 거쳐서, 다른 노드로 이동하는 거리가 더 짧은 경우
                if (cost < d[graph.get(now).get(i).getIndex()]) {
                    d[graph.get(now).get(i).getIndex()] = cost;
                    pq.offer(new Node(graph.get(now).get(i).getIndex(), cost));
                }
            }
        }
        System.out.println(d[4]);
        System.out.println(d[5]);
        System.out.println(d[6]);
        System.out.println(d[7]);
        System.out.println(d[100]);

    }
}

// 간선 정보에서 비용 정보도 얻어와야 함.
// 인접 리스트로 간선 정보 담아야 할 듯.

// 애초에 node 좌표를 가져오는 게 아니라 index를 가져왔어야 함.
// 다익스트라 알고리즘에서는 좌표에 대한 자세한 정보가 필요하지 않다. 그저 노드의 인덱스랑 간선 정보 담은 그래프만 있으면 돼.

