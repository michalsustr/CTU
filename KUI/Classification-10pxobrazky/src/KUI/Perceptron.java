package KUI;

import java.util.LinkedHashMap;

/**
 * Class implementing Linear perceptron classifier
 *
 * @author Jan Stiborek, email: stiborek@labe.felk.cvut.cz
 *
 */
public class Perceptron extends Classifier {

    /**
     * Weights gathered from training data
     */
    private double[][] w;
    private double[] b;
    /**
     * HashMaps of each characters for easier indexing
     */
    private LinkedHashMap<Character, Integer> labelsPos = new LinkedHashMap<Character, Integer>();
    private LinkedHashMap<Integer, Character> labelsPosRev = new LinkedHashMap<Integer, Character>();

    public Perceptron() {
    }

    @Override
    public void learn(double[][] data, char[] labels) {
        for (char l : labels) {
            if (!labelsPos.containsKey(l)) {
                int id = labelsPos.size();
                labelsPos.put(l, id);
                labelsPosRev.put(id, l);
            }
        }

        // Setting default values
        w = new double[labelsPos.size()][data[0].length];
        b = new double[labelsPos.size()];

        // Flag if classifier is correctly trained
        boolean missed = true;

        // Repeat training until error-free classification is found
        while (missed) {

            missed = false;
            // For each element in training set...
            for (int i = 0; i < data.length; i++) {
                // ...classify this element
                int classId = classify(data[i]);
                char classification = labelsPosRev.get(classId);

                int corrClassId = labelsPos.get(labels[i]);

                // If the element was missclassified alter weights and set
                // variable missed to true
                if (classification != labels[i]) {
                    missed = true;

                    // Increase weights in correct perceptron
                    w[corrClassId] = sum(w[corrClassId], data[i], 1);
                    b[corrClassId]++;

                    // Decrease weights in incorect perceptron
                    w[classId] = sum(w[classId], data[i], -1);
                    b[classId]--;
                }
            }
        }

        state = States.CLEVER;

    }

    /**
     * Method for classifying single character (single row from data matrix)
     *
     * This methods basically search maximum from w[s]*data+b for each s from
     * the set of possible classifications i.e. from the set of perceptrons
     *
     * @param data
     * @return
     */
    private int classify(double[] data) {
        double maxG = -Double.MAX_VALUE;
        int charId = -1;

        for (int i = 0; i < w.length; i++) {
            double g = scalarMult(w[i], data) + b[i];
            if (g > maxG) {
                maxG = g;
                charId = i;
            }
        }
        return charId;
    }

    @Override
    public char[] classify(double[][] data) {
        if (state != States.CLEVER) {
            System.err.println("Classifier is not trained");
            return null;
        }

        char[] retVal = new char[data.length];
        for (int i = 0; i < data.length; i++) {
            retVal[i] = labelsPosRev.get(classify(data[i]));
        }

        return retVal;
    }

    private double scalarMult(double[] a, double[] b) {
        double retVal = 0;
        for (int i = 0; i < a.length; i++) {
            retVal += a[i] * b[i];
        }
        return retVal;
    }

    private double[] sum(double[] a, double[] b, double alpha) {
        double[] retVal = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            retVal[i] = a[i] + alpha * b[i];
        }
        return retVal;
    }

}