import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class Actor<S extends Shape> {
  Color color;
  Cell loc;
  S[] shapes;

  public void paint(Graphics g) {
    g.setColor(color);

    if (shapes != null) {
      Graphics2D g2 = (Graphics2D) g;
      for (S s : shapes) {
        g2.fill(s);

        g2.setColor(Color.BLACK);
        g2.draw(s);
      }
    }
    /*
     * g.setColor(color);
     * g.fillRect(loc.x + 5, loc.y + 5, loc.width - 10, loc.height - 10);
     * g.setColor(Color.GRAY);
     * g.drawRect(loc.x + 5, loc.y + 5, loc.width - 10, loc.height - 10);
     */
  }
}
