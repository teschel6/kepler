# Kepler



Kepler is planatary motion simulator that implements the Barnes-Hut 
N-Body Force calculation algorigthm. 
[Read More](https://en.wikipedia.org/wiki/Barnes%E2%80%93Hut_simulation)\
Kepler is also multi-threaded so you can take advantage of 
multiple cores for faster simulations.

It is not a scientific tool, the simulations are meant to run in 
real time and is intented for visual experimentation
and exploration of planatery motion.

<img width="536" height="536" alt="image" src="https://github.com/user-attachments/assets/21d6532b-c279-49ce-84e6-95763881843c" />


## Building Kepler

Requires...

- java 21 or greater
- maven

```bash
mvn clean package
```

## Kepler Usage

```bash
java -jar target/kepler-1.0.0.jar <time_step> <thread_count> <sim_file>

# simple spiral galaxy simulation
java -jar target/kepler-1.0.0.jar 0.1 4 simulations/galaxy_spiral.sim

# galaxy collision - two particle clusters
java -jar target/kepler-1.0.0.jar 0.1 8 simulations/galaxy_collision.sim
```
* **time_step** </br>
the number of seconds that pass on each update.
Use a smaller number for more accurate simulations or a 
larger number for faster animations.

* **thread_count** </br>
the number of threads to run the simulation on.

* **sim_file** </br>
a file containing particle clusters configurations. 


## Simulation File
The simulation file contains a list of particle clusters
to put in the simulation. With one cluster per line of the file
positioned on a relative scale from 0.0 - 1.0 where (0,0)
is the top right of the simulation canvas.

### Simulation File Layout
```
<CLUSTER_CODE> <X_POSITION> <Y_POSITION> <RADIUS> <N_PARTICLES> <MASS>
```

* **CLUSTER_CODE**\
the type of distribution to use for particle cluster
see cluster codes section.
  
* **X_POSITION**\
the x position of the particle cluster center (0.0 - 1.0)
  
* **Y_POSITION**\
the y position of the particle cluster center (0.0 - 1.0)
  
* **RADIUS**\
the relative radius of particle cluster 
on a scale of 0.0 - 1.0 where 1.0 is entire canvas
  
* **N_PARTICLES**\
the number of particles in cluster

* **MASS**\
the mass of each particle in the cluster in kg


Play around with different groups of clusters. Hint: try
having the clusters run into eachother for more chaos.

**please look at the example datafiles**

# Cluster Codes
Various cluster distribution types to use in simulation

| Code | Description |
|------|-------------|
|RAND    | Random distribution over given radius|
|GAUS    | Gaussian (Normal) distribution of position and velocities|
|DISC    | Disc galaxy, or at least my rough estimate of one|
