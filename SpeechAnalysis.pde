// to use sound input
import processing.sound.*;
// an audio processing thingy
AudioIn in;

// to remember the size of the screen
final int FLOOR = 800;
// something to check sound amplitudes
Amplitude amp;
// keep a list of points
final int BASELINE = 600;

class point {
  float x, y, vel;
  
  point(float y) {
    x = FLOOR/2;
    this.y = BASELINE - y;
    vel = 1;
  }
  
  void display() {
    stroke(0);
    for (int i = 0; i < 3; i++) {
      point(this.x, this.y + i);
      //point(this.x +1, this.y + i);
      point(this.x, this.y - i);
      //point(this.x + 1, this.y - i);
    }
    x -= vel;
  }
  
}

ArrayList<point> a;
int zoom;
final int ZMAX = 12800;
final int ZMIN = 400;

void setup() {
 size(800, 800);
 zoom = FLOOR/2;
 a = new ArrayList<point>();
 amp = new Amplitude(this);
 in = new AudioIn(this);
 in.start();
 amp.input(in);
}

void draw() {  
    background(255);
    a.add(new point(amp.analyze() * zoom));
    if (zoom == ZMIN) fill(153);
    else noFill();
    rect(30, 20, 55, 55, 7);
    if (zoom >= ZMAX) fill(153);
    else noFill();
    rect(30 + 55, 20, 55, 55, 7);
    // minus symbol
    line(47, 47, 67, 47);
    // plus symbol
    line(47 + 55, 47, 67 + 55, 47);
    line(57 + 55, 57, 57 + 55, 37);
    if (a.size() >= 400) a.remove(0);
    for (int i = 0; i < a.size(); i++) {
      a.get(i).display(); 
    }
}

void mouseClicked() {
  if (pmouseX >= 30 && pmouseX <= 85) {
    if (pmouseY >= 20 && pmouseY <= 75) {
     print("Decrease Zoom");
     if (zoom > ZMIN) {
       zoom = zoom/2;
     }
    }
  }
  else if (pmouseX > 85 && pmouseX <= 140) {
    if (pmouseY >= 20 && pmouseY <= 75) {
      print("Increase Zoom");
      if (zoom < ZMAX) {
        zoom = zoom*2;
      }
    }
  }
  else {
    saveFrame();
    println("frame saved");
  }
}
