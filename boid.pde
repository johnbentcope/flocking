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

  void update()
  {
    vel.add(acc);
    pos.add(vel);
    edges();
    acc.mult(0);
  }

  void edges()
  {
    if(pos.x < frame) pos.x += (width-frame*4);
    else if(pos.x > (width-frame) ) pos.x -= (width - frame*2);
    if(pos.y < frame) pos.y += (height-frame*2);
    else if(pos.y > (height-frame) ) pos.y -= (height-frame*2);
    // if(pos.x <=0) pos.x+= width;
    // else if(pos.x >= width) pos.x -= width;
  }

  void render(color c)
  {
    ellipseMode(CENTER);
    fill(c);
    noStroke();
    ellipse(pos.x, pos.y, 4, 4);
  }


}