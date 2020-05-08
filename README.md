# kepler
A multithreaded implementation of Barnes-Hut N-Body simulator

To build: javac *.java

Usage: java Kepler TIME_STEP THREAD_COUNT DATAFILE
  
  
# Datafiles
Used to configure particles in simulation

File structure:
- One cluster per line
- Paticle Cluster Syntax (space delimited)
- CLUSTER_CODE  X_POSITION  Y_POSITION RADIUS  N_PARTICLES MASS
- please look at the example datafiles

Cluster Codes
- RAND - random distribution over given radius
- GAUS - Gaussian (Normal) distribution of position and velocities
- DISC - disc galaxy
