//package bia.sga;

/**
 * Created by michal on 12/27/14.
 */
public class SimParams {
    public static double popSize = 1000;
    public static double xPopSize = 1.0/popSize;
    public static double simSteps = 400;

    public static double pPM = 0.05;               // probability of point mutation
    public static double pGDM = 0.1;              // probability of gene duplication mutation
    public static double pGSM = 0.1;              // probability of gene split mutation
    public static double growthConstant = 0.3;
    public static int reproductionAge = 1;          // from 0
    public static double offspringFraction = 2.0/3.0;
    public static int PDGameRounds = 2000;   // # of Prisonner's Dilemma rounds
    public static int maxGenomeLength_2 = 10; // maximum genomre length in the exponent of power of 2
    public static int maxGenomeLength = (int) Math.pow(2, maxGenomeLength_2);
    public static double k[]={0.6,1,1.5};   // 5, 3 and 2 rounds
    //public static double k[]={0.1,0.1,0.1};
    public static int altruisticCost=-3;
    public static double maxCValue=10.0;
    public static int maxSpecies = 200;
    public static double mutantFraction = 1.0/5.0;
    static double minSpeciesIndividualFraction = xPopSize*2;
}
