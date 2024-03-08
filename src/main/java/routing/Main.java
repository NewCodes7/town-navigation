package routing;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        JsonReader jsonReader = new JsonReader();
        jsonReader.process();
        Navigator navigator = new Navigator(jsonReader.getGraph());

        navigator.dijkstra(0, 205);

    }
}

// 오히려 다시 시작하는 게 빠를 수 있구나..!