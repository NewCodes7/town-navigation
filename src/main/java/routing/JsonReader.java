package routing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonReader {
    private List<List<Node>> graph = new ArrayList<>();

    public void process() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode nodes = null;
        JsonNode edges = null;

        try {
            // JSON 파일 읽기
            JsonNode rootNode = objectMapper.readTree(new File("roadNetwork/edges_data.json"));

            // 노드 정보 출력
            nodes = rootNode.path("nodes");
            for (JsonNode node : nodes) {
                int x = node.get(0).asInt();
                int y = node.get(1).asInt();
            }

            for (int i = 0; i <= nodes.size(); i++) {
                graph.add(new ArrayList<>());
            }

            // 간선 정보 출력
            edges = rootNode.path("edges");
            if (edges != null) {
                Iterator<String> fieldNames = edges.fieldNames();
                while (fieldNames.hasNext()) {
                    String key = fieldNames.next();
                    JsonNode edge = edges.get(key);

                    for (int i = 0; i < edge.size(); i++) {
                        JsonNode value = edge.get(i);
                        if (value == null || value.size() < 2) continue;

                        int endIndex = value.get(0).asInt();
                        double cost = value.get(1).asDouble();

//                        System.out.println("Start Index: " + key + ", End Index: " + endIndex + ", Cost: " + cost);
                        graph.get(Integer.parseInt(key)).add(new Node(endIndex, cost));
                    }
                }
            }

//            for (List<Node> n : graph) {
//                for (Node c : n) {
//                    System.out.println(c.getIndex() + " - " + c.getDistance());
//                }
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<List<Node>> getGraph() {
        return graph;
    }
}