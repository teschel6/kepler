package com.teschel6.kepler;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Simulation extends JFrame {

    public final static int WORLD_WIDTH = 1000;
    static private double deltaTime;
    private ArrayList<Particle> particles;
    private ArrayList<ArrayList<Particle>> partitions;
    private QuadNode root;
    private boolean running;
    private double elapsedTime;

    public Simulation(ArrayList<Particle> particles, double deltaTime, int threadCount) {
        Simulation.deltaTime = deltaTime;
        this.particles = particles;
        this.setSofteningParameter(1);
        this.partitionParticles(threadCount);
        running = false;
        elapsedTime = 0;
        initUI();
    }

    public void setSofteningParameter(double epsilon) {
        Particle.epsilon = epsilon;
    }

    private void initUI() {
        SimulationParticleSurface surface = new SimulationParticleSurface(this);
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });
        setTitle("N-Body Simulation");
        setSize(WORLD_WIDTH, WORLD_WIDTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {
        running = true;
        while (running) {
            try {
                update();
                elapsedTime += deltaTime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void partitionParticles(int N) {
        partitions = new ArrayList<ArrayList<Particle>>();
        int partition_size = particles.size() / N;
        for (int i = 0; i < N; i++) {
            ArrayList<Particle> partition = new ArrayList<Particle>();

            int lower_bound = i * partition_size;
            int upper_bound = lower_bound + (partition_size - 1);

            for (int j = lower_bound; j <= upper_bound; j++) {
                partition.add(particles.get(j));
            }
            partitions.add(partition);
        }
    }

    public void update() throws InterruptedException, StackOverflowError {
        root = new QuadNode(0, 0, WORLD_WIDTH / 2);

        // break into threads and add to quad-tree
        ArrayList<InsertThread> insert_threads = new ArrayList<InsertThread>();
        for (ArrayList<Particle> partition : partitions) {
            InsertThread thread = new InsertThread(partition, root);
            thread.start();
            insert_threads.add(thread);
        }
        for (InsertThread thread : insert_threads) {
            thread.join();
        }

        // break into threads and update force using quad-tree
        ArrayList<ApplyForceThread> force_threads = new ArrayList<ApplyForceThread>();
        for (ArrayList<Particle> partition : partitions) {
            ApplyForceThread thread = new ApplyForceThread(partition, root);
            thread.start();
            force_threads.add(thread);
        }

        for (ApplyForceThread thread : force_threads) {
            thread.join();
        }

        // break into threads and update using leap-frog integration
        ArrayList<UpdatePositionThread> update_threads = new ArrayList<UpdatePositionThread>();
        for (ArrayList<Particle> partition : partitions) {
            UpdatePositionThread thread = new UpdatePositionThread(partition);
            thread.start();
            update_threads.add(thread);
        }

        for (UpdatePositionThread thread : update_threads) {
            thread.join();
        }
    }

    static private class InsertThread extends Thread {

        private ArrayList<Particle> particles;
        QuadNode q;

        public InsertThread(ArrayList<Particle> particles, QuadNode q) {
            this.particles = particles;
            this.q = q;
        }

        @Override
        public void run() {
            for (Particle p : particles) {
                if (inBounds(p)) {
                    try {
                        q.insert(p);
                    } catch (StackOverflowError e) {
                        // e.printStackTrace();
                    }

                }
            }
        }

    }

    static private class ApplyForceThread extends Thread {

        private ArrayList<Particle> particles;
        QuadNode q;

        public ApplyForceThread(ArrayList<Particle> particles, QuadNode q) {
            this.particles = particles;
            this.q = q;
        }

        @Override
        public void run() {
            for (Particle p : particles) {
                if (inBounds(p)) {
                    p.resetForce();
                    q.updateForce(p);
                }
            }
        }
    }

    static private class UpdatePositionThread extends Thread {

        private ArrayList<Particle> particles;

        public UpdatePositionThread(ArrayList<Particle> particles) {
            this.particles = particles;
        }

        @Override
        public void run() {
            for (Particle p : particles) {
                if (inBounds(p)) {
                    p.updatePosition(deltaTime);
                }
            }
        }
    }

    private static boolean inBounds(Particle p) {
        return (p.getX() > 0 && p.getY() > 0 && p.getX() < WORLD_WIDTH && p.getY() < WORLD_WIDTH);
    }

    public ArrayList<Particle> getActiveParticles() {
        ArrayList<Particle> activeParticles = new ArrayList<Particle>();
        for (Particle p : this.particles) {
            if (inBounds(p)) {
                activeParticles.add(p);
            }
        }
        return activeParticles;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public double getElapsedTime() {
        return elapsedTime;
    }

}
