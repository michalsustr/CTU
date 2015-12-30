package KUI;

import java.io.IOException;

public class MainFrame {

	private static final int CLASSIFIER_PERCEPTRON = 1;
	private static final int CLASSIFIER_NN = 2;
	private static final int CLASSIFIER_BAYES = 3;
	private static final int CLASSIFIER_BAYES_NORMALIZED = 4;

    public static void main(String[] args) {
		int classifier = MainFrame.CLASSIFIER_BAYES;

		switch(classifier) {
			case MainFrame.CLASSIFIER_PERCEPTRON:
				runPerceptron();
				break;
			case MainFrame.CLASSIFIER_NN:
				runNN();
				break;
			case MainFrame.CLASSIFIER_BAYES:
				runBayes();
				break;
			case MainFrame.CLASSIFIER_BAYES_NORMALIZED:
				runBayesNormalized();
				break;


		}
    }

	private static void runPerceptron() {
		// new instance of classifier
        // For k-neares neighbor and naive bayes classifier you have to just
        // change class Perceptron for NN or Bayes respectively
        Classifier perceptron = new Perceptron();
        try {

            // Loading training data
            double[][] trainData = Classifier.loadMatrix("train.txt");
            char[] trainLabels = Classifier.loadLabels("train_labels.txt");

            // training classifier
            perceptron.learn(trainData, trainLabels);

            // loading test data
            double[][] testData = Classifier.loadMatrix("test.txt");
            char[] testLabels = Classifier.loadLabels("test_labels.txt");

            // classifying
            char[] classLabels = perceptron.classify(testData);

            // Printing result matrix
            Classifier.printConfusionMatrix(testLabels, classLabels);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static void runNN() {
        Classifier nn = new NN();
        try {

            // Loading training data
            double[][] trainData = Classifier.loadMatrix("train.txt");
            char[] trainLabels = Classifier.loadLabels("train_labels.txt");

            // training classifier
            nn.learn(trainData, trainLabels);

            // loading test data
            double[][] testData = Classifier.loadMatrix("test.txt");
            char[] testLabels = Classifier.loadLabels("test_labels.txt");

            // classifying
            char[] classLabels = nn.classify(testData);

            // Printing result matrix
            Classifier.printConfusionMatrix(testLabels, classLabels);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private static void runBayes() {
        Classifier bayes = new Bayes();
        try {

            // Loading training data
            double[][] trainData = Classifier.loadMatrix("train.txt");
            char[] trainLabels = Classifier.loadLabels("train_labels.txt");

            // training classifier
            bayes.learn(trainData, trainLabels);

            // loading test data
            double[][] testData = Classifier.loadMatrix("test.txt");
            char[] testLabels = Classifier.loadLabels("test_labels.txt");

            // classifying
            char[] classLabels = bayes.classify(testData);

            // Printing result matrix
            Classifier.printConfusionMatrix(testLabels, classLabels);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


	private static void runBayesNormalized() {
        Classifier bayesNormalized = new BayesNormalized();
        try {

            // Loading training data
            double[][] trainData = Classifier.loadMatrix("train.txt");
            char[] trainLabels = Classifier.loadLabels("train_labels.txt");

            // training classifier
            bayesNormalized.learn(trainData, trainLabels);

            // loading test data
            double[][] testData = Classifier.loadMatrix("test.txt");
            char[] testLabels = Classifier.loadLabels("test_labels.txt");

            // classifying
            char[] classLabels = bayesNormalized.classify(testData);

            // Printing result matrix
            Classifier.printConfusionMatrix(testLabels, classLabels);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}


}