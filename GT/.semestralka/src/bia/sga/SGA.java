//package bia.sga;

import bia.sga.Individual;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This main class represents population and implements evolution process.
 *
 * @author Frantisek Hruska, Jiri Kubalik, Michal sustr
 */
public class SGA {

    static ArrayList<Individual> individuals;
    private static int maxAge;
    private static BufferedWriter bwGenomes;
    private static BufferedWriter bwFractions;

    // mapping of genome sequences to integers
    // to ensure distinct writing of data
    static ArrayList<String> uniqueGenomes;
    private static int genomeWrittenIdx = 0;

    /**
     * Main function.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        run();
    }

    public static void run() throws Exception {
        uniqueGenomes = new ArrayList<String>();

        loadInput();

        bwGenomes = new BufferedWriter(new FileWriter(new File("genomes.csv")));
        bwFractions = new BufferedWriter(new FileWriter(new File("fractions.csv")));

        for (int i = 0; i < SimParams.simSteps; i++) {
            playTournament();
            extinctPopulation();
            reproducePopulation();
            writeResults();
        }

        bwGenomes.close();
        bwFractions.close();
    }

    private static void writeResults() throws IOException {
        // write new genomes
        if(genomeWrittenIdx != uniqueGenomes.size()-1) {
            for (; genomeWrittenIdx < uniqueGenomes.size(); genomeWrittenIdx++) {
                if(genomeWrittenIdx != 0) bwGenomes.write(", ");

                bwGenomes.write(uniqueGenomes.get(genomeWrittenIdx));
            }
        }

        // write the new fractions
        boolean isStart = true;
        for (String genome: uniqueGenomes) {
            // find the individuals with the same genome
            double fraction = 0.0;

            for(Individual I : individuals) {
                if(I.getGenesString().equals(genome)) {
                    fraction += I.getFraction();
                }
            }

            bwFractions.write(""+fraction);
            if(!isStart) bwFractions.write(", ");
            isStart = false;
        }
        bwFractions.write("\n");
    }

    public static void playTournament() {
        // nullify scores of all individuals
        for (Individual I : individuals) {
            I.setScore(0.0);
        }

        // play games
        for (int i = 0; i < individuals.size(); i++) {
            for (int j = i; j < individuals.size(); j++) {
                Individual I = individuals.get(i);
                Individual J = individuals.get(j);

                if(!I.isAlive() || !J.isAlive()) continue;

                double[] score = playGame(I, J);
                if(i != j) {
                    I.setScore(I.getScore() + score[0]*J.getFraction());
                    J.setScore(J.getScore() + score[1]*I.getFraction());
                } else {
                    I.setScore(I.getScore() + score[0]*I.getFraction());
                }
            }
        }

        // calculate fitness and grow fractions
        double avgScore = getAverageScore();
        for (Individual I : individuals) {
            if(!I.isAlive()) continue;
            I.setFitness(I.getScore() - avgScore);
            I.setFraction( (SimParams.growthConstant * I.getFitness() + 1)* I.getFraction());

            // increase the age of population
            I.setAge(I.getAge()+1);
        }
    }

    private static void extinctPopulation() {
        // normalize the fractions if some individuals died out
        double popSize = 0.0;
        int aliveInds = 0;
        for (Individual I : individuals) {
            if(!I.isAlive()) continue;
            if(I.getAge() == maxAge) {
                I.setFraction(0);
                continue;
            }
            popSize += I.getFraction();
            aliveInds++;
        }

        if(popSize < 1.0) {
            double diff = (1.0-popSize) / aliveInds;
            for (Individual I : individuals) {
                I.setFraction(I.getFraction() + diff);
            }
        }
    }

    public static void reproducePopulation() {
        for (Individual ind : individuals) {
            if(!ind.isAlive()) continue;
            if(ind.getAge() != SimParams.reproductionAge) continue;

            Individual offspring = ind.clone();

            // mutations if it happens
            if(Math.random() < SimParams.pPM) {
                offspring.pointMutation();
            }
            if(Math.random() < SimParams.pGDM) {
                offspring.geneDuplicationMutation();
            }
            if(Math.random() < SimParams.pGSM) {
                offspring.geneSplitMutation();
            }

            offspring.setFraction(ind.getFraction() * SimParams.offspringFraction);
            // the offspring "eats" the fraction from parents
            ind.setFraction(ind.getFraction() - offspring.getFraction());
            if(!uniqueGenomes.contains(ind.getGenesString())) {
                uniqueGenomes.add(ind.getGenesString());
            }
            individuals.add(offspring);
        }
    }


    /**
     * Return score after playing a game of Prisoner's Dilemma
     *
     * @param I
     * @param J
     */
    private static double[] playGame(Individual I, Individual J) {
        // tactics
        int[] T1 = I.getActiveGenome();
        int[] T2 = J.getActiveGenome();
        // length of history lookup table for each player
        int L1 = historyLength(T1.length);
        int L2 = historyLength(T2.length);
        // history lookup
        int H1 = 0;
        int H2 = 0;
        // action that each should take (Defect 0, Cooperate 1)
        int A1, A2;
        // overflow mask
        int M1 = (1 << (L1+1))-1;
        int M2 = (1 << (L2+1))-1;
        double score[] = {0.0, 0.0};
        int N1=0,N2=0,m;
        double C1,C2;
        for (int i = 0; i < SimParams.PDGameRounds; i++) {
            A1=T1[H1];
            A2=T2[H2];
            score[0]+=SimParams.altruisticCost*A1;
            score[1]+=SimParams.altruisticCost*A2;
            // shift history left
            H1 <<= 1;
            H2 <<= 1;
            // set the current action that was taken
            H1 |= A1;
            H2 |= A2;
            // trim the overflow
            H1 &= M1;
            H2 &= M2;
            //compute number of round without cooperation
            N1=computeN(H1,N1);
            N2=computeN(H2,N2);
            C1=Math.exp(SimParams.k[I.getAge()]*N1)-1;
            C2=Math.exp(SimParams.k[J.getAge()]*N2)-1;
            score[0]-=C1;
            score[1]-=C2;
        }

        return score;
    }

