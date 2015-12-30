package bia.sga;

import static bia.sga.StatData.fbest;
import static bia.sga.StatData.indbest;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * This main class represents population and implements evolution process.
 *
 * @author Frantisek Hruska, Jiri Kubalik
 */
public class SGA {

    /**
     * Old and new population.a
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
    public static double[][] data;
    public static double minX, minY, maxX, maxY;
    public static int numSegments;
    public static double rangeMinX;
    public static double rangeMaxX;
    public static double rangeMinY;
    public static double rangeMaxY;
    private static GraphFrame f;

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

        loadInput();
        parameters.setGenesPerValue((numSegments+1)*2);

        int totEvals = 0;

        // Allocate memory for arrays
        newPop = new Individual[parameters.getPopSize()];
        oldPop = new Individual[parameters.getPopSize()];
        offspringPop = new Individual[2];

        // Initialise population.
        totEvals = initializePopulation(oldPop);

        createGraph();

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
     * Evolves the
     *
     * @param population either using a generational or steady-state replacement
     * strategy.
     *
     * @param population the population to be evolved.
     * @param totEvals a number of fitness function evaluations that have been
     * calculated during an initialization phase.
     */
    public static void evolvePopulation(Individual[] population, int totEvals) {

        switch (parameters.getReplacementType()) {
            case 0:
                System.out.println("\nGenerational replacement\n");
                evolvePopGenerational(population, totEvals);
                break;
            case 1:
                System.out.println("\nSteady-state replacement\n");
                evolvePopSteadyState(population, totEvals);
                break;
            default:
                System.err.println("ERROR - Bad value of replacementType variable. Check if its value is 0 or 1.");
                System.exit(1);
        }
    }

    /**
     * Evolves the
     *
     * @param population using a generational replacement strategy.
     *
     * @param population the population to be evolved.
     * @param totEvals a number of fitness function evaluations that have been
     * calculated during an initialization phase.
     */
    public static void evolvePopGenerational(Individual[] population, int totEvals) {
        int generationCounter = 0;
        // While the termination criterion is not satisfied ...
        while (totEvals < parameters.getEvaluations()) {
            int chroms_generated = 0;

            // Elitism. Copy best individual from old population.
            newPop[chroms_generated++] = population[StatData.indbest].clone();

            // While new population is not filled...
            while (chroms_generated < parameters.getPopSize()) {
                int[] par = selection(population);
                Individual parent1 = population[par[0]].clone();
                Individual parent2 = population[par[1]].clone();
                // Creates two new individuals.
                if (Math.random() < parameters.getPC()) {
                    offspringPop = parent1.crossover(parent2);
                    offspringPop[0].mutation();
                    offspringPop[1].mutation();
                } else {
                    offspringPop[0] = parent1;
                    offspringPop[1] = parent2;
                    offspringPop[0].mutation();
                    offspringPop[1].mutation();
                }
                // Calculates fitness of new individuals.
                offspringPop[0].getFitness();
                offspringPop[1].getFitness();
                totEvals += 2;  // Increase number of evaluations done.

                // Get offspring to new parent population.
                newPop[chroms_generated++] = offspringPop[0];
                if (chroms_generated < parameters.getPopSize()) {
                    newPop[chroms_generated++] = offspringPop[1];
                }
            }

            // Swap oldPop and newPop
            Individual[] tempPop = new Individual[parameters.getPopSize()];
            tempPop = population;
            population = newPop;
            newPop = population;

            // Generate statistic data
            StatData.getStatistics(population);
            System.out.println(""+StatData.favg+","+StatData.fbest);

            generationCounter++;
            if (generationCounter == parameters.getEvaluations() / 20) {
                SGA.drawIndividual(StatData.bestIndividual);
                generationCounter = 0;
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SGA.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        System.out.println("--- Final report ---");
        //--- Final report
        // Generate statistic data
        StatData.getStatistics(population);
        // Print results
        StatData.reportResults(totEvals);
        StatData.printBest(population);
    }

    /**
     * Evolves the
     *
     * @param population using a steady-state replacement strategy.
     *
     * @param population the population to be evolved.
     * @param totEvals a number of fitness function evaluations that have been
     * calculated during an initialization phase.
     */
    public static void evolvePopSteadyState(Individual[] population, int totEvals) {

        int generationCounter = 0;
        double allBestFitness = Double.MAX_VALUE;
        double[] bestChromosome;

        // While the termination criterion is not satisfied ...
        while (totEvals < parameters.getEvaluations()) {
            int replacementIndex;   //--- index of the individual to be replaced by a newly generated offspring

            int[] par = selection(population);
            Individual parent1 = population[par[0]].clone();
            Individual parent2 = population[par[1]].clone();
            // Creates two new individuals
            if (Math.random() < parameters.getPC()) {
                offspringPop = parent1.crossover(parent2);  //--- create offspring by a crossover
                offspringPop[0].mutation();                 //--- mutate the offspring
                offspringPop[1].mutation();
            } else {
                offspringPop[0] = parent1;
                offspringPop[1] = parent2;
                offspringPop[0].mutation();     //--- just mutate the parents
                offspringPop[1].mutation();
            }
            // Calculates fitness of new individuals
            offspringPop[0].getFitness();
            offspringPop[1].getFitness();
            totEvals += 2;  // Increase number of evaluations done

            // Find an individual to be replaced by the first child and replace it
            replacementIndex = worstInPopulation(population);
            population[replacementIndex] = offspringPop[0];
            // Find an individual to be replaced by the second child and replace it
            replacementIndex = worstInPopulation(population);
            population[replacementIndex] = offspringPop[1];

            //--- Regular reports

            // Generate statistic data
            StatData.getStatistics(population);
            System.out.println(""+StatData.favg+","+StatData.fbest);

            generationCounter++;
            if (generationCounter == parameters.getEvaluations() / 20) {
                SGA.drawIndividual(StatData.bestIndividual);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SGA.class.getName()).log(Level.SEVERE, null, ex);
                }
                generationCounter = 0;
            }
        }

        System.out.println("--- Final report ---");
        // Generate statistic data
        StatData.getStatistics(population);
        // Print results
        StatData.reportResults(totEvals);
        StatData.printBest(population);
    }

