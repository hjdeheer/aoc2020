package Util;



public class Point4D extends Point3D {

    private int w;

    public Point4D(int x, int y, int z, int w) {
        super(x, y, z);
        this.w = w;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Point4D point4D = (Point4D) o;
        return w == point4D.w;
    }

    @Override
    public String toString() {
        return "Point4D{" +
                "w=" + w +
                '}';
    }
}