    private static int historyLength(int x) {
        for (int i = 1; i < SimParams.maxGenomeLength_2; i++) {
            if((x & (1 << i)) == (1 << i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return an index of the worst individual in the current population.
     */
    public static int worstInPopulation(Individual[] population) {
        int worst = 0;

        for (int ind = 1; ind < population.length; ind++) {
            if (population[ind].getFitness() > population[worst].getFitness()) {
                worst = ind;
            }
        }

        return worst;
    }

    public static int bestInPopulation(Individual[] population) {
        int best = 0;

        for (int ind = 1; ind < population.length; ind++) {
            if (population[ind].getFitness() < population[best].getFitness()) {
                best = ind;
            }
        }

        return best;
    }


    private static void loadInput() {
        // load input
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter number of starting individuals:");
        int N = sc.nextInt();
        System.out.println("Enter number of ages");
        int A = maxAge = sc.nextInt();
        System.out.println("Enter individuals (one row per age, first # is # of genes)");

        individuals = new ArrayList<Individual>(N);

        for (int i = 0; i < N; i++) {
            int[][] genes = new int[A][];
            for (int a = 0; a < A; a++) {
                int G = sc.nextInt();
                for (int g = 0; g < G; g++) {
                    genes[a][g] = sc.nextInt();
                }
            }
            Individual ind = new Individual(genes);
            ind.setFitness(1.0);
            ind.setFraction(1.0 / SimParams.popSize);
            if(!uniqueGenomes.contains(ind.getGenesString())) {
                uniqueGenomes.add(ind.getGenesString());
            }
            individuals.add(ind);
        }
    }

    public static double getAverageScore() {
        double sc = 0;
        for (Individual I : individuals) {
            sc+=I.getScore()*I.getFraction();
        }
        return sc;
    }
    
    private static int computeN(int H,int N){
    	int n;
    	if(H==0)
    		return N+1;
    	n=-H;
    	n^=H;
    	n=-n;
    	int i=0;
    	while(n>2){
    		n=n/2;
    		i++;
    	}
    	return i;
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
