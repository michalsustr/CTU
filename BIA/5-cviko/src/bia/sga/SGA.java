
package bia.sga;

import sun.awt.geom.Crossings;

/**
 * This main class represents population and implements evolution process.
 *
 * @author Frantisek Hruska, Jiri Kubalik
 */
public class SGA {

    /**
     * Old and new population.
     */
    static Individual[] oldPop;
    static Individual[] newPop;

    /**
     * Newly generated individuals
     */
    static Individual[] offspringPop;

    /**
     * SGA parameters
     */
    public static SGAParameters parameters = SGAParameters.getInstance();


    /**
     * Main function.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        run();
    }

    public static double[] run() throws Exception {

        parameters.readConfigFile();

        int cgen = 0;
        int totEvals = 0;

        // Allocate memory for arrays
        newPop = new Individual[parameters.getPopSize()];
        oldPop = new Individual[parameters.getPopSize()];
        offspringPop = new Individual[2];

        // Initialise population.
        totEvals = initializePopulation(oldPop);

        // Generate statistic data.
        StatData.getStatistics(oldPop);
        // Print results.
        StatData.reportResults(totEvals);
        StatData.printBest(oldPop);

        // Evolve population using either generational or steady-state replacement strategy
        evolvePopulation(oldPop, totEvals);
        return StatData.getBest(oldPop);
    }

    /**
     * Evolves the @param population either using a generational or steady-state replacement strategy.
     *
     * @param population the population to be evolved.
     * @param totEvals   a number of fitness function evaluations that have been calculated
     *                   during an initialization phase.
     */
    public static void evolvePopulation(Individual[] population, int totEvals) {

        switch (parameters.getReplacementType()) {
            case 0:
                evolvePopGenerational(population, totEvals);
                break;
            case 1:
                evolvePopSteadyState(population, totEvals);
                break;
            default:
                System.err.println("ERROR - Bad value of replacementType variable. Check if its value is 0 or 1.");
                System.exit(1);
        }
    }

    /**
     * Evolves the @param population using a generational replacement strategy.
     *
     * @param population the population to be evolved.
     * @param totEvals   a number of fitness function evaluations that have been calculated
     *                   during an initialization phase.
     */
    public static void evolvePopGenerational(Individual[] population, int totEvals) {
    /* PUT YOUR CODE HERE */

    }

    /**
     * Evolves the @param population using a steady-state replacement strategy.
     *
     * @param population the population to be evolved.
     * @param totEvals   a number of fitness function evaluations that have been calculated
     *                   during an initialization phase.
     */
    public static void evolvePopSteadyState(Individual[] population, int totEvals) {
        while(parameters.getEvaluations() > totEvals) {
            // select the parents that will be used
            int[] parents = selection(population);

            // find out if we mutate or crossover
            double probab = Math.random();
            Individual[] offspring;
            if(probab > parameters.getPC()) {
                offspring = population[parents[0]].crossover(population[parents[1]]);
            } else {
                offspring = new Individual[2];
                offspring[0] = population[parents[0]].clone();
                offspring[1] = population[parents[1]].clone();
                offspring[0].mutation();
                offspring[1].mutation();
            }

            // find out if the mutated individuals are better than the worst in population
            double worstFitness = Integer.MAX_VALUE;
            int worstIdx = -1;
            for (int i = 0; i < population.length; i++) {
                population[i].getFitness();
                if(population[i].fitness < worstFitness) {
                    worstFitness = population[i].fitness;
                    worstIdx = i;
                }
            }
            offspring[0].getFitness();
            if(worstFitness < offspring[0].fitness) {
                population[worstIdx] = offspring[0];
            }

            worstFitness = Integer.MAX_VALUE;
            worstIdx = -1;
            for (int i = 0; i < population.length; i++) {
                population[i].getFitness();
                if(population[i].fitness < worstFitness) {
                    worstFitness = population[i].fitness;
                    worstIdx = i;
                }
            }
            offspring[1].getFitness();
            if(worstFitness < offspring[1].fitness) {
                population[worstIdx] = offspring[1];
            }

            StatData.getStatistics(population);
        }

    }

    /**
     * Initialization of population.
     *
     * @return a number of fitness evaluations calculated during the population initialization
     */
    public static int initializePopulation(Individual[] population) {
        int count = 0;

        // Generate individuals in parent population and calculate their fitness values.
        for (int ind = 0; ind < parameters.getPopSize(); ind++) {
            population[ind] = new Individual();
            population[ind].getFitness(); // Evaluate fitness of new individual.
            count++;
        }

        return count;
    }

    /**
     * @return an index of the worst individual in the current population.
     */
    public static int worstInPopulation(Individual[] population) {
        int worst = 0;

        for (int ind = 1; ind < parameters.getPopSize(); ind++) {
            if (population[worst].fitness > population[ind].fitness)
                worst = ind;
        }

        return worst;
    }

    /**
     * Makes deep copy of individual in population p with index from and stores it to population p2 to index to.
     */
    public static void copyIndividual(Individual source, int to, Individual[] population) {
        population[to] = source;
    }

    /**
     * Tournament selection with number of individuals in tournament.
     *
     * @return index of winner individual.
     */
    public static int tournamentSelection(Individual[] p) {
        int index;
        int winner = (int) (Math.random() * (parameters.getPopSize())); // First member of tournament selection.

        // Choose N individuals and select best one.
        // We chose randomly first individual 3 rows up, so there is only SGA.tournament_size-1 random individuals to check.
        for (int individualIndex = 0; individualIndex < parameters.getTournamentSize() - 1; individualIndex++) {
            index = (int) (Math.random() * (parameters.getPopSize()));
            if (p[index].fitness > p[winner].fitness) {
                winner = index;
            }
        }

        return winner;
    }

    /**
     * Performs selection method according selectionType value.
     *
     * @return array of two indexes selected by selection methods.
     */
    public static int[] selection(Individual[] p) {
        int[] chosen = new int[2];

        switch (parameters.getSelectionType()) {
            case 0:
                chosen[0] = tournamentSelection(p);
                chosen[1] = chosen[0];
                while (chosen[0] == chosen[1])
                    chosen[1] = tournamentSelection(p);
                break;
            default:
                System.err.println("ERROR - Bad value of selectionType variable. Check if its value is 0.");
                System.exit(1);
        }

        return chosen;
    }
}
