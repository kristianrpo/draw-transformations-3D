import MathElements.Point3;
import MathElements.Point4;

public class Edge {
    private Point4 start;
    private Point4 end;

    public Edge(Point4 start, Point4 end) {
        this.start = start;
        this.end = end;
    }

    public Point4 getStart() {
        return start;
    }

    public Point4 getEnd() {
        return end;
    }

    public void setStart(Point4 start) {
        this.start = start;
    }
    public void setEnd(Point4 end) {
        this.end = end;
    }
}
