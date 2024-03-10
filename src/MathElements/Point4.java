package MathElements;

public class Point4 {
    public double x;
    public double y;
    public double z;
    public double w;
    public Point4(double x, double y, double z, double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() { return z; }
    public double getW() { return w; }

    public String toString(){
        return this.x + ", " + this.y + ", " + this.z + ", " + this.w + "\n";
    }

}
