/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
import TSim.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Lab1 {
    // the interface for communication with the simulator
    private final TSimInterface mTsi = TSimInterface.getInstance();

    // Lookup table to convert sensor (x,y) position into a reasonable index number
    // The indices are ordered in such a way that if you take the difference of two indices
    // their sign (+/-) indicates the direction, (-) is from left to right, (+) is from right to left
    private final static Integer[][] mSensorLookupTable = new Integer[20][2];
    private final static ArrayList<Integer> mStationSensors = new ArrayList<Integer>();
    static {
        mSensorLookupTable[0]  = new Integer[ ]{ 14, 5 };
        mSensorLookupTable[1]  = new Integer[ ]{ 14, 3 };
        mSensorLookupTable[2]  = new Integer[ ]{ 9 , 5 };
        mSensorLookupTable[3]  = new Integer[ ]{ 6 , 5 };
        mSensorLookupTable[4]  = new Integer[ ]{ 11, 7 };
        mSensorLookupTable[5]  = new Integer[ ]{ 11, 8 };
        mSensorLookupTable[6]  = new Integer[ ]{ 14, 7 };
        mSensorLookupTable[7]  = new Integer[ ]{ 14, 8 };
        mSensorLookupTable[8]  = new Integer[ ]{ 19, 7 };
        mSensorLookupTable[9]  = new Integer[ ]{ 17, 9 };
        mSensorLookupTable[10] = new Integer[ ]{ 12, 9 };
        mSensorLookupTable[11] = new Integer[ ]{ 12, 10 };
        mSensorLookupTable[12] = new Integer[ ]{ 7 ,9 };
        mSensorLookupTable[13] = new Integer[ ]{ 7 ,10 };
        mSensorLookupTable[14] = new Integer[ ]{ 2 ,9 };
        mSensorLookupTable[15] = new Integer[ ]{ 1 ,10 };
        mSensorLookupTable[16] = new Integer[ ]{ 6 ,11 };
        mSensorLookupTable[17] = new Integer[ ]{ 6 ,13 };
        mSensorLookupTable[18] = new Integer[ ]{ 13, 11 };
        mSensorLookupTable[19] = new Integer[ ]{ 13, 13 };

        mStationSensors.add(0);
        mStationSensors.add(1);
        mStationSensors.add(18);
        mStationSensors.add(19);
    }



    public int getSensorIndex(SensorEvent ev) {
        return getSensorIndex(ev.getXpos(), ev.getYpos());
    }
    public int getSensorIndex(int x, int y) {
        for (int i = 0; i < mSensorLookupTable.length; i++) {
            if(mSensorLookupTable[i][0] == x && mSensorLookupTable[i][1] == y) return i;
        }
        return -1;
    }
    public Integer[] getSensorPosition(int index) {
        return mSensorLookupTable[index];
    }



    // The list of all semaphores. There are exactly 6 semaphores for the given map:
    // - Semaphores 1,2,4 are designated for critical sections where only one train can be at a given time
    // - Semaphores 0,3,5 are there to indicate if the upper part of the train track is occupied (acquired)
    private final static Semaphore[] mSemaphores = new Semaphore[6];
    static {
        for (int i = 0; i < mSemaphores.length; i++) {
            mSemaphores[i] = new Semaphore(1); // only binary semaphores are allowed!
        }
    }

    private int mSimulationSpeed;
    private int final MAX_TRAIN_SPEED = 22;
    private int final MAX_SIMULATION_SPEED = 100;    

    private final static Integer[][] mSwitches = new Integer[4][2];
    static {
        mSwitches[0] = new Integer[] { 17, 7};
        mSwitches[1] = new Integer[] { 15, 9};
        mSwitches[2] = new Integer[] { 4, 9};
        mSwitches[3] = new Integer[] { 3, 11};
    }

    public static void main(String[] args) {
        new Lab1(args);
    }

    public Lab1(String[] args) {
        try {
        	Train tr1;
        	Train tr2;

            Random r = new Random();
            
            int tr1speed = r.nextInt(mMaxSpeed-1)+1, tr2speed = r.nextInt(mMaxSpeed-1)+1;
            mSimulationSpeed = 100;
            
            switch(args.length) {
                case 1:
                    tr1speed = Integer.parseInt(args[0]);
                    break;
                
                case 2:
                    tr1speed = Integer.parseInt(args[0]);
                    tr2speed = Integer.parseInt(args[1]);
                    break;
                    
                case 3:
                    tr1speed = Integer.parseInt(args[0]);
                    tr2speed = Integer.parseInt(args[1]);
                    mSimulationSpeed = Integer.parseInt(args[2]);
                    break;
                    
                
                default:
                    System.err.println("Too many args");
                    return;
            }
            
            if(tr1speed > MAX_TRAIN_SPEED) tr1speed = MAX_TRAIN_SPEED;
            if(tr2speed > MAX_TRAIN_SPEED) tr2speed = MAX_TRAIN_SPEED;
            if(mSimulationSpeed > MAX_SIMULATION_SPEED) mSimulationSpeed = MAX_SIMULATION_SPEED;            
            
            
            Train tr1 = new Train(1, tr1speed);
            Train tr2 = new Train(2, tr2speed);

            // acquire semaphores for the initial situation of train positions
            mSemaphores[0].acquire(); // tr1 is at the top station, acquire semaphore
            mSemaphores[5].acquire(); // tr2 is at the top station, acquire semaphore

            Thread th1 = new Thread(tr1);
            Thread th2 = new Thread(tr2);

            th1.start();
            th2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isTrainStationSensor(SensorEvent ev) {
        int idx =  getSensorIndex(ev.getXpos(), ev.getYpos());
        return mStationSensors.contains(idx);
    }

    private class Train implements Runnable {
        private Integer mmLastSensor = null;
        private final int mmTrainIdx;
        private int mmDefaultSpeed;
        private int mDirection;

        private Train(int mTrainIdx, int defaultSpeed) {
            mmTrainIdx = mTrainIdx;
            mmDefaultSpeed = defaultSpeed;
            mDirection = 1;
        }

        private synchronized void go() throws CommandException {
            mTsi.setSpeed(mmTrainIdx, mmDefaultSpeed*mDirection);
        }

        private synchronized void changeDirection() {
            mDirection *= -1;
        }

        private synchronized void stop() throws CommandException {
            mTsi.setSpeed(mmTrainIdx, 0);
        }

        private synchronized boolean goesFromLeft(int idx) {
            return mmLastSensor-idx < 0;
        }
        private synchronized boolean goesFromRight(int idx) {
            return mmLastSensor-idx > 0;
        }

        private synchronized void acquire(int semaphore) {
            try {
                if(!isAquired(semaphore)) {
                    System.err.println("Train "+mmTrainIdx+" acquires "+semaphore);
                    mSemaphores[semaphore].acquire();
                } else {
                    System.err.println("Train "+mmTrainIdx+" stops and acquires "+semaphore);
                    stop();
                    mSemaphores[semaphore].acquire();
                    go();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                //e.printStackTrace();
            }
        }


        private synchronized void release(int semaphore) {
            System.err.println("Train "+mmTrainIdx+" releases "+semaphore);
            mSemaphores[semaphore].release();
        }

        private synchronized boolean isAquired(int semaphore) {
            return mSemaphores[semaphore].availablePermits() == 0;
        }

        private synchronized void changeSwitch(int switchIdx, int dir) {
            System.err.println("Train "+mmTrainIdx+" switches "+switchIdx+" to direction "+dir);
            try {
                mTsi.setSwitch(mSwitches[switchIdx][0], mSwitches[switchIdx][1], dir);
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }

	
	    

        @Override
        public void run() {
            try {
                // go before we hit the first sensor
                go();

                while(true) {
                    // this is a blocking call
                    SensorEvent ev = mTsi.getSensor(mmTrainIdx);
                    int sensorIdx = getSensorIndex(ev);

                    System.err.println("Train "+mmTrainIdx+" visited sensor "+sensorIdx);

                    if(ev.getStatus() == SensorEvent.ACTIVE) {

			// skip the first reading of sensor -- it is the train station sensor
                        if (mmLastSensor == null) {
                            mmLastSensor = sensorIdx;
                            continue;
                        }
                       // we arrived at the station, we should wait for 2sec
                        if (isTrainStationSensor(ev) && (!mmLastSensor.equals(sensorIdx))) {
                            //mmLastSensor = null;
                            stop();
                            Thread.sleep(1000 + 2* mSimulationSpeed * Math.abs(mmDefaultSpeed));
                            changeDirection();
                            go();
                        }

			 
                    }

                    // consider only the event of train entering the sensor
                    if(ev.getStatus() == SensorEvent.INACTIVE) continue;

                    switch (sensorIdx) {
                        case 2:
                        case 3:
                            if(goesFromLeft(sensorIdx))  acquire(1);
                            if(goesFromRight(sensorIdx)) release(1);
                            break;

                        case 4:
                        case 5:
                            if(goesFromRight(sensorIdx)) acquire(1);
                            if(goesFromLeft(sensorIdx))  release(1);
                            break;

                        case 6:
                            if(goesFromLeft(sensorIdx)) {
                                acquire(2);
                                changeSwitch(0, TSimInterface.SWITCH_RIGHT);
                            }
                            if(goesFromRight(sensorIdx)) release(2);
                            break;

                        case 7:
                            if(goesFromLeft(sensorIdx)) {
                                acquire(2);
                                changeSwitch(0, TSimInterface.SWITCH_LEFT);
                            }
                            if(goesFromRight(sensorIdx)) release(2);
                            break;

                        case 8:
                            if(goesFromLeft(sensorIdx) && mmLastSensor == 6) release(0);
                            if(goesFromRight(sensorIdx) && isAquired(0)) {
                                changeSwitch(0, TSimInterface.SWITCH_LEFT);
                            }
                            if(goesFromRight(sensorIdx) && !isAquired(0)) {
                                acquire(0);
                                changeSwitch(0, TSimInterface.SWITCH_RIGHT);
                            }
                            break;

                        case 9:
                            if(goesFromLeft(sensorIdx) && isAquired(3)) {
                                changeSwitch(1, TSimInterface.SWITCH_LEFT);
                            }
                            if(goesFromLeft(sensorIdx) && !isAquired(3)) {
                                acquire(3);
                                changeSwitch(1, TSimInterface.SWITCH_RIGHT);
                            }
                            if(goesFromRight(sensorIdx) && isAquired(3)) {
                                release(3);
                            }
                            break;

                        case 10:
                            if(goesFromLeft(sensorIdx)) {
                                release(2);
                            }
                            if (goesFromRight(sensorIdx)) {
                                acquire(2);
                                changeSwitch(1, TSimInterface.SWITCH_RIGHT);
                            }
                            break;

                        case 11:
                            if(goesFromLeft(sensorIdx)) {
                                release(2);
                            }
                            if (goesFromRight(sensorIdx)) {
                                acquire(2);
                                changeSwitch(1, TSimInterface.SWITCH_LEFT);
                            }
                            break;

                        case 12:
                            if (goesFromLeft(sensorIdx)) {
                                acquire(4);
                                changeSwitch(2, TSimInterface.SWITCH_LEFT);
                            }
                            if (goesFromRight(sensorIdx)) {
                                release(4);
                            }
                            break;

                        case 13:
                            if(goesFromLeft(sensorIdx)) {
                                acquire(4);
                                changeSwitch(2, TSimInterface.SWITCH_RIGHT);
                            }
                            if(goesFromRight(sensorIdx)) {
                                release(4);
                            }
                            break;

                        case 14:
                            if(goesFromLeft(sensorIdx) && mmLastSensor == 12) release(3);
                            if(goesFromRight(sensorIdx) && isAquired(3)) {
                                changeSwitch(2, TSimInterface.SWITCH_RIGHT);
                            }
                            if(goesFromRight(sensorIdx) && !isAquired(3)) {
                                acquire(3);
                                changeSwitch(2, TSimInterface.SWITCH_LEFT);
                            }
                            break;

                        case 15:
                            if(goesFromLeft(sensorIdx) && isAquired(5)) {
                                changeSwitch(3, TSimInterface.SWITCH_RIGHT);
                            }
                            if(goesFromLeft(sensorIdx) && !isAquired(5)) {
                                acquire(5);
                                changeSwitch(3, TSimInterface.SWITCH_LEFT);
                            }
                            if(goesFromRight(sensorIdx) && isAquired(5)) {
                                release(5);
                            }
                            break;

                        case 16:
                            if(goesFromLeft(sensorIdx)) {
                                release(4);
                            }
                            if (goesFromRight(sensorIdx)) {
                                acquire(4);
                                changeSwitch(3, TSimInterface.SWITCH_LEFT);
                            }
                            break;

                        case 17:
                            if(goesFromLeft(sensorIdx)) {
                                release(4);
                            }
                            if (goesFromRight(sensorIdx)) {
                                acquire(4);
                                changeSwitch(3, TSimInterface.SWITCH_RIGHT);
                            }
                            break;


                    }

                    mmLastSensor = sensorIdx;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

