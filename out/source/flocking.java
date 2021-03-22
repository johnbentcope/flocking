import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.svg.*; 
import java.util.HashMap; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class flocking extends PApplet {


 

boolean record;

PImage source;

ArrayList<Boid> flock;

int rows = 50;
int cols = 75;

HashMap<Integer, Integer> Cmap = new HashMap<Integer, Integer>();
HashMap<Integer, Integer> Mmap = new HashMap<Integer, Integer>();
HashMap<Integer, Integer> Ymap = new HashMap<Integer, Integer>();
HashMap<Integer, Integer> Kmap = new HashMap<Integer, Integer>();

PImage C, M, Y, K;

int mode = 0;

public void setup() {
  
  float xspace = width/(cols+1);
  float yspace = height/(rows+1);
  source = loadImage("baloons.png");
  //source = loadImage("lena.jpg");
  //source = loadImage("kitten.jpg");
  flock = new ArrayList<Boid>();

  C = createImage(source.width, source.height, RGB);
  M = createImage(source.width, source.height, RGB);
  Y = createImage(source.width, source.height, RGB);
  K = createImage(source.width, source.height, RGB);

  source.loadPixels();
  C.loadPixels();
  M.loadPixels();
  Y.loadPixels();
  K.loadPixels();

  for(int y = 0; y < source.height; y++)
  {
    for(int x = 0; x < source.width; x++)
    {
      int i = getI(x,y,source.width);
      int s = source.pixels[i];
      C.pixels[i] = color(getC(s));
      M.pixels[i] = color(getM(s));
      Y.pixels[i] = color(getY(s));
      K.pixels[i] = color(getK(s));
    }
  }

  C.updatePixels();
  M.updatePixels();
  Y.updatePixels();
  K.updatePixels();


  for (int y = 0; y < rows; y++)
  {
    for (int x = 0; x < cols; x++)
    {
      flock.add(new Boid( (x+1)*xspace, (y+1)*yspace));
    }
  }
  background(255);
}

public void draw() {
  if (record) {
    // Note that #### will be replaced with the frame number. Fancy!
    beginRecord(SVG, "frame-####.svg");
  }

  rectMode(CORNERS);
  fill(255,5);
  rect(0,0,width,height);

  // Draw something good here
  switch(mode){
    case 0: image(source,0,0); break;
    case 1: image(C,0,0); break;
    case 2: image(M,0,0); break;
    case 3: image(Y,0,0); break;
    case 4: image(K,0,0); break;
  }
  
  // image(source,0,0);
  // image(C,0,150);
  // image(M,0,300);
  // image(Y,0,450);
  // image(K,0,600);


  // for(Boid b : flock)
  // {
  //   b.update();
  //   switch(mode) {
  //     case 0:  b.render( source.get( (int) b.pos.x+100, (int) b.pos.y+200 ) ); break;
  //     case 1:  b.render( C.get( (int) b.pos.x+100, (int) b.pos.y+200 ) ); break;
  //     case 2:  b.render( M.get( (int) b.pos.x+100, (int) b.pos.y+200 ) ); break;
  //     case 3:  b.render( Y.get( (int) b.pos.x+100, (int) b.pos.y+200 ) ); break;
  //     case 4:  b.render( K.get( (int) b.pos.x+100, (int) b.pos.y+200 ) ); break;
  //   }
  // }
  println(mode);

  if (record) {
    endRecord();
	  record = false;
  }
}

// Use a keypress so thousands of files aren't created
public void mousePressed() {
  //record = true;
}

public int getI(int x, int y, int w){
  return x+y*w;
}

public float getC(int c)
{
  return constrain((255*(255-red(c)-getK(c)) / (255-getK(c))), 0,255);
}

public float getM(int c)
{
  return constrain((255*(255-green(c)-getK(c)) / (255-getK(c))), 0,255);
}

public float getY(int c)
{
  return constrain((255*(255-blue(c)-getK(c)) / (255-getK(c))), 0,255);
}

public float getK(int c)
{
  return 255-max( red(c), green(c), blue(c) );
}

public void keyPressed()
{
  if(key == 'c' || key == 'C') mode = 1;
  if(key == 'm' || key == 'M') mode = 2;
  if(key == 'y' || key == 'Y') mode = 3;
  if(key == 'k' || key == 'K') mode = 4;
  if(key == 's' || key == 'S') mode = 0;
}
class Boid
{

  int frame = 20;

  PVector pos, vel, acc;
  PImage img;

  Boid(float x, float y)
  {
    pos = new PVector(x, y);
    vel = PVector.fromAngle(random(TWO_PI));
    acc = new PVector();
  }

  public void update()
  {
    vel.add(acc);
    pos.add(vel);
    edges();
    acc.mult(0);
  }

  public void edges()
  {
    if(pos.x < frame) pos.x += (width-frame*4);
    else if(pos.x > (width-frame) ) pos.x -= (width - frame*2);
    if(pos.y < frame) pos.y += (height-frame*2);
    else if(pos.y > (height-frame) ) pos.y -= (height-frame*2);
    // if(pos.x <=0) pos.x+= width;
    // else if(pos.x >= width) pos.x -= width;
  }

  public void render(int c)
  {
    ellipseMode(CENTER);
    fill(c);
    noStroke();
    ellipse(pos.x, pos.y, 4, 4);
  }


}
  public void settings() {  size(960, 750); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "flocking" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
