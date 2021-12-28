package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Program
{

	final static int NUM_PROCS = 6; // How many concurrent processes
	final static int TOTAL_RESOURCES = 30; // Total resources in the system
	final static int MAX_PROC_RESOURCES = 13; // Highest amount of resources any process could need
	final static int ITERATIONS = 30; // How long to run the program
	static int totalHeldResources = 0;
	static int totalAvailable = TOTAL_RESOURCES;
	static Random rand = new Random();
	
	public static void main(String[] args)
	{
		
		// The list of processes:
		ArrayList<Proc> processes = new ArrayList<>();
		for (int i = 0; i < NUM_PROCS; i++)
			processes.add(new Proc(MAX_PROC_RESOURCES - rand.nextInt(3))); // Initialize to a new Proc, with some small range for its max
		
		// Run the simulation:
		for (int i = 0; i < ITERATIONS; i++)
		{
			System.out.println("THIS IS ITERATION NUMBER " + i);
			// loop through the processes and for each one get its request
			for (int j = 0; j < processes.size(); j++) {
				// Get the request
				int currRequest = processes.get(j).resourceRequest(TOTAL_RESOURCES - totalHeldResources);

				// just ignore processes that don't ask for resources
				if (currRequest == 0 || currRequest < 0) {
					System.out.println("Process " + j + " requested " + currRequest + ", ignored.");
					continue;
				} else if (currRequest > (TOTAL_RESOURCES - totalHeldResources)) {
					System.out.println("Process " + j + " requested " + currRequest + ", denied.");
					continue;
				}

				// Here you have to enter code to determine whether the request can be granted,
				// and then grant the request if possible. Remember to give output to the console 
				// this indicates what the request is, and whether or not its granted.

				//if the requested amount is less than the amount available or it is equal to the amount needed to complete
				if ((currRequest < (TOTAL_RESOURCES - totalHeldResources) ||
						currRequest == (processes.get(j).getMaxResources() - processes.get(j).getHeldResources())) &&
						currRequest > 0) {
					System.out.println("Process " + j + " requested " + currRequest + ", granted.");
					processes.get(j).addResources(currRequest);
					totalHeldResources += currRequest;

					//i don't need this if its done in Proc....
					if (processes.get(j).getHeldResources() == processes.get(j).getMaxResources()) {
						System.out.println("Process " + j + " has finished");
						totalHeldResources = processes.get(j).getHeldResources();

					}

				}

				// At the end of each iteration, give a summary of the current status:
				System.out.println("\n***** STATUS *****");
				System.out.println("Total Available: " + (TOTAL_RESOURCES - totalHeldResources));
				for (int k = 0; k < processes.size(); k++)
					System.out.println("Process " + k + " holds: " + processes.get(k).getHeldResources() + ", max: " +
							processes.get(k).getMaxResources() + ", claim: " +
							(processes.get(k).getMaxResources() - processes.get(k).getHeldResources()));
				System.out.println("***** STATUS *****\n");
			}

		}



	}

}
