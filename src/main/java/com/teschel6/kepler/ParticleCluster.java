package com.teschel6.kepler;

import java.util.ArrayList;
import java.util.Random;

public class ParticleCluster {

    public static ArrayList<Particle> randomDistribution(double x, double y, double radius, int N, double mass) {

        ArrayList<Particle> particles = new ArrayList<Particle>();
        Random rand = new Random();
        particles = new ArrayList<Particle>();

        for (int i = 0; i < N; i++) {
            double theta = rand.nextDouble() * 2 * Math.PI;
            double r = radius * Math.sqrt(rand.nextDouble());
            double px = x + r * Math.cos(theta);
            double py = y + r * Math.sin(theta);

            Particle p = new Particle(px, py, mass);
            particles.add(p);
        }
        return particles;
    }

    public static ArrayList<Particle> discGalaxy(double x, double y, double radius, int N, double mass) {

        ArrayList<Particle> particles = new ArrayList<Particle>();
        Random rand = new Random();
        particles = new ArrayList<Particle>();

        final double G = 6.674E-11;
        double total_mass = 11 * N * mass;
        double black_hole_mass = 10 * N * mass;
        Particle black_hole = new Particle(x, y, black_hole_mass);
        particles.add(black_hole);

        for (int i = 0; i < N; i++) {
            double theta = rand.nextDouble() * 2 * Math.PI;
            double gradient = rand.nextGaussian() / 2;

            double r = radius * gradient;
            double px = x + r * Math.cos(theta);
            double py = y + r * Math.sin(theta);

            // Orbital velocity --> v = Sqrt(GM / r)
            double vel_magnitude = Math.sqrt((G * total_mass) / r);
            double vel_x = vel_magnitude * -1 * Math.sin(theta);
            double vel_y = vel_magnitude * Math.cos(theta);

            Particle p = new Particle(px, py, mass);
            p.setVelocity(new Vector(vel_x, vel_y));
            particles.add(p);
        }
        return particles;
    }

    public static ArrayList<Particle> gaussianDistribution(double x, double y, double radius, int N, double mass) {

        ArrayList<Particle> particles = new ArrayList<Particle>();
        Random rand = new Random();
        particles = new ArrayList<Particle>();

        final double G = 6.674E-11;

        for (int i = 0; i < N; i++) {
            double theta = rand.nextDouble() * 2 * Math.PI;
            double gradient = rand.nextGaussian() / 2;

            double r = radius * gradient;
            double px = x + r * Math.cos(theta);
            double py = y + r * Math.sin(theta);

            double vel_x = rand.nextGaussian();
            double vel_y = rand.nextGaussian();

            Particle p = new Particle(px, py, mass);
            p.setVelocity(new Vector(vel_x, vel_y));
            particles.add(p);
        }
        return particles;
    }

}
