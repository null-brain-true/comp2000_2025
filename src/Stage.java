import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class Stage {
  Grid grid;
  List<Actor<?>> actors;

  public Stage() {
    grid = new Grid();
    actors = new ArrayList<>();
    Random rand = new Random();

    actors.add(new Cat(grid.cellAtColRow(rand.nextInt(20), rand.nextInt(20)).get()));
    actors.add(new Dog(grid.cellAtColRow(rand.nextInt(20), rand.nextInt(20)).get()));
    actors.add(new Bird(grid.cellAtColRow(rand.nextInt(20), rand.nextInt(20)).get()));
    /*
     * cat = new Cat(grid.cellAtColRow(catC, catR));
     * dog = new Dog(grid.cellAtColRow(dogC, dogR));
     * bird = new Bird(grid.cellAtColRow(birdC, birdR));
     */
  }

  public void paint(Graphics g, Point mouseLoc) {
    grid.paint(g, mouseLoc);

    for (Actor<?> a : actors) {
      a.paint(g);
    }

    g.drawRect(740, 20, 260, 700);
    Optional<Cell> maybeCell = grid.cellAtPoint(mouseLoc);
    if (maybeCell.isPresent()) {
      Cell c = maybeCell.get();

      Optional<String> actorName = actorAtCell(c);
      if (actorName.isPresent()) {
        g.drawString("Actor " + actorName.get(), 750, 40);
      } else {
        g.drawString("Cell: (" + c.col + "," + c.row + ")", 750, 40);
      }
      g.drawString("Elevation: " + c.elevation, 750, 60);

      g.drawString("Type: " + c.getClass().getSimpleName(), 750, 80);
    } else {
      g.drawString("No cell under cursor", 750, 40);
    }
    /*
     * cat.paint(g);
     * dog.paint(g);
     * bird.paint(g);
     */
  }

  public Optional<String> actorAtCell(Cell c) {
    for (Actor<?> a : actors) {
      if (a.loc == c) {
        return Optional.of(a.getClass().getSimpleName());
      }
    }
    return Optional.empty();
  }
}
