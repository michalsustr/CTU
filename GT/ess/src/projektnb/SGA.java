//package bia.sga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * This main class represents population and implements evolution process.
 *
 * @author Frantisek Hruska, Jiri Kubalik, Michal sustr
 */
public class SGA {

    static ArrayList<Individual> individuals;
    public static int maxAge;
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
//        uniqueGenomes = new ArrayList<String>();
//        loadInput();
//        Individual top = individuals.get(9);
//        System.out.println("Top: "+top);
//        for (Individual I: individuals) {
//            System.out.println("Score against "+I);
//            double[] score = playGame(top, I);
//            System.out.println(score[0] + " | " + score[1] + (score[0] > score[1] ? "OK" : "NOT OK"));
//        }
    }

    public static void run() throws Exception {
        uniqueGenomes = new ArrayList<String>();

        loadInput();

        bwGenomes = new BufferedWriter(new FileWriter(new File("genomes.csv")));
        bwFractions = new BufferedWriter(new FileWriter(new File("fractions.csv")));


        for (int i = 0; i < SimParams.simSteps; i++) {
            double sum = 0.0;
            for (Individual I: individuals) {
                sum += I.getFraction();
            }
            System.out.println("### Sim step #"+i+", sum of fracs "+sum);
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
                bwGenomes.write(uniqueGenomes.get(genomeWrittenIdx)+"\n");
            }
        }

        // write the new fractions
        boolean isStart = true;
        int i = 0;
        for (String genome: uniqueGenomes) {
            if(!isStart) bwFractions.write(", ");
            isStart = false;

            // find the individuals with the same genome
            double fraction = 0.0;
            for(Individual I : individuals) {
                if(I.getGenesString().equals(genome)) {
                    fraction += I.getFraction();
                }
            }
            bwFractions.write(""+fraction);
            i++;
        }
        if( i <= SimParams.maxSpecies) {
            for (; i < SimParams.maxSpecies; i++) {
                bwFractions.write(", 0.0");
            }
        } else {
            throw new RuntimeException ("too many species");
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
                //System.out.println("Play: "+I.getGenesString()+" X "+J.getGenesString()+" Score "+score[0]+", "+score[1]);
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

            I.setFitness( (I.getScore() - avgScore) / Math.abs(avgScore));
            I.setFraction( (SimParams.growthConstant*I.getFitness() + 1)*I.getFraction());

            // increase the age of population
            for(int i=1;i<maxAge;i++)
            I.setAgeFraction(i,I.getAgeFraction(i-1));
        }
    }

    private static void extinctPopulation() {
        // normalize the fractions if some individuals died out
        double popSize = 0.0;
        for (Individual I : individuals) {
            if(!I.isAlive()) continue;

            popSize += I.getFraction();
        }
        System.out.println("Extinct pop size:"+popSize);
        if(popSize != 1.0) {


            for (Iterator<Individual> it = individuals.iterator(); it.hasNext();) {
                Individual I = it.next();

                if (!I.isAlive()) {
                    it.remove();
                } else {
                    I.setFraction(I.getFraction()/popSize);
                }
            }
        }
    }

    public static void reproducePopulation() {
        ArrayList<Individual> offsprings = new ArrayList<>();
        for (Individual ind : individuals) {
            if(!ind.isAlive()) continue;
            
            
            int mutations = 0;
            double popSize=0.0;
            // mutations if it happens
            if(Math.random() < SimParams.pPM) {
                Individual mutant = ind.clone();
                mutant.pointMutation();
                mutant.setFraction(
                   ind.getFraction()*SimParams.mutantFraction
                );
                mutations++;

                if(!uniqueGenomes.contains(mutant.getGenesString())) {
                    uniqueGenomes.add(mutant.getGenesString());
                }
                offsprings.add(mutant);
            }
            if(Math.random() < SimParams.pGDM) {
                Individual mutant = ind.clone();
                mutant.geneDuplicationMutation();
                mutant.setFraction(
                   ind.getFraction()*SimParams.mutantFraction
                );
                mutations++;

                if(!uniqueGenomes.contains(mutant.getGenesString())) {
                    uniqueGenomes.add(mutant.getGenesString());
                }
                offsprings.add(mutant);
            }
            if(Math.random() < SimParams.pGSM) {
                Individual mutant = ind.clone();
                mutant.geneSplitMutation();
                mutant.setFraction(
                   ind.getFraction()*SimParams.mutantFraction
                );
                mutations++;

                if(!uniqueGenomes.contains(mutant.getGenesString())) {
                    uniqueGenomes.add(mutant.getGenesString());
                }
                offsprings.add(mutant);
            }
            ind.setFraction(ind.getFraction()*(1-mutations*SimParams.mutantFraction));
            ind.setAgeFraction(0, SimParams.offspringFraction);
            
            for(int i=0;i<maxAge;i++)
            	popSize+=ind.getAgeFraction(i);
            for(int i=1;i<maxAge;i++)
            	ind.setAgeFraction(i, ind.getAgeFraction(i)/popSize);
        }

        individuals.addAll(offsprings);
    }

    private static double[] playGame(Individual I,Individual J){
    	double score[]={0,0},result[];
    	for(int i=0;i<maxAge;i++)
    		for(int j=0;j<maxAge;j++){
    			result=playGame(I,J,i,j);
    			score[0]+=result[0]*J.getAgeFraction(j)*I.getAgeFraction(i);
    			score[1]+=result[1]*I.getAgeFraction(i)*J.getAgeFraction(j);
    		}		
    	return score;
    }
    
    
    /**
     * Return score after playing a game of Prisoner's Dilemma
     *
     * @param I
     * @param J
     */
    private static double[] playGame(Individual I, Individual J,int ageI,int ageJ) {
        // tactics
        int[][] T1 = I.getActiveGenome(ageI);
        int[][] T2 = J.getActiveGenome(ageJ);
        // length of history lookup table for each player
        int L1 = historyLength(T1[0].length);
        int L2 = historyLength(T2[0].length);
        // action that each should take (Defect 0, Cooperate 1)
        int A1, A2;
        // overflow mask
        int M1 = (1 << (L1))-1;
        int M2 = (1 << (L2))-1;
        int H1 = M1;
        int H2 = M2;
        int h1=M2;
        int h2=M1;
        double score[] = {0.0, 0.0};
        int N1=0,N2=0,S1=0,S2=0;
        double C1,C2;

        for (int i = 0; i < SimParams.PDGameRounds; i++) {
            if(H1 >= T1[0].length || H2 >= T2[0].length) {
                System.out.println("Problem");
                System.out.println("T1: ");
                for (int j = 0; j < T1.length; j++) {
                    System.out.print(" "+T1[j]);
                }
                System.out.println("");
                System.out.println("T2: ");
                for (int j = 0; j < T2.length; j++) {
                    System.out.print(" "+T2[j]);
                }
                System.out.println("");
                System.out.println("H1: "+H1);
                System.out.println("H2: "+H2);
                System.out.println("L1: "+L1);
                System.out.println("L2: "+L2);
                System.out.println("M1: "+M1);
                System.out.println("M2: "+M2);
            }
            A1=T1[S1][H1];
            A2=T2[S2][H2];
            
            //score[0]=getScore(A1,A2);
            //score[1]=getScore(A2,A1);
            score[0]+=SimParams.altruisticCost*A1;
            score[1]+=SimParams.altruisticCost*A2;
            // shift history left
            H1 <<= 1;
            H2 <<= 1;
            // set the current action that was taken
            H1 |= A1;
            H2 |= A2;
            // trim the overflow
            h1=H1&M2;
            h2=H2&M1;
            H1 &= M1;
            H2 &= M2;
            //compute number of round without cooperation
            N1=computeN(H2,N1);
            N2=computeN(H1,N2);
            C1=SimParams.k[ageI]*N1;
            C2=SimParams.k[ageJ]*N2;
            score[0]-=C1;
            score[1]-=C2;
            //update state
            if(i>M1 && T1[S1+2][h2]==1)
            	S1=1-S1;
            if(i>M2 && T2[S1+2][h1]==1)
            	S2=1-S2;
            /*int sum=0;
            for(int j=0;j<T1[S1+2].length;j++){
            	if(T1[S1+2][j]==1)
            		sum+=(1 << (T1[S1+2].length-1-j));
            }
            if(sum==H2)
            	S1=1-S1;
            sum=0;
            for(int j=0;j<T2[S2+2].length;j++){
            	if(T2[S2+2][j]==1)
            		sum+=(1 << (T2[S2+2].length-1-j));
            }
            if(sum==H1)
            	S2=1-S2;*/
        }

        return score;
    }
    
    private static double getScore(int A1,int A2){
    	double scores[][]=new double[2][2];
    	scores[0][0]=1.0;
    	scores[0][1]=5.0;
    	scores[1][0]=0.0;
    	scores[1][1]=3.0;
    	return scores[A1][A2];
    }

    private static int historyLength(int x) {
        for (int i = 1; i < SimParams.maxGenomeLength_2; i++) {
            if((x & (1 << i)) == (1 << i)) {
                return i;
            }
        }
        return -1;
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
            ArrayList<int[][]> genes = new ArrayList<int[][]>();
            int[][] genome=new int[4][];
            for (int a = 0; a < A; a++) {
            	for(int j=0; j<4;j++){
                int G = sc.nextInt();
                genome[j]=new int[G];
                for (int g = 0; g < G; g++) {
                    genome[j][g] = sc.nextInt();
                }
            	}
            	for(int j=0;j<genome.length;j++)
            		for(int g=0;g<genome[j].length;g++)
            			System.out.print(genome[j][g]);
            	System.out.println();
            	genes.add(genome.clone());
            }
            Individual ind = new Individual(genes);
            ind.setFitness(1.0);
            ind.setFraction(1.0 / (N));
            for (int a = 0; a < A; a++) {
                ind.setAgeFraction(a,1.0/A);
                if(!uniqueGenomes.contains(ind.getGenesString())) {
                    uniqueGenomes.add(ind.getGenesString());
                }
            }
            System.out.println("Ind: "+ind.getGenesString());
                individuals.add(ind);
            

        }
    }

    public static double getAverageScore() {
        double sc = 0;
        for (Individual I : individuals) {
            if(I.isAlive()) {
                sc+=I.getScore()*I.getFraction();
            }
        }
        return sc;
    }

    private static int computeN(int H,int N){
    	if(H==0) {
            return N+1;
        }

        int n;
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
