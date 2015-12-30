package KUI;

import java.lang.reflect.Array;

public class Bayes extends Classifier {

	// possible values of intensity
	private static int INTENSITY_SIZE = 256;
	
	// it must be a divider of INTENSITY_SIZE
	private static int CLASS_SIZE = 64;
	

	private ApriorProbability apriorProb;


    @Override
    public void learn(double[][] data, char[] labels) {
        apriorProb = new ApriorProbability();
		apriorProb.setClassSize(CLASS_SIZE);
		apriorProb.setIntensitySize(INTENSITY_SIZE);
		apriorProb.setLabels(labels);

		// loop all images
		for(int img = 0; img < data.length; img++) {
			// for each pixel, find what class of intensity it belongs to
			for(int i = 0; i < data[img].length; i++) {
				apriorProb.learnPixel(labels[img], i, getClass(data[img][i]));
			}
		}
    }

    @Override
    public char[] classify(double[][] data) {
        char[] returnValue = new char[data.length];
		// loop all images
		for(int img = 0; img < data.length; img++) {
			
			// save probabilities for each letter
			double probabilities[] = new double[apriorProb.getLetterCnt()];
			for(int j = 0; j < apriorProb.getLetterCnt(); j++) {
				probabilities[j] = 1;
			}

			// calculate probability of each letter
			for(int i = 0; i < data[img].length; i++) {
				for(int j = 0; j < apriorProb.getLetterCnt(); j++) {
					probabilities[j] *= apriorProb.getPixel(j, i, getClass(data[img][i]));
				}
			}

			// find max probability
			int maxLetter = -1;
			double maxProbab = 0;
			for(int i = 0; i < probabilities.length; i++) {
				if(probabilities[i] > maxProbab) {
					maxProbab = probabilities[i];
					maxLetter = i;
				}
			}
			returnValue[img] = apriorProb.getLetter(maxLetter);
		}

		return returnValue;
    }

	/**
	 * return the class based on intensity
	 */
	private int getClass(double intensity) {
		return (int) Math.floor(intensity/CLASS_SIZE);
	}

}

class ApriorProbability {

	private double classSize;
	private int intensitySize;
	private int letterCnt;
	private int[] letters; // array of letters to numerical indexes
	private int[] normalize; // letter counter
	private int[][][] data;

	void setClassSize(int classSize) {
		this.classSize = classSize;
	}

	int getLetterCnt() {
		return letterCnt;
	}

	void setLabels(char[] labels) {
		letters = new int[256]; // as large as ASCII table :-)
		char lastLetter = '\0'; // this is safe value
		int letterCnt = 0;

		for(int i = 0; i < labels.length; i++) {
			if(lastLetter != labels[i]) {
				lastLetter = labels[i];
				letters[letterCnt] = lastLetter;
				letters[lastLetter] = letterCnt++;
			}
		}

		this.letterCnt = letterCnt;

		// initialize data
		int levels = (int) Math.ceil(intensitySize/classSize);
		data = new int[letterCnt][100][levels];
		for(int i = 0; i < letterCnt; i++) {
			for(int j = 0; j < 100; j++) {
				for(int k = 0; k < levels; k++) {
					data[i][j][k] = 1;
				}
			}
		}
	}

	void learnPixel(char letter, int pixel, int classLevel) {
		data[ letters[letter] ][pixel][ classLevel]++;
	}

	int getPixel(int letter, int pixel, int classLevel) {
		return data[letter][pixel][classLevel];
	}

	char getLetter(int letter) {
		return (char) letters[letter];
	}

	void setIntensitySize(int INTENSITY_SIZE) {
		this.intensitySize = INTENSITY_SIZE;
	}
}
