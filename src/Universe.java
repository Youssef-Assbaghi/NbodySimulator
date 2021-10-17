import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.Random;

/******************************************************************************
 *  Compilation:  javac Universe.java
 *  Execution:    java Universe dt input.txt
 *  Dependencies: Body.java Vector.java StdIn.java StdDraw.java
 *  Datafiles:    http://www.cs.princeton.edu/introcs/34nbody/2body.txt
 *                http://www.cs.princeton.edu/introcs/34nbody/3body.txt
 *                http://www.cs.princeton.edu/introcs/34nbody/4body.txt
 *                http://www.cs.princeton.edu/introcs/34nbody/2bodyTiny.txt
 *
 *  This data-driven program simulates motion in the universe defined
 *  by the standard input stream, increasing time at the rate on the
 *  command line.
 *
 *  %  java Universe 25000 4body.txt
 *
 *
 ******************************************************************************/

public class Universe {
    private  int n;             // number of bodies DEBEN SER PRIVATE LAS DOS
    private  Body[] bodies;     // array of n bodies
    private double radi;

    // read universe from standard input
    public Body[] getBodies(){
        return this.bodies;
    }

    public double getRadi() {
        return radi;
    }
    public int getN() {
        return n;
    }



    public Universe(String archivo) {
        try {
            Scanner in = new Scanner(new FileReader(archivo));
            n = Integer.parseInt(in.next());
            // number of bodies
            System.out.println("nBodies=" + n);

            // the set scale for drawing on screen
            double radius = Double.parseDouble(in.next());
            radi=radius;

            System.out.println("radius=" + radius);


            // read in the n bodies
            bodies = new Body[n];
            for (int i = 0; i < n; i++) {
                System.out.println("i=" + i);
                double rx = Double.parseDouble(in.next());
                double ry = Double.parseDouble(in.next());
                double vx = Double.parseDouble(in.next());
                double vy = Double.parseDouble(in.next());
                double mass = Double.parseDouble(in.next());
                double[] position = {rx, ry};
                double[] velocity = {vx, vy};
                Vector r = new Vector(position);
                Vector v = new Vector(velocity);
                bodies[i] = new Body(r, v, mass);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Universe(int nBodies){
        //crear bodies
        //seteamos el radius
        n=nBodies;
        bodies = new Body[n];
        Random rd = new Random(); // creating Random object
        radi=rd.nextDouble();


        for (int i = 0; i < n; i++) {
            System.out.println("i=" + i);
            double upper = 1;
            double lower = -1;
            double rx = Math.random() * (upper - lower) + lower;
            double ry = Math.random() * (upper - lower) + lower;
            double vx = rd.nextDouble();
            double vy = rd.nextDouble();
            double mass = rd.nextDouble();
            double[] position = {rx, ry};
            double[] velocity = {vx, vy};
            Vector r = new Vector(position);
            Vector v = new Vector(velocity);
            bodies[i] = new Body(r, v, mass);
        }

    }

    // increment time by dt units, assume forces are constant in given interval
    public void increaseTime(double dt) {

        // initialize the forces to zero
        Vector[] f = new Vector[n];
        for (int i = 0; i < n; i++) {
            f[i] = new Vector(new double[2]);
        }

        // compute the forces
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    f[i] = f[i].plus(bodies[i].forceFrom(bodies[j]));
                }
            }
        }

        // move the bodies
        for (int i = 0; i < n; i++) {
            bodies[i].move(f[i], dt);
        }
    }


    // client to simulate a universe
    // In IntelliJ : Run -> Run... -> Edit configurations -> Program arguments 10000 data/3body.txt
    /*public static void main(String[] args) {
        Universe newton;
        double dt = Double.parseDouble(args[0]);
        System.out.println("dt=" + dt);
        String archivo = args[1];
        newton = new Universe(archivo);
        StdDraw.enableDoubleBuffering();
        while (true) {
            StdDraw.clear();
            newton.increaseTime(dt);
            newton.draw();
            StdDraw.show();
            StdDraw.pause(10);
        }
    }*/
}
