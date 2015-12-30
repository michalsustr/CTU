/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import TSim.*;

public class Lab1 {

  public static void main(String[] args) {
    new Lab1(args);
  }

  public Lab1(String[] args) {
    TSimInterface tsi = TSimInterface.getInstance();

    try {
      tsi.setSpeed(1,10);
      tsi.setSpeed(2,10);
      tsi.setSpeed(1,0);
    }
    catch (CommandException e) {
      e.printStackTrace();    // or only e.getMessage() for the error
      System.exit(1);
    }
  }

    private class Sensor extends SensorEvent {
        /**
         * Creates a new SensorEvent for a specific train and sensor.
         * The train is represented by the trainId and the sensor by
         * its coordinates.
         *
         * @param trainId the id of the train passing the sensor.
         * @param xPos    the x coordinate of the sensor.
         * @param yPos    the y coordinate of the sensor.
         * @param status  the status of the sensor; either ACTIVE or INACTIVE/
         */
        public Sensor(int trainId, int xPos, int yPos, int status) {
            super(trainId, xPos, yPos, status);
        }
    }
}

