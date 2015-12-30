/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bia.sga;

import bia.common.Utils;
import java.util.Arrays;

/**
 * @author Frantisek Hruska, Jiri Kubalik
 */
public class StatData {
    /**
     * Best fitness in the population (best-so-far).
     */
    public static double fbest = Double.MAX_VALUE;

    /**
     * Average fitness of the chromosomes.
     */
    public static double favg = 0.0;

    /**
     * Fitness of the worst chromosome.
     */
    public static double fworst = Double.MIN_VALUE;

    /**
     * Sum of fitness values over the whole population.
     */
    public static double fsum = 0.0;
    /**
     * Index of the best individual.
     */
    public static int indbest = 0;

    /**
     * Index of the worst individual
     */
    public static int indworst = 0;

    /**
     * genes of the all-time-best solution
     */
    public static Individual bestIndividual;

    /**
     * SGA parameters
     */
    public static SGAParameters parameters = SGAParameters.getInstance();

    /**
     * Stores statistics of actual population.
     *
     * @param p population of individuals
     */
    public static void getStatistics(Individual[] p) {
        fsum = 0.0;
        for (int i = 0; i < parameters.getPopSize(); i++) {
            if (fbest >= p[i].fitness) {
                fbest = p[i].fitness;   // Store best fitness value.
                indbest = i;              // Store best fitness individual index.
                bestIndividual = p[i].clone();
            }
            if (fworst <= p[i].fitness) {
                fworst = p[i].fitness;    // Store worst fitness value.
                indworst = i;             // Stroe worst fitness individual index.
            }
            fsum += p[i].fitness;           // Calculate fitness sum of whole population.
        }

        favg = fsum / parameters.getPopSize();  // Calculate average fitness value in population.
    }

    /**
     * Prints evolution progress information on standard output.
     *
     * @param evals number of total evaluations during evolution.
     */
    public static void reportResults(int evals) {
        System.out.println("evals: " + evals + "\t fbest: " + fbest + "\t favg: " + favg);
    }

    /**
     * Prints value of individual and its fitness value on standard output.
     *
     * @param p population of individuals.
     */
    public static void printBest(Individual[] p) {
        double[] x = getBest(p);
        SGA.drawIndividual(p[indbest]);

        // Print the values of x on standard output.
        System.out.println("\tbest solution: " + Utils.vectorToString(x) + " fitness " + p[indbest].fitness);
    }

    public static double[] getBest(Individual[] p) {
        return p[indbest].genes;
    }
}
