//package bia.sga;

/**
 * Created by michal on 12/27/14.
 */
public class SimParams {
    public static double popSize = 1000;
    public static double xPopSize = 1.0/popSize;
    public static double simSteps = 500;

    public static double pPM = 0.2;               // probability of point mutation
    public static double pGDM = 0.125;              // probability of gene duplication mutation
    public static double pGSM = 0.025;              // probability of gene split mutation
    public static double growthConstant = 1.5;
    public static int reproductionAge = 2;
    public static double offspringFraction = 2.0/3.0;
    public static int PDGameRounds = 200;   // # of Prisonner's Dilemma rounds
    public static int maxGenomeLength_2 = 6; // maximum genome length in the exponent of power of 2
    public static int maxGenomeLength = (int) Math.pow(2, maxGenomeLength_2);
    public static double k[]={0.6,1,2};
    public static int altruisticCost=-3;
    public static double maxCValue=10.0;
    public static int maxSpecies = 1000;
    public static double mutantFraction = 1.0/5.0;
}
