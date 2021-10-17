
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Class Nbody simulator
 * la tocha que lo hara todo
 * gg para mi si lo consigo
 */
public class NBodySimulator {
  private  Universe newton;

  private double d_time;
  private int pause;
  private boolean trazada;

  public NBodySimulator(String archivo,double dt,int pausa,boolean trace) {
    newton = new Universe(archivo);
    pause=pausa;
    d_time=dt;
    trazada=trace;
  }

  public NBodySimulator(int nBodies) {

    newton = new Universe(nBodies);
    Random rd = new Random(); // creating Random object
    int random=rd.nextInt()%15;
    if( random<0){
      pause=-random;
    }else {
    pause=random;
    }

    d_time=rd.nextDouble();
  }
  public void draw() {
    Body[] bodys;
    bodys=newton.getBodies();
    int n=newton.getN();
    for (int i = 0; i < n; i++) {
      StdDraw.setPenRadius(0.025);
      Vector r=bodys[i].getR();
      StdDraw.point(r.cartesian(0), r.cartesian(1));
    }
  }

  public void draw(double penRadius) {
    Body[] bodys;
    bodys=newton.getBodies();
    int n= newton.getN();
    for (int i = 0; i < n; i++) {
      StdDraw.setPenRadius(penRadius);
      Vector r=bodys[i].getR();
      StdDraw.point(r.cartesian(0), r.cartesian(1));
    }
  }
  public void simulate() {
    double radius= newton.getRadi();
    StdDraw.setXscale(-radius, +radius);
    StdDraw.setYscale(-radius, +radius);
    StdDraw.clear(StdDraw.GRAY);
    StdDraw.enableDoubleBuffering();



      while (true) {
        if (!trazada) {
          StdDraw.clear();
          StdDraw.clear(StdDraw.GRAY);
          newton.increaseTime(d_time);
        }else{
          StdDraw.setPenColor(StdDraw.MAGENTA);
          this.draw();
          newton.increaseTime(d_time);
          StdDraw.setPenColor(StdDraw.BLACK);
        }
        System.out.println(newton.getBodies()[0].toString());
        System.out.println(d_time);
        System.out.println(pause);

        this.draw();
        StdDraw.show();
        StdDraw.pause(pause);
      }

  }



  // In IntelliJ : Run -> Run... -> Edit configurations -> Program arguments 10000 data/3body.txt
  //10000 data/4body.txt 10 trace
  public static void main(String[] args) {

    NBodySimulator nbody;
    int numargs = args.length;

    if ((numargs==3) || (numargs==4)) {

      double dt = Double.parseDouble(args[0]);
      String fname = args[1];
      int pauseTime = Integer.parseInt(args[2]);
      boolean do_trace = false;
      if (args.length == 4) {
        do_trace = args[3].toLowerCase().equals("trace");
        StdDraw.setCanvasSize(700, 700);
      }
      nbody = new NBodySimulator(fname, dt, pauseTime, do_trace);
      nbody.simulate();
    } else if (numargs==1) {

      int numBodies = Integer.parseInt(args[0]);
      nbody = new NBodySimulator(numBodies);
      nbody.simulate();
    } else {
      assert false : "invalid number of arguments";
    }

    }


}
