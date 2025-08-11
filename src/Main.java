import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
  private Grid grid;

  public static void main(String[] args) throws Exception {
    Main window = new Main();
    window.run();
  }

  class Canvas extends JPanel {
    public Canvas() {
      setPreferredSize(new Dimension(720, 720));
    }

    @Override
    public void paint(Graphics g) {
      super.paint(g);

      g.setColor(java.awt.Color.BLACK);
      g.drawRect(10, 10, 700, 700);
      
      Point mouse = getMousePosition();

      grid.clearHighLight();
      if(mouse!=null){
        Cell hovered = grid.getCellAt(mouse.x, mouse.y);
        if(hovered!=null){
          hovered.setHighlight(true);
        }
      }

      grid.paint(g);
    }
  }

  private Main() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    grid = new Grid(20, 20, 35, 10, 10);

    Canvas canvas = new Canvas();
    this.setContentPane(canvas);
    this.pack();
    this.setVisible(true);
  }

  public void run() {
    while (true) {
      repaint();
    }
  }
}

class Cell {
  private int x;
  private int y;
  private int size;
  private boolean highlighted = false;

  public Cell(int x, int y, int size) {
    this.x = x;
    this.y = y;
    this.size = size;
  }

  public void setHighlight(boolean h) {
    this.highlighted = h;
  }

  public void paint(Graphics g) {
    if (highlighted) {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(x, y, size, size);
    }
    g.setColor(Color.BLACK);
    g.drawRect(x, y, size, size);
  }

  public boolean contains(int dX, int dY) {
    return dX >= x && dX < x + size && dY >= y && dY < y + size;
  }
}

class Grid {
  private Cell[][] cells;
  private int rows;
  private int cols;
  private int cSize;
  private int offSetX;
  private int offSetY;

  public Grid(int rows, int cols, int cSize, int offSetX, int offSetY) {
    this.rows = rows;
    this.cols = cols;
    this.cSize = cSize;
    this.offSetX = offSetX;
    this.offSetY = offSetY;

    cells = new Cell[rows][cols];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        int x = offSetX + col * cSize;
        int y = offSetY + row * cSize;
        cells[row][col] = new Cell(x, y, cSize);
      }
    }
  }

  public void paint(Graphics g) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col].paint(g);
      }
    }
  }

  public Cell getCellAt(int dX, int dY) {
    if (dX < offSetX || dY < offSetY)
      return null;
    int col = (dX - offSetX) / cSize;
    int row = (dY - offSetY) / cSize;
    if (row < 0 || row >= rows || col < 0 || col >= cols)
      return null;
    return cells[row][col];
  }

  public void clearHighLight() {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        cells[row][col].setHighlight(false);
      }
    }
  }
}
