/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
import TSim.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lab2 {
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



    // The list of all track monitors. There are exactly 6 locks for the given map:
    // - Locks 1,2,4 are designated for critical sections where only one train can be at a given time
    // - Locks 0,3,5 are there to indicate if the upper part of the train track is occupied (locked)
    private final static TrackMonitor[] mTracks = new TrackMonitor[6];

    private int mSimulationSpeed;
    private final int MAX_TRAIN_SPEED = 22;
    private final int MAX_SIMULATION_SPEED = 100;

    private final static Integer[][] mSwitches = new Integer[4][2];
    static {
        mSwitches[0] = new Integer[] { 17, 7};
        mSwitches[1] = new Integer[] { 15, 9};
        mSwitches[2] = new Integer[] { 4, 9};
        mSwitches[3] = new Integer[] { 3, 11};
    }

    public static void main(String[] args) {
        new Lab2(args);
    }

    public Lab2(String[] args) {
        try {
            Random r = new Random();
            
            int tr1speed = r.nextInt(MAX_TRAIN_SPEED)+1; // do not allow 0 value from random
            int tr2speed = r.nextInt(MAX_TRAIN_SPEED)+1;
            mSimulationSpeed = 100;
            
            switch(args.length) {
                case 0:
                    break;

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

            for (int i = 0; i < mTracks.length; i++) {
                mTracks[i] = new TrackMonitor(); // only locks are allowed!
            }


            // acquire locks for the initial situation of train positions
            mTracks[0].enter(tr1); // tr1 is at the top station, lock monitor
            mTracks[5].enter(tr2); // tr2 is at the top station, lock monitor

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

    private class TrackMonitor {
        // The Lock object basically replaces the "synchronized" keyword in java.
        private final Lock mmLock = new ReentrantLock();

        // The Condition object replaces the use of the Object monitor methods.
        // Conditions (also known as condition queues or condition variables)
        // provide a means for one thread to suspend execution (to "wait") until
        // notified by another thread that some state condition may now be true.
        // Because access to this shared state information occurs in different threads,
        // it must be protected, so a lock of some form is associated with the condition.
        //
        // The key property that waiting for a condition provides is that it atomically
        // releases the associated lock and suspends the current thread, just like Object.wait.
        //
        // Note: Condition instances are inherited from Object as any other classes, so they have
        // wait and notify methods, unrelated to the signal and await methods from Condition.
        // To avoid confusion, it is better not to use the Object methods.
        //
        // (from javadoc)
        private final Condition conditionOccupied = mmLock.newCondition();

        // This variable states the current state of the track, it could be also any other object,
        // such as a counter, stack, ... whatever
        private boolean isOccupied = false;

        public void enter(Train tr) {
            mmLock.lock();
            try {
                if(isOccupied) {
                    tr.stop();

                    // Wait for the other train to leave the track
                    // and signal it is leaving. This is a blocking call.
                    // This call also releases the mmLock atomically.
                    //
                    // Current thread lies dormant until one of these four things happen:
                    // - Some other thread invokes the signal() method for this Condition
                    //   and the current thread happens to be chosen as the thread to be awakened; or
                    // - Some other thread invokes the signalAll() method for this Condition; or
                    // - Some other thread interrupts the current thread, and interruption
                    //   of thread suspension is supported; or
                    // - A "spurious wakeup" occurs.
                    //
                    // Before this method can return, it will also acquire the lock atomically.
                    //
                    // (from javadoc)
                    conditionOccupied.await();
                }

                // The track is free now, so let's occupy it
                isOccupied = true;

                // Let the train go. When it hits next appropriate sensor,
                // it will call TrackMonitor.leave()
                tr.go();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // We must always unlock!
                mmLock.unlock();
            }
        }

        public boolean tryEnter(Train tr) {
            mmLock.lock();

            // Return immediately false if we cannot enter
            if(isOccupied) {
                mmLock.unlock();
                return false;
            }

            // We can enter the track!
            try {
                isOccupied = true;
                tr.go();
            } finally {
                mmLock.unlock();
            }

            return true;
        }

        public void leave(Train tr) {
            mmLock.lock();
            try {
                isOccupied = false;

                // Signal the other thread that might be waiting to enter the track
                conditionOccupied.signal();
            } finally {
                mmLock.unlock();
            }
        }
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

        private  void go() {
            try {
                mTsi.setSpeed(mmTrainIdx, mmDefaultSpeed*mDirection);
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }

        private  void changeDirection() {
            mDirection *= -1;
        }

        private  void stop() {
            try {
                mTsi.setSpeed(mmTrainIdx, 0);
            } catch (CommandException e) {
                e.printStackTrace();
            }
        }

        private  boolean goesFromLeft(int idx) {
            return mmLastSensor-idx < 0;
        }
        private  boolean goesFromRight(int idx) {
            return mmLastSensor-idx > 0;
        }

        /**** these methods stay here the same, so that we don't need to change the code from previous assignment *****/
        private  void acquire(int track) {
            mTracks[track].enter(this);
        }

        private void release(int track) {
            mTracks[track].leave(this);
        }

        private boolean tryAcquire(int semaphore) {
            return mTracks[semaphore].tryEnter(this);
        }

        private  void changeSwitch(int switchIdx, int dir) {
            //System.err.println("Train "+mmTrainIdx+" switches "+switchIdx+" to direction "+dir);
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

                    //System.err.println("Train "+mmTrainIdx+" visited sensor "+sensorIdx);

                    if(ev.getStatus() == SensorEvent.ACTIVE) {

			            // skip the first reading of sensor -- it is the train station sensor
                        if (mmLastSensor == null) {
                            mmLastSensor = sensorIdx;
                            continue;
                        }
                       // we arrived at the station, we should wait for a short time
                        if (isTrainStationSensor(ev) && (!mmLastSensor.equals(sensorIdx))) {
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

                            if(goesFromRight(sensorIdx)) {
                                if(!tryAcquire(0)) {
                                    changeSwitch(0, TSimInterface.SWITCH_LEFT);
                                } else {
                                    changeSwitch(0, TSimInterface.SWITCH_RIGHT);
                                }

                            }
                            break;

                        case 9:
                            if(goesFromLeft(sensorIdx)) {
                                if(!tryAcquire(3)) {
                                    changeSwitch(1, TSimInterface.SWITCH_LEFT);
                                } else {
                                    changeSwitch(1, TSimInterface.SWITCH_RIGHT);
                                }
                            }

                            if(goesFromRight(sensorIdx) && mmLastSensor == 10) {
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
                            if(goesFromLeft(sensorIdx) && mmLastSensor == 12) {
                                release(3);
                            }

                            if(goesFromRight(sensorIdx)) {
                                if(!tryAcquire(3)) {
                                    changeSwitch(2, TSimInterface.SWITCH_RIGHT);
                                } else {
                                    changeSwitch(2, TSimInterface.SWITCH_LEFT);
                                }
                            }
                            break;

                        case 15:
                            if(goesFromLeft(sensorIdx)) {
                                if(!tryAcquire(5)) {
                                    changeSwitch(3, TSimInterface.SWITCH_RIGHT);
                                } else {
                                    changeSwitch(3, TSimInterface.SWITCH_LEFT);
                                }
                            }

                            if(goesFromRight(sensorIdx) && mmLastSensor == 16) {
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

