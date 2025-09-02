import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Polygon;
import java.util.List;

public abstract class Actor<S extends Shape> {
  Color color;
  Cell loc;
  List<Polygon> display;

  public void paint(Graphics g) {
    
    if (display != null) {
      for(Polygon p : display){
        g.setColor(color);
        g.fillPolygon(p);
        g.setColor(Color.GRAY);
        g.drawPolygon(p);

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
