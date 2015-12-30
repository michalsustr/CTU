//package bia.sga;

import java.util.Arrays;
import java.util.ArrayList;
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
    private ArrayList<int[][]> mGenes;

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
    private double ageFraction [];

    /**
     * Cached genes array into a string
     */
    private String mGenesString;


    /**
     * Constructor. Generates random individual.
     */
    public Individual(ArrayList<int[][]> genes) {
        this.mGenes = genes;
        this.ageFraction=new double[SGA.maxAge];
        setAgeFraction(0,1.0);
        for(int i=1;i<SGA.maxAge;i++)
        	setAgeFraction(i,0.0);
        // cache the gene string value
        mGenesString = "";
        for (int i = 0; i < genes.size(); i++) {
            mGenesString  += "A"+i+"-";
            for (int j = 0; j < genes.get(i).length; j++) {
            	mGenesString += " G"+j+":";
        		char[] cmap = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e','f'};
        		int cbuf = 0;
        		for(int k=0; k<genes.get(i)[j].length;k++)
        		{
        			cbuf += genes.get(i)[j][k] << (3-(k%4));
        			if((k+1)%4==0)
        			{
        				mGenesString += cmap[cbuf];
        				cbuf=0;
        			}
        		}
            	if(genes.get(i)[j].length%4!=0)
            		mGenesString += cmap[cbuf/4];
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
    public Individual clone(){ 
        ArrayList<int[][]> cloneGenes = new ArrayList<int[][]>();
        for (int i = 0; i < mGenes.size(); i++) {
        	int[][] genes=new int[mGenes.get(i).length][];
        	for(int j=0;j < mGenes.get(i).length;j++){
            genes[j] = Arrays.copyOf(mGenes.get(i)[j], mGenes.get(i)[j].length);
        	}
        	cloneGenes.add(genes);
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

    public double getAgeFraction(int i) {
        return ageFraction[i];
    }

    public void setAgeFraction(int i,double fraction) {
        this.ageFraction[i]=fraction;
    }

    public boolean isAlive() {
        return (mFraction >= SimParams.xPopSize*2);
    }

    private int getGenomeLength() {
        int l=0;
        for (int i = 0; i < mGenes.size(); i++) {
        	for(int j=0;j<mGenes.get(i).length;j++)
            l += mGenes.get(i)[j].length;
        }
        return l;
    }

    public void pointMutation() {
        int k = (int) (Math.random() * getGenomeLength());
        int i,h=0,j = -1;
        int l = 0; // cumsum for the divider
        // figure out what age i and gene j the index k belongs to
        for (i = 0; i < mGenes.size(); i++) {
            for(h = 0;h < mGenes.get(i).length;h++){
        	l+=mGenes.get(i)[h].length;
            if(k / l == 0) {
                j = k-l+mGenes.get(i)[h].length;
                break;
            }
            }
            if(h<mGenes.get(i).length)
            	break;
        }

        mGenes.get(i)[h][j] = (int) Math.round(Math.random());
    }

    public void geneDuplicationMutation() {
        if(mGenes.get(0)[0].length == 32) return;

        for (int i = 0; i < mGenes.size(); i++) {
        	for(int k=0; k<mGenes.get(i).length;k++){
            int[] dup = new int[mGenes.get(i)[k].length*2];
            for (int j = 0; j < mGenes.get(i)[k].length; j++) {
                dup[j] = mGenes.get(i)[k][j];
                dup[j+mGenes.get(i)[k].length] = mGenes.get(i)[k][j];
            }
            mGenes.get(i)[k] = dup;
        }
        }
    }

    public void geneSplitMutation() {
        if(mGenes.get(0)[0].length == 2) return;

        for (int i = 0; i < mGenes.size(); i++) {
        	for(int k=0; k < mGenes.get(i).length;k++){
            int[] split = new int[mGenes.get(i)[k].length/2];
            for (int j = 0; j < split.length; j++) {
                split[j] = mGenes.get(i)[k][j];
            }
            mGenes.get(i)[k] = split;
        }
        }
    }

    public int[][] getActiveGenome(int mAge) {
        return this.mGenes.get(mAge);
    }

    public String getGenesString() {
        return mGenesString;
    }

    @Override
    public String toString() {
        return "I{"+ mGenesString + '}';
    }



}
