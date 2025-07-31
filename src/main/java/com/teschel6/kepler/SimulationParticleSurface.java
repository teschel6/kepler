package com.teschel6.kepler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import javax.swing.JPanel;

class SimulationParticleSurface extends JPanel implements ActionListener {

    private final int DELAY = 33;
    private Timer timer;

    private Simulation simulation;

    public SimulationParticleSurface(Simulation simulation) {
        // Start redraw timer
        timer = new Timer(DELAY, this);
        timer.start();
        this.simulation = simulation;
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setPaint(Color.WHITE);

        ArrayList<Particle> particles = simulation.getActiveParticles();
        g2d.drawString("Particles: \t" + particles.size(), 10, 20);
        String elapsed = String.format("%.2f", simulation.getElapsedTime());
        g2d.drawString("Elapsed: \t" + elapsed + " S", 10, 40);
        g2d.drawString("dt: \t" + simulation.getDeltaTime() + " S", 10, 60);
        for (Particle p : particles) {
            g2d.fillRect((int) p.getX(), (int) p.getY(), 2, 2);
        }
    }

    // redraw invoked by timer
    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

}
