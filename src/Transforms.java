
import MathElements.Matrix4x4;
import MathElements.Point4;

import static MathElements.Matrix4x4.times;

public class Transforms {
    public static Point4 calculateCenter(Point4[] vertexes) {
        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        for (Point4 vertex : vertexes) {
            sumX += vertex.getX();
            sumY += vertex.getY();
            sumZ += vertex.getZ();
        }
        double centerX = sumX / vertexes.length;
        double centerY = sumY / vertexes.length;
        double centerZ = sumZ / vertexes.length;
        return new Point4(centerX, centerY, centerZ,1);
    }

    public static void applyTransformation(Point4[] vertexes, Edge[] edges, Matrix4x4 translationMatrix,
                                           Matrix4x4 rotationMatrix, Matrix4x4 scalingMatrix, int flagTransformation) {
        for (int i = 0; i < vertexes.length; i++) {
            if (flagTransformation == 1) {
                vertexes[i] = times(translationMatrix, vertexes[i]);
            } else if (flagTransformation == 2) {
                vertexes[i] = times(rotationMatrix, vertexes[i]);

            } else if (flagTransformation == 3) {
                vertexes[i] = times(scalingMatrix, vertexes[i]);
            }
        }
        for (Edge edge : edges) {
            if (flagTransformation == 1) {
                edge.setStart(times(translationMatrix, edge.getStart()));
                edge.setEnd(times(translationMatrix, edge.getEnd()));
            } else if (flagTransformation == 2) {
                edge.setStart(times(rotationMatrix, edge.getStart()));
                edge.setEnd(times(rotationMatrix, edge.getEnd()));
            } else if (flagTransformation == 3) {
                edge.setStart(times(scalingMatrix, edge.getStart()));
                edge.setEnd(times(scalingMatrix, edge.getEnd()));
            }

        }
    }

    public static void translate(Point4[] vertexes, Edge[] edges, Matrix4x4 translationMatrix, int x, int y, int z, int numberOfRotations, int degree) {
        Point4 pointToMove = new Point4(x,y,z,1);
        if (numberOfRotations!=0){
            Point4 newPoint = times(Matrix4x4.rotation(degree*numberOfRotations,'y'),pointToMove);
            translationMatrix = Matrix4x4.translation(newPoint.getX(), newPoint.getY(), newPoint.getZ());
            applyTransformation(vertexes, edges, translationMatrix, null, null, 1);
        }
        else{
            translationMatrix = Matrix4x4.translation(pointToMove.getX(), pointToMove.getY(), pointToMove.getZ());
            applyTransformation(vertexes, edges, translationMatrix, null, null, 1);
        }
    }

    public static void rotate(Point4[] vertexes, Edge[] edges, Matrix4x4 translationMatrix, Matrix4x4 rotationMatrix,
                              int degrees, char axis) {
        Point4 center = calculateCenter(vertexes);
        translationMatrix = Matrix4x4.translation(-center.getX(), -center.getY(),-center.getZ());
        applyTransformation(vertexes, edges, translationMatrix, null, null, 1);
        rotationMatrix = Matrix4x4.rotation(degrees,axis);
        applyTransformation(vertexes, edges, null, rotationMatrix, null, 2);
        translationMatrix = Matrix4x4.translation(center.getX(), center.getY(),center.getZ());
        applyTransformation(vertexes, edges, translationMatrix, null, null, 1);
    }

}
