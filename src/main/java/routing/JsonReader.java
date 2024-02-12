package routing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
// 잭슨 공식 문서 살펴보기..

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {
    private List<List<Node>> graph = new ArrayList<>();
    public void process() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = null;
        JsonNode edges = null;

        try {
            // JSON 파일 읽기
            JsonNode rootNode = objectMapper.readTree(new File("roadNetwork/graph_data.json"));

            // 노드 정보 출력
            nodes = rootNode.path("nodes");
            for (JsonNode node : nodes) {
                int x = node.path(0).asInt();
                int y = node.path(1).asInt();
            }

            // 간선 정보 출력
            edges = rootNode.path("edges");
            for (JsonNode edge : edges) {
                int startIndex = edge.path(0).asInt();
                int endIndex = edge.path(1).asInt();
                int cost = edge.path(2).asInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < nodes.size(); i++) {
            graph.add(new ArrayList<>());
        }

        for (JsonNode edge : edges) {
            int startIndex = edge.path(0).asInt();
            int endIndex = edge.path(1).asInt();
            int cost = edge.path(2).asInt();
            graph.get(startIndex).add(new Node(endIndex, cost));
            System.out.println(startIndex + "-" + endIndex + "/" + cost);
        }

    }

    public List<List<Node>> getGraph() {
        return graph;
    }
}

// 노드가 연속적으로 이어지지 않아서 다익스트라 알고리즘이 정상적으로 작동하지 않음. 결국 전처리 문제에 다시 부딪힘.
