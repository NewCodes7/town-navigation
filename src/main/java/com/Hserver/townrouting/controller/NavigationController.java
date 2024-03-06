package com.Hserver.townrouting.controller;

import com.Hserver.townrouting.dto.GraphRequest;
import com.Hserver.townrouting.service.DijkstraService;
import com.Hserver.townrouting.util.ReadMapData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/navigation")
public class NavigationController {

    @PostMapping("/shortest-path")
    public String findShortestPath(@RequestBody GraphRequest request) {
        ReadMapData readMapData = new ReadMapData();
        readMapData.process();
        DijkstraService dijkstraService = new DijkstraService(readMapData.getGraph());

        // 다익스트라 알고리즘으로 최단 경로 계산
        int start = request.getStart();
        int end = request.getEnd();
        dijkstraService.dijkstra(start, end);

        // 리팩토링: 그래프 전처리 미리 해서 저장해두는 방법?
        // 결과 반환
        return "Shortest path from " + start + " to " + end + " calculated.";
    }
}