import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;

public abstract class Actor<S extends Shape> {
  Color color;
  Cell loc;
  S[] shapes;

  public void paint(Graphics g) {
    
    if (shapes != null) {
      Graphics2D g2 = (Graphics2D) g;

       g.setColor(color);
      for (S shape : shapes) {
        g2.fill(shape);

        g2.setColor(Color.GRAY);
        for (S outline : shapes) {
          g2.draw(outline);
        }
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