    /**
     * Initialization of population.
     *
     * @return a number of fitness evaluations calculated during the population
     * initialization
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
            if (population[ind].fitness > population[worst].fitness) {
                worst = ind;
            }
        }

        return worst;
    }

    public static int bestInPopulation(Individual[] population) {
        int best = 0;

        for (int ind = 1; ind < parameters.getPopSize(); ind++) {
            if (population[ind].fitness < population[best].fitness) {
                best = ind;
            }
        }

        return best;
    }

    /**
     * Makes deep copy of individual in population p with index from and stores
     * it to population p2 to index to.
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
                while (chosen[0] == chosen[1]) {
                    chosen[1] = tournamentSelection(p);
                }
                break;
            default:
                System.err.println("ERROR - Bad value of selectionType variable. Check if its value is 0.");
                System.exit(1);
        }

        return chosen;
    }

    private static void createGraph() {
        f = new GraphFrame();
        f.setVisible(true);
    }

    public static void drawIndividual(Individual i) {
        f.drawIndividual(i);
    }

    private static void loadInput() {
        // load input

        System.out.println("Enter data (#ofPoints #ofSegments, then the X Y of all points");
        Scanner sc = new Scanner(System.in);

        data = new double[sc.nextInt()][2];
        numSegments = sc.nextInt();
        minX = Double.MAX_VALUE;
        minY = Double.MAX_VALUE;
        maxX = Double.MIN_VALUE;
        maxY = Double.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            data[i][0] = sc.nextDouble();
            data[i][1] = sc.nextDouble();

            // X point
            if (data[i][0] < minX) {
                minX = data[i][0];
            }
            if (data[i][0] > maxX) {
                maxX = data[i][0];
            }

            // Y point
            if (data[i][1] < minY) {
                minY = data[i][1];
            }
            if (data[i][1] > maxY) {
                maxY = data[i][1];
            }
        }

        rangeMinX = Math.max(0, minX - (maxX - minX) * 0.5);
        rangeMaxX = maxX + (maxX - minX) * 0.5;
        rangeMinY = Math.max(0, minY - (maxY - minY) * 0.5);
        rangeMaxY = maxY + (maxY - minY) * 0.5;


    }
}
