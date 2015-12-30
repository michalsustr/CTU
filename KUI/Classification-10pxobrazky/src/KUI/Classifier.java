package KUI;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Formatter;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * @author Jan Stiborek, email: stiborek@labe.felk.cvut.cz
 *
 *         This is basic class which have to extends all classifiers. Class
 *         contains static methods for loading data from files (@sse
 *         {@link Classifier#loadMatrix(String)} and
 *         {@link Classifier#loadLabels(String)}
 *
 *         For correct implementation methods
 *         {@link Classifier#learn(double[][], char[])} and
 *         {@link Classifier#classify(double[][])} must be implemented
 */
abstract public class Classifier {

    private static final String DATA_OUTPUT_FORMAT = "%-3d";
    private static final String LABEL_OUTPUT_FORMAT = "%-3c";

    /**
     * States of classifier
     */
    public enum States {

        DUMB, CLEVER
    }
    /**
     * State of classifier Default state is {@link Classifier.States#DUMB} which
     * means that classifier is not trained. This field should be set to
     * {@link Classifier.States#CLEVER} after running method
     * {@link Classifier#learn(double[][], char[])}
     *
     */
    protected States state = States.DUMB;

    /**
     * Loading labels from file Method throws IOException in case that file is
     * empty or has wrong format.
     *
     * Input data file has to be in following format:
     *
     * N M
     * data_{1,1} data_{1,2} data_{1,3} ... ... ... ... data_{1,M}
     * ...
     * ...
     * ...
     * data_{N,1} data_{N,2} data_{N,3} ... ... ... ... data_{N,M}
     *
     *
     * @param dataFileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static char[] loadLabels(String dataFileName)
            throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFileName));

        String line = reader.readLine();
        if (line == null) {
            throw new IOException(
                    "Data file has wrong format - first line does not contains size of following data.");
        }

        int numOfRows = Integer.parseInt(line);

        char[] labels = new char[numOfRows];
        int i = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() != 1) {
                throw new IOException(
                        "Line "
                        + (i + 1)
                        + " has wrong format. Only single character per line is allowed.");
            }
            labels[i] = line.charAt(0);
            i++;
        }

        reader.close();
        return labels;
    }

    /**
     * Loading matrix representing images from file. Method throws IOException
     * in case that file is
     * empty or has wrong format.
     *
     * Input data file has to be in following format:
     *
     * N
     * letter_1
     * letter_2
     * ...
     * ...
     * ...
     * leter_N
     *
     *
     * @param dataFileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected static double[][] loadMatrix(String dataFileName)
            throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(dataFileName));

        String line = reader.readLine();
        if (line == null) {
            throw new IOException(
                    "Data file has wrong format - first line does not contains size of following data.");
        }

        String nums[] = line.split("\\s");
        int numOfRows = Integer.parseInt(nums[0]);
        int numOfCols = Integer.parseInt(nums[1]);

        double[][] data = new double[numOfRows][numOfCols];
        int i = 0;

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            String[] d = line.split("\\s+");

            if (d.length != numOfCols) {
                throw new IOException("Line " + (i + 1)
                        + " has wrong number of elements.");
            }

            for (int j = 0; j < d.length; j++) {
                data[i][j] = Double.parseDouble(d[j]);
            }
            i++;
        }

        reader.close();
        return data;
    }

    /**
     * Printing confusion matrix This method compares given arrays and computes
     * and prints confusion matrix
     *
     * @see {@link http://en.wikipedia.org/wiki/Confusion_matrix}
     *
     * @param trainingLabels
     * @param classifiedLabels
     */
    public static void printConfusionMatrix(char[] trainingLabels,
            char[] classifiedLabels) {

        if (trainingLabels.length != classifiedLabels.length) {
            throw new IllegalArgumentException(
                    "Arrays with training and classified labels must have same length");
        }

        // Searching for all possible characters
        LinkedHashMap<Character, Integer> positions = new LinkedHashMap<Character, Integer>();
        for (char ch : trainingLabels) {
            if (!positions.containsKey(ch)) {
                positions.put(ch, positions.size());

            }
        }
        for (char ch : classifiedLabels) {
            if (!positions.containsKey(ch)) {
                positions.put(ch, positions.size());
            }
        }

        int[][] confMatrix = new int[positions.size()][positions.size()];
        int correctlyClass = 0;

        for (int k = 0; k < trainingLabels.length; k++) {
            int i = positions.get(trainingLabels[k]);
            int j = positions.get(classifiedLabels[k]);
            confMatrix[i][j]++;
            if (i == j) {
                correctlyClass++;
            }
        }

        Formatter formatter = new Formatter(System.out);
        formatter.format(LABEL_OUTPUT_FORMAT, ' ');
        for (Character ch : positions.keySet()) {
            formatter.format(LABEL_OUTPUT_FORMAT, ch);
        }
        System.out.println();

        for (Entry<Character, Integer> eTrain : positions.entrySet()) {
            formatter.format(LABEL_OUTPUT_FORMAT, eTrain.getKey());
            for (Entry<Character, Integer> eClass : positions.entrySet()) {
                /*
                 * System.out.print(confMatrix[eTrain.getValue()][eClass
                 * .getValue()] + "\t");
                 */
                formatter.format(DATA_OUTPUT_FORMAT,
                        confMatrix[eTrain.getValue()][eClass.getValue()]);

            }
            System.out.println();
        }

        System.out.println("Results: " + correctlyClass + " of "
                + classifiedLabels.length + " correctly classified.");

    }

    /**
     * Learning classifier
     *
     * @param data
     * @param labels
     */
    abstract public void learn(double[][] data, char[] labels);

    /**
     * Classification method Parameter data represents matrix loaded by method
     * {@link Classifier#loadMatrix(String)} and returns array representing
     * classification
     *
     * @param data
     * @return
     */
    abstract public char[] classify(double[][] data);

}