import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Kepler{
	static Simulation simulation;
	static double deltaTime;
	static int threads;

	public static void main(String[] args) {
		//process simulation arguments
		if(args.length != 3){
			System.out.println("Usage: \tKepler time_step threads datafile");
			System.out.println("Example: Kepler 0.1 4 clusters.txt");
			System.exit(0);
		}

		deltaTime = Double.parseDouble(args[0]);
		threads = Integer.parseInt(args[1]);
		String filename = args[2];

		ArrayList<Particle> particles = loadParticleClusters(filename);

		System.out.println("\n==================================\n");
		System.out.println("Particles:\t"+particles.size());
		System.out.println("Delta Time:\t"+deltaTime);
		System.out.println("Thread Count:\t"+threads);
		System.out.println("\n==================================\n");
		System.out.println("Starting simulation...");

		simulation = new Simulation(particles,deltaTime,threads);
		simulation.setVisible(true);
		simulation.start();
	}

	public static ArrayList<Particle> loadParticleClusters(String filename){
		ArrayList<Particle> particles = new ArrayList<Particle>();
		try {
      File file = new File(filename);
      Scanner scanner = new Scanner(file);
			int n = 1;
      while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
        String[] data = line.split("\\s+");

				if(data.length == 6){
					String cluster_type = data[0];
					double x = Double.parseDouble(data[1]) * Simulation.WORLD_WIDTH;
					double y = Double.parseDouble(data[2]) * Simulation.WORLD_WIDTH;
					double radius = Double.parseDouble(data[3]) * Simulation.WORLD_WIDTH;
					int N = Integer.parseInt(data[4]);
					double mass = Double.parseDouble(data[5]);

					switch (cluster_type) {
						case "RAND":
							particles.addAll(ParticleCluster.randomDistribution(x,y,radius,N,mass));
							break;
						case "GAUS":
							particles.addAll(ParticleCluster.gaussianDistribution(x,y,radius,N,mass));
							break;
						case "DISC":
							particles.addAll(ParticleCluster.discGalaxy(x,y,radius,N,mass));
							break;
						default:
							System.out.println("Invalid cluster code on line "+n+": \n\t" + line);
					}
				}else{
					System.out.println("Malformed datafile on line "+n+": \n\t" + line);
				}
				n++;
      }
      scanner.close();
    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
		return particles;
	}
}
