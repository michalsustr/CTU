//package bia.sga;

/**
 * Created by michal on 12/27/14.
 */
public class SimParams {
    public static double popSize = 1000;
    public static double xPopSize = 1.0/popSize;
    public static double simSteps = 100;

    public static double pPM;               // probability of point mutation
    public static double pGDM;              // probability of gene duplication mutation
    public static double pGSM;              // probability of gene split mutation
    public static double growthConstant;
    public static int reproductionAge = 2;
    public static double offspringFraction = 2.0/3.0;
    public static int PDGameRounds = 100;   // # of Prisonner's Dilemma rounds
    public static int maxGenomeLength_2 = 5; // maximum genomre length in the exponent of power of 2
    public static int maxGenomeLength = (int) Math.pow(2, maxGenomeLength_2);
    public static double k[]={0.2,0.3,0.5};
    public static int altruisticCost=-3;
}
