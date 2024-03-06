package com.Hserver.townrouting.dto;

import com.Hserver.townrouting.domain.Node;
import java.util.List;

public class GraphRequest {
    private int start;
    private int end;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}