# kepler
A multithreaded implementation of Barnes-Hut N-Body simulator

To build: javac *.java

Usage: java Kepler <time_step> <thread_count> <sim_file>

time_step       the number of seconds that pass on each update.
                Use a smaller number for more accurate simulations or a 
                larger number for faster animations. 
thread_count    the number of threads to run the simulation on 
sim_file        a file containing particle clusters configurations
  
# Simulation File
The simulation file contains a list of particle clusters
to put in the simulation. With one cluster per line of the file
positioned on a relative scale from 0.0 - 1.0 where (0,0)
is the top right of the simulation canvas.



File Syntax:
<CLUSTER_CODE>  <X_POSITION>  <Y_POSITION> <RADIUS>  <N_PARTICLES> <MASS>

CLUSTER_CODE    the type of distribution to use for particle cluster
                see cluster codes section
X_POSITION      the x position of the particle cluster center (0.0 - 1.0)
Y_POSITION      the y position of the particle cluster center (0.0 - 1.0)
RADIUS          the relative radius of particle cluster 
                on a scale of 0.0 - 1.0 where 1.0 is entire canvas
N_PARTICLES     the number of particles in cluster
MASS            the mass of each particle in the cluster in kg

Play around with different groups of clusters. Hint: try
having the clusters run into eachother for a spectacular
animation. 

please look at the example datafiles

# Cluster Codes
Various cluster distribution types to use in simulation

RAND    random distribution over given radius
GAUS    Gaussian (Normal) distribution of position and velocities
DISC    disc galaxy, rough estimate of disc galaxy
