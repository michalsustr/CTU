package KUI;

import java.lang.reflect.Array;

public class BayesNormalized extends Classifier {

	// it must be a divider of 256
	private static int CLASS_SIZE = 64;

	private ApriorProbabilityNormalized apriorProb;


    @Override
    public void learn(double[][] data, char[] labels) {
        apriorProb = new ApriorProbabilityNormalized();
		apriorProb.setClassSize(CLASS_SIZE);
		apriorProb.setLabels(labels);

		// loop all images
		for(int img = 0; img < data.length; img++) {
			// for each pixel, find what class of intensity it belongs to
			for(int i = 0; i < data[img].length; i++) {
				apriorProb.learnPixel(labels[img], i, getClass(data[img][i]));
			}
		}

		apriorProb.normalize();
    }

    @Override
    public char[] classify(double[][] data) {
        char[] returnValue = new char[data.length];
		for(int img = 0; img < data.length; img++) {
			double probabilities[] = new double[apriorProb.getLetterCnt()];
			for(int j = 0; j < apriorProb.getLetterCnt(); j++) {
				probabilities[j] = 1;
			}

			for(int i = 0; i < data[img].length; i++) {
				for(int j = 0; j < apriorProb.getLetterCnt(); j++) {
					probabilities[j] *= apriorProb.getPixel(j, i, getClass(data[img][i]));
				}
			}

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

	private int getClass(double intensity) {
		return (int) Math.floor(intensity/CLASS_SIZE);
	}

}

class ApriorProbabilityNormalized {

	private double classSize;

	// number of letters used to learn
	private int letterCnt;

	// conversion array - from letters to numerical indexes and vice versa
	private int[] letters;

	// letter counter
	private int[] normalize;

	// [letter][pixel][class]
	private double[][][] data;

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
		int levels = (int) Math.ceil(256/classSize);
		data = new double[letterCnt][100][levels];
		for(int i = 0; i < letterCnt; i++) {
			for(int j = 0; j < 100; j++) {
				for(int k = 0; k < levels; k++) {
					data[i][j][k] = 0;
				}
			}
		}

		// fill in normalization table
		normalize = new int[letterCnt];
		for(int i = 0; i < letterCnt; i++) {
			normalize[i] = 0;
		}
		for(int i = 0; i < labels.length; i++) {
			normalize[ letters[labels[i]] ]++;
		}
	}

	void normalize() {
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				for(int k = 0; k < data[i][j].length; k++) {
					// +constant to prevent multiplication by zero
					data[i][j][k] = data[i][j][k]/normalize[i]+0.000001;
				}
			}
		}
	}

	void learnPixel(char letter, int pixel, int classLevel) {
		data[ letters[letter] ][pixel][ classLevel]++;
	}

	double getPixel(int letter, int pixel, int classLevel) {
		return data[letter][pixel][classLevel];
	}

	char getLetter(int letter) {
		return (char) letters[letter];
	}
}
