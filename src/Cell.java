import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Cell extends Rectangle {
  static int size = 35;
  public int col, row;
  public int elevation;

  public Cell(int x, int y) {
    super(x, y, size, size);
    col = (x - 10) / size + 1;
    row = (y - 10) / size + 1;
    elevation = (int) (Math.random() * 10);
  }

  public void paint(Graphics g, Point mousePos) {
    if (contains(mousePos)) {
      g.setColor(Color.GRAY);
    } else {
      g.setColor(Color.WHITE);
    }
    g.fillRect(x, y, size, size);
    g.setColor(Color.BLACK);
    g.drawRect(x, y, size, size);
  }

  public boolean contains(Point p) {
    if (p != null) {
      return super.contains(p);
    } else {
      return false;
    }
  }
}
