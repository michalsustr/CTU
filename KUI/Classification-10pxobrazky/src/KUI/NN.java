package KUI;

public class NN extends Classifier {

	private double[][] referenceData;
    private char[] referenceLabels;

    @Override
    public void learn(double[][] data, char[] labels) {
        this.referenceData = data;
		this.referenceLabels = labels;
    }

    @Override
    public char[] classify(double[][] data) {
		char[] returnValue = new char[data.length];

		// loop all images to classify
        for(int img = 0; img < data.length; img++) {

			// for each image find the minimal distance from reference data
			int minDistancePointer = -1;
			double minDistance = Double.MAX_VALUE;

			for(int i = 0; i < referenceData.length; i++) {
				double distance = 0;

				for(int j = 0; j < data[img].length; j++) {
					distance += Math.pow(data[img][j]-referenceData[i][j], 2);
				}
				if(distance < minDistance) {
					minDistance = distance;
					minDistancePointer = i;
				}
			}

			returnValue[img] = referenceLabels[minDistancePointer];
		}


        return returnValue;
    }
}
