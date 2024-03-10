import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JPanel;
import MathElements.Matrix4x4;
import MathElements.Point4;

public class DrawFile extends JPanel implements KeyListener {

    private File file;
    private Point4[] vertexes;
    private Edge[] edges;

    private int d;
    private int numberOfRotations;
    private Matrix4x4 translationMatrix;
    private Matrix4x4 rotationMatrix;
    private boolean fileRead;

    public DrawFile(String fileName) {
        this.fileRead = false;
        this.file = new File(fileName);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocus();
        this.translationMatrix = Matrix4x4.identity();
        this.rotationMatrix = Matrix4x4.identity();
        this.d= -500;
        this.numberOfRotations = 0;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        drawAxes(g, width, height);
        if (!fileRead){
            try {
                Scanner scanner = new Scanner(this.file);
                int numberVertexes = readNumberOfVertexes(scanner);
                readVertices(scanner, numberVertexes);
                int numberEdges = readNumberOfEdges(scanner);
                readEdges(scanner, numberEdges);
                scanner.close();

                drawEdges(g2d, width, height,d);

                fileRead = true;
            } catch (FileNotFoundException e) {
                System.out.println("Archivo no encontrado: " + e.getMessage());
            }
        }
        drawEdges(g2d, width, height,d);
    }

    public void drawAxes(Graphics g, int width, int height) {
        g.setColor(Color.RED);
        g.drawLine(width / 2, 0, width / 2, height);
        g.setColor(Color.GREEN);
        g.drawLine(0, height / 2, width, height / 2);
    }

    public int readNumberOfVertexes(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }

    public void readVertices(Scanner scanner, int numberVertexes) {
        vertexes = new Point4[numberVertexes];
        for (int i = 0; i < numberVertexes; i++) {
            String[] splitText = scanner.nextLine().split(" ");
            int x = Integer.parseInt(splitText[0]);
            int y = Integer.parseInt(splitText[1]);
            int z = Integer.parseInt(splitText[2]);
            vertexes[i] = new Point4(x, y,z,1);
        }
    }

    public int readNumberOfEdges(Scanner scanner) {
        return Integer.parseInt(scanner.nextLine());
    }

    public void readEdges(Scanner scanner, int numberEdges) {
        edges = new Edge[numberEdges];
        for (int i = 0; i < numberEdges; i++) {
            String[] splitText = scanner.nextLine().split(" ");
            int startIndex = Integer.parseInt(splitText[0]);
            int endIndex = Integer.parseInt(splitText[1]);
            edges[i] = new Edge(vertexes[startIndex], vertexes[endIndex]);
        }
    }

    public void drawEdges(Graphics2D g2d, int width, int height, int d) {
        g2d.setColor(Color.BLACK);
        for (Edge edge : edges) {
            double x1 = (edge.getStart().getX())/(edge.getStart().getZ()/d) + width/2;;
            double y1 = height / 2 - ((edge.getStart().getY())/(edge.getStart().getZ()/d));
            double x2 = (edge.getEnd().getX())/(edge.getEnd().getZ()/d) + width/2;
            double y2 = height / 2 - ((edge.getEnd().getY())/(edge.getEnd().getZ()/d));
            g2d.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                Transforms.translate(vertexes, edges, translationMatrix, 0, 0, 10,numberOfRotations,10);
                break;
            case KeyEvent.VK_DOWN:
                Transforms.translate(vertexes, edges, translationMatrix, 0, 0, -10, numberOfRotations,10);
                break;
            case KeyEvent.VK_LEFT:
                numberOfRotations--;
                Transforms.rotate(vertexes, edges, translationMatrix, rotationMatrix, -10, 'y');
                break;
            case KeyEvent.VK_RIGHT:
                numberOfRotations++;
                Transforms.rotate(vertexes, edges, translationMatrix, rotationMatrix, 10, 'y');
                break;
            default:
                break;
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}


    public static void main(String[] args) {
        Scanner scannerFile = new Scanner(System.in);
        System.out.println("Route of file to draw:");
        String filePath = scannerFile.nextLine();
        scannerFile.close();
        JFrame frame = new JFrame("File Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawFile drawFile = new DrawFile(filePath);
        frame.add(drawFile);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
