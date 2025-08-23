import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

public class Stage {
  Grid grid;
  Actor cat;
  Actor dog;
  Actor bird;

  public Stage() {
    grid = new Grid();
    Random rand = new Random();

    int catC = rand.nextInt(20);
    int catR = rand.nextInt(20);

    int dogC = rand.nextInt(20);
    int dogR = rand.nextInt(20);

    int birdC = rand.nextInt(20);
    int birdR = rand.nextInt(20);

    cat = new Cat(grid.cellAtColRow(catC, catR));
    dog = new Dog(grid.cellAtColRow(dogC, dogR));
    bird = new Bird(grid.cellAtColRow(birdC, birdR));
  }

  public void paint(Graphics g, Point mouseLoc) {
    grid.paint(g, mouseLoc);
    cat.paint(g);
    dog.paint(g);
    bird.paint(g);
  }
}
