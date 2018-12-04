import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SpeechAnalysis extends PApplet {

// to use sound input

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
  
  public void display() {
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

public void setup() {
 
 zoom = FLOOR/2;
 a = new ArrayList<point>();
 amp = new Amplitude(this);
 in = new AudioIn(this);
 in.start();
 amp.input(in);
}

public void draw() {  
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

public void mouseClicked() {
  if (pmouseX >= 30 && pmouseX <= 85) {
    if (pmouseY >= 20 && pmouseY <= 75) {
     print("Decrease Zoom");
     if (zoom > ZMIN) {
       zoom = zoom/2;
     }
    }
  }
  if (pmouseX > 85 && pmouseX <= 140) {
    if (pmouseY >= 20 && pmouseY <= 75) {
      print("Increase Zoom");
      if (zoom < ZMAX) {
        zoom = zoom*2;
      }
    }
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "SpeechAnalysis" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
