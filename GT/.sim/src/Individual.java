//package bia.sga;

import java.util.Arrays;

/**
 * @author Michal Sustr
 */
public class Individual {

    /**
     * Genes of individual binary represented as list of actions D (defect, 0) and C (cooperate, 1)
     * based on the playing history.
     *
     * First dimension corresponds to the number of ages the individuals can have
     * and the second to the genes of the corresponding age.
     *
     * This is a jagged array.
     */
    private int[][] mGenes;

    /**
     * Fitness of individual.
     */
    private double mFitness;


    /**
     * Score of individual.
     */
    private double mScore;


    /**
     * The fraction of the individual in the current population size
     */
    private double mFraction;

    /**
     * Current age
     */
    private int mAge = 0;

    /**
     * Cached genes array into a string
     */
    private String mGenesString;


    /**
     * Constructor. Generates random individual.
     */
    public Individual(int[][] genes) {
        this.mGenes = genes;

        // cache the gene string value
        mGenesString = "";
        for (int i = 0; i < mGenes.length; i++) {
            mGenesString  += "A"+i+"-";
            for (int j = 0; j < mGenes[i].length; j++) {
                mGenesString += mGenes[i][j];
            }
            mGenesString +=" ";
        }
    }

    /**
     * Cloning method for creating deep copies of individual instances.
     *
     * @return
     */
    @Override
    public Individual clone() {
        int[][] cloneGenes = new int[mGenes.length][mGenes[0].length];
        for (int i = 0; i < cloneGenes.length; i++) {
            cloneGenes[i] = Arrays.copyOf(mGenes[i], mGenes[i].length);
        }

        Individual i = new Individual(cloneGenes);
        return i;
    }

    /**
     * Fitness function calculation.
     */
    public double getFitness() {
        return mFitness;
    }
    public void setFitness(double mFitness) {
        this.mFitness = mFitness;
    }

    public double getScore() {
        return mScore;
    }

    public void setScore(double mScore) {
        this.mScore = mScore;
    }

    public double getFraction() {
        return mFraction;
    }

    public void setFraction(double mFraction) {
        this.mFraction = mFraction;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int mAge) {
        this.mAge = mAge;
    }

    public boolean isAlive() {
        return (mFraction >= SimParams.xPopSize && mAge < SGA.maxAge);
    }

    private int getGenomeLength() {
        int l=0;
        for (int i = 0; i < mGenes.length; i++) {
            l += mGenes[i].length;
        }
        return l;
    }

    public void pointMutation() {
        int k = (int) (Math.random() * getGenomeLength());
        int i,j = -1;
        int l = 0; // cumsum for the divider
        // figure out what age i and gene j the index k belongs to
        for (i = 0; i < mGenes.length; i++) {
            l+=mGenes[i].length;
            if(k / l == 0) {
                j = k-l+mGenes[i].length;
                break;
            }
        }

        mGenes[i][j] = (int) Math.round(Math.random());
    }

    public void geneDuplicationMutation() {
        if(mGenes[0].length == 32) return;

        for (int i = 0; i < mGenes.length; i++) {
            int[] dup = new int[mGenes[i].length*2];
            for (int j = 0; j < mGenes[i].length; j++) {
                dup[j] = mGenes[i][j];
                dup[j+mGenes[i].length] = mGenes[i][j];
            }
            mGenes[i] = dup;
        }
    }

    public void geneSplitMutation() {
        if(mGenes[0].length == 2) return;

        for (int i = 0; i < mGenes.length; i++) {
            int[] split = new int[mGenes[i].length/2];
            for (int j = 0; j < split.length; j++) {
                split[j] = mGenes[i][j];
            }
            mGenes[i] = split;
        }
    }

    public int[] getActiveGenome() {
        return this.mGenes[mAge];
    }

    public String getGenesString() {
        return mGenesString;
    }

    @Override
    public String toString() {
        return "I{"+ mAge + ", "+ mGenesString + '}';
    }

    String getActiveGenomeString() {
        String r = "";
        for (int j = 0; j < mGenes[mAge].length; j++) {
             r += mGenes[mAge][j];
        }
        return r;
    }


}
