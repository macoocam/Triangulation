package uk.co.alt236.btlescan.util;

/**
 * Created by Lambert on 2016/8/12.
 */

import android.util.Log;

import java.util.ArrayList;

import uk.co.alt236.btlescan.util.BeaconInfo;


public class Calculation {

    public double X, Y, sum;
    boolean GO = true;
    public int FPX, FPY;
    private double Threshold = -44;

    public int calculateMAXbeacon(double[] beacon) {
        double max = beacon[0];
        int temp = 0;

        for (int i = 1; i < beacon.length; i++) {
            if (max < beacon[i]) {
                temp = i;
                max = beacon[i];
            }
        }

        if (temp == 0) {
            return 1;
        } else if (temp == 1) {
            return 2;
        } else if (temp == 2) {
            return 4;
        } else if (temp == 3) {
            return 5;
        } else if (temp == 4) {
            return 6;
        } else if (temp == 5) {
            return 9;
        } else if (temp == 6) {
            return 11;
        } else if (temp == 7) {
            return 12;
        } else if (temp == 8) {
            return 13;
        } else if (temp == 9) {
            return 14;
        } else if (temp == 10) {
            return 15;
        } else if (temp == 11) {
            return 16;
        } else if (temp == 12) {
            return 17;
        } else if (temp == 13) {
            return 18;
        } else if (temp == 14) {
            return 19;
        } else {
            return -1;
        }
    }

    public int calculateFourCornerSpace(double c1, double c6, double c15, double c19) {
        if (c1 > c6 && c1 > c15 && c1 > c19) {
            return 1;
        } else if (c6 > c1 && c6 > c15 && c6 > c19) {
            return 2;
        } else if (c15 > c1 && c15 > c6 && c15 > c19) {
            return 3;
        } else if (c19 > c1 && c19 > c6 && c19 > c15) {
            return 4;
        } else {
            return 0;
        }
    }

    public int calculate8SubspaceSUM(double[] calbeacon) {
        // TODO Auto-generated method stub
        double r1 = calbeacon[0] + calbeacon[1] + calbeacon[5] + calbeacon[6];
        double r2 = calbeacon[1] + calbeacon[2] + calbeacon[6] + calbeacon[7];
        double r3 = calbeacon[2] + calbeacon[3] + calbeacon[7] + calbeacon[8];
        double r4 = calbeacon[3] + calbeacon[4] + calbeacon[8] + calbeacon[9];
        double r5 = calbeacon[5] + calbeacon[6] + calbeacon[10] + calbeacon[11];
        double r6 = calbeacon[6] + calbeacon[7] + calbeacon[11] + calbeacon[12];
        double r7 = calbeacon[7] + calbeacon[8] + calbeacon[12] + calbeacon[13];
        double r8 = calbeacon[8] + calbeacon[9] + calbeacon[13] + calbeacon[14];
        double region[] = {r1, r2, r3, r4, r5, r6, r7, r8};
        double temp = region[0];
        int Max = 0;
        for (int i = 1; i < region.length; i++) {
            if (temp < region[i]) {
                Max = i;
                temp = region[i];
            }
        }

        return Max + 1;
    }

    public int calculateSubspace(int maxbeaconNUM, int sum8Subspace, double[] calbeacon) {
        // TODO Auto-generated method stub
        switch (maxbeaconNUM) {
            case 1:
                return 1;
            case 2:
                if (calbeacon[0] > calbeacon[2]) {
                    return 1;
                } else {
                    return 2;
                }
            case 4:
                if (calbeacon[1] > calbeacon[3]) {
                    return 2;
                } else {
                    return 3;
                }
            case 5:
                if (calbeacon[2] > calbeacon[4]) {
                    return 3;
                } else {
                    return 4;
                }
            case 6:
                return 4;
            case 9:
//			if(calbeacon[1] > calbeacon[11]){
//				return 1;
//			}else{
//				return 5;
//			}
                return sum8Subspace;
            case 11:
//			if(calbeacon[1] > calbeacon[11]){
//				if(calbeacon[5] > calbeacon[7]){
//					return 1;
//				}else{
//					return 2;
//				}
//			}else{
//				if(calbeacon[5] > calbeacon[7]){
//					return 5;
//				}else{
//					return 6;
//				}
//			}
                return sum8Subspace;
            case 12:
                return sum8Subspace;
            case 13:
                return sum8Subspace;
            case 14:
                if (calbeacon[3] > calbeacon[13]) {
                    return 4;
                } else {
                    return 8;
                }
            case 15:
                return 5;
            case 16:
                if (calbeacon[10] > calbeacon[12]) {
                    return 5;
                } else {
                    return 6;
                }
            case 17:
//			if(calbeacon[6] > calbeacon[8]){
//				return 6;
//			}else{
//				return 7;
//			}
                return sum8Subspace;
            case 18:
                if (calbeacon[12] > calbeacon[14]) {
                    return 7;
                } else {
                    return 8;
                }
            case 19:
                return 8;
            default:
                return 0;
        }
    }

    public void calculatePosition(int chooseSubspace, double[] calbeacon) {
        // TODO Auto-generated method stub
        int[] chooseB = chooseMax3(chooseSubspace, calbeacon);
        Log.v("p", chooseB[0] + " " + chooseB[1] + " " + chooseB[2]);
        int c = 0;
        double[] distance = {0, 0, 0};

        switch (chooseSubspace) {
            case 1://0156
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 0:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon1RSSI) / 10) / BeaconInfo.d1beacon1N);
                            break;
                        case 1:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon2RSSI) / 10) / BeaconInfo.d1beacon2N);
                            break;
                        case 5:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon9RSSI) / 10) / BeaconInfo.d1beacon9N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon11RSSI) / 10) / BeaconInfo.d1beacon11N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 2://1267
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 1:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon2RSSI) / 10) / BeaconInfo.d2beacon2N);
                            break;
                        case 2:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon4RSSI) / 10) / BeaconInfo.d2beacon4N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon11RSSI) / 10) / BeaconInfo.d2beacon11N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon12RSSI) / 10) / BeaconInfo.d2beacon12N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 3://2378
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 2:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon4RSSI) / 10) / BeaconInfo.d3beacon4N);
                            break;
                        case 3:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon5RSSI) / 10) / BeaconInfo.d3beacon5N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon12RSSI) / 10) / BeaconInfo.d3beacon12N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon13RSSI) / 10) / BeaconInfo.d3beacon13N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 4://3489
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 3:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon5RSSI) / 10) / BeaconInfo.d4beacon5N);
                            break;
                        case 4:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon6RSSI) / 10) / BeaconInfo.d4beacon6N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon13RSSI) / 10) / BeaconInfo.d4beacon13N);
                            break;
                        case 9:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon14RSSI) / 10) / BeaconInfo.d4beacon14N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 5://561011
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 5:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon9RSSI) / 10) / BeaconInfo.d5beacon9N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon11RSSI) / 10) / BeaconInfo.d5beacon11N);
                            break;
                        case 10:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon15RSSI) / 10) / BeaconInfo.d5beacon15N);
                            break;
                        case 11:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon16RSSI) / 10) / BeaconInfo.d5beacon16N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 6://671112
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon11RSSI) / 10) / BeaconInfo.d6beacon11N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon12RSSI) / 10) / BeaconInfo.d6beacon12N);
                            break;
                        case 11:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon16RSSI) / 10) / BeaconInfo.d6beacon16N);
                            break;
                        case 12:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon17RSSI) / 10) / BeaconInfo.d6beacon16N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 7://781213
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon12RSSI) / 10) / BeaconInfo.d7beacon12N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon13RSSI) / 10) / BeaconInfo.d7beacon13N);
                            break;
                        case 12:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon17RSSI) / 10) / BeaconInfo.d7beacon17N);
                            break;
                        case 13:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon18RSSI) / 10) / BeaconInfo.d7beacon18N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 8://891314
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon13RSSI) / 10) / BeaconInfo.d8beacon13N);
                            break;
                        case 9:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon14RSSI) / 10) / BeaconInfo.d8beacon14N);
                            break;
                        case 13:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon18RSSI) / 10) / BeaconInfo.d8beacon18N);
                            break;
                        case 14:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon19RSSI) / 10) / BeaconInfo.d8beacon19N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
        }

        chooseB = ErrorDetection(chooseSubspace, chooseB, distance);//3->4
        double[] distancefix = {0, 0, 0};
        int[] chooseBfix = {0, 0, 0};

        if (!GO) {
            boolean ErrorDetect = false;
            int badnumber = -1;
            Log.v("p", "RechooseB:" + chooseB[0] + " " + chooseB[1] + " " + chooseB[2] + " Error:" + chooseB[3]);
            distance = ReDistanceWithError(chooseSubspace, chooseB, calbeacon);//4
            Log.v("p", "Redistance:" + distance[0] + " " + distance[1] + " " + distance[2] + " Error:" + distance[3]);
            for (int i = 0; i < distance.length - 1; i++) {

                if (distance[i] > distance[distance.length - 1] && distance[i] < 100) {//���I�j��̮t��
                    Log.v("p", chooseB[i] + " > Error:" + chooseB[chooseB.length - 1]);
                    Log.v("p", distance[i] + " > Error:" + distance[distance.length - 1]);
                    badnumber = chooseB[i];
                    ErrorDetect = true;
                }
            }

            if (ErrorDetect) {
                Log.v("p", "ErrorDetect!");
                chooseBfix = Fixback(chooseSubspace, badnumber, calbeacon);//4->3
                distancefix = FixDistanceback(chooseSubspace, chooseBfix, calbeacon);//4->3
            } else {
                for (int i = 0; i < chooseBfix.length; i++) {//4->3
                    chooseBfix[i] = chooseB[i];
                    distancefix[i] = distance[i];
                }
            }

            Triangulation(chooseBfix, distancefix);
            GO = true;
        } else {
            for (int i = 0; i < chooseBfix.length; i++) {
                chooseBfix[i] = chooseB[i];
            }
            Triangulation(chooseBfix, distance);
        }
    }

    private double[] FixDistanceback(int chooseSubspace, int[] chooseB, double[] calbeacon) {
        // TODO Auto-generated method stub
        int c = 0;
        double[] distance = {0, 0, 0};

        switch (chooseSubspace) {
            case 1://0156
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 0:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon1RSSI) / 10) / BeaconInfo.d1beacon1N);
                            break;
                        case 1:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon2RSSI) / 10) / BeaconInfo.d1beacon2N);
                            break;
                        case 5:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon9RSSI) / 10) / BeaconInfo.d1beacon9N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon11RSSI) / 10) / BeaconInfo.d1beacon11N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 2://1267
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 1:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon2RSSI) / 10) / BeaconInfo.d2beacon2N);
                            break;
                        case 2:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon4RSSI) / 10) / BeaconInfo.d2beacon4N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon11RSSI) / 10) / BeaconInfo.d2beacon11N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon12RSSI) / 10) / BeaconInfo.d2beacon12N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 3://2378
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 2:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon4RSSI) / 10) / BeaconInfo.d3beacon4N);
                            break;
                        case 3:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon5RSSI) / 10) / BeaconInfo.d3beacon5N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon12RSSI) / 10) / BeaconInfo.d3beacon12N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon13RSSI) / 10) / BeaconInfo.d3beacon13N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 4://3489
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 3:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon5RSSI) / 10) / BeaconInfo.d4beacon5N);
                            break;
                        case 4:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon6RSSI) / 10) / BeaconInfo.d4beacon6N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon13RSSI) / 10) / BeaconInfo.d4beacon13N);
                            break;
                        case 9:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon14RSSI) / 10) / BeaconInfo.d4beacon14N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 5://561011
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 5:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon9RSSI) / 10) / BeaconInfo.d5beacon9N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon11RSSI) / 10) / BeaconInfo.d5beacon11N);
                            break;
                        case 10:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon15RSSI) / 10) / BeaconInfo.d5beacon15N);
                            break;
                        case 11:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon16RSSI) / 10) / BeaconInfo.d5beacon16N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 6://671112
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon11RSSI) / 10) / BeaconInfo.d6beacon11N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon12RSSI) / 10) / BeaconInfo.d6beacon12N);
                            break;
                        case 11:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon16RSSI) / 10) / BeaconInfo.d6beacon16N);
                            break;
                        case 12:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon17RSSI) / 10) / BeaconInfo.d6beacon16N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 7://781213
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon12RSSI) / 10) / BeaconInfo.d7beacon12N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon13RSSI) / 10) / BeaconInfo.d7beacon13N);
                            break;
                        case 12:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon17RSSI) / 10) / BeaconInfo.d7beacon17N);
                            break;
                        case 13:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon18RSSI) / 10) / BeaconInfo.d7beacon18N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 8://891314
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon13RSSI) / 10) / BeaconInfo.d8beacon13N);
                            break;
                        case 9:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon14RSSI) / 10) / BeaconInfo.d8beacon14N);
                            break;
                        case 13:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon18RSSI) / 10) / BeaconInfo.d8beacon18N);
                            break;
                        case 14:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon19RSSI) / 10) / BeaconInfo.d8beacon19N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
        }

        return distance;
    }

    private int[] Fixback(int chooseSubspace, int badnumber, double[] calbeacon) {
        // TODO Auto-generated method stub

        switch (chooseSubspace) {
            case 1://0156
                if (badnumber == 0) {
                    int[] tmp = {1, 5, 6};
                    return tmp;
                }
                if (badnumber == 1) {
                    int[] tmp = {0, 5, 6,};
                    return tmp;
                }
                if (badnumber == 5) {
                    int[] tmp = {0, 1, 6};
                    return tmp;
                }
                if (badnumber == 6) {
                    int[] tmp = {0, 1, 5};
                    return tmp;
                }
                break;
            case 2://1267
                if (badnumber == 1) {
                    int[] tmp = {2, 6, 7};
                    return tmp;
                }
                if (badnumber == 2) {
                    int[] tmp = {1, 6, 7};
                    return tmp;
                }
                if (badnumber == 6) {
                    int[] tmp = {1, 2, 7};
                    return tmp;
                }
                if (badnumber == 7) {
                    int[] tmp = {1, 2, 6};
                    return tmp;
                }
                break;
            case 3://2378
                if (badnumber == 2) {
                    int[] tmp = {3, 7, 8};
                    return tmp;
                }
                if (badnumber == 3) {
                    int[] tmp = {2, 7, 8};
                    return tmp;
                }
                if (badnumber == 7) {
                    int[] tmp = {2, 3, 8};
                    return tmp;
                }
                if (badnumber == 8) {
                    int[] tmp = {2, 3, 7};
                    return tmp;
                }
                break;
            case 4://3489
                if (badnumber == 3) {
                    int[] tmp = {4, 8, 9};
                    return tmp;
                }
                if (badnumber == 4) {
                    int[] tmp = {3, 8, 9};
                    return tmp;
                }
                if (badnumber == 8) {
                    int[] tmp = {3, 4, 9};
                    return tmp;
                }
                if (badnumber == 9) {
                    int[] tmp = {3, 4, 8};
                    return tmp;
                }
                break;
            case 5://561011
                if (badnumber == 5) {
                    int[] tmp = {6, 10, 11};
                    return tmp;
                }
                if (badnumber == 6) {
                    int[] tmp = {5, 10, 11};
                    return tmp;
                }
                if (badnumber == 10) {
                    int[] tmp = {5, 6, 11};
                    return tmp;
                }
                if (badnumber == 11) {
                    int[] tmp = {5, 6, 10};
                    return tmp;
                }
                break;
            case 6://671112
                if (badnumber == 6) {
                    int[] tmp = {7, 11, 12};
                    return tmp;
                }
                if (badnumber == 7) {
                    int[] tmp = {6, 11, 12};
                    return tmp;
                }
                if (badnumber == 11) {
                    int[] tmp = {6, 7, 12};
                    return tmp;
                }
                if (badnumber == 12) {
                    int[] tmp = {6, 7, 11};
                    return tmp;
                }
                break;
            case 7://781213
                if (badnumber == 7) {
                    int[] tmp = {8, 12, 13};
                    return tmp;
                }
                if (badnumber == 8) {
                    int[] tmp = {7, 12, 13};
                    return tmp;
                }
                if (badnumber == 12) {
                    int[] tmp = {7, 8, 13};
                    return tmp;
                }
                if (badnumber == 13) {
                    int[] tmp = {7, 8, 12};
                    return tmp;
                }
                break;
            case 8://891314
                if (badnumber == 8) {
                    int[] tmp = {9, 13, 14};
                    return tmp;
                }
                if (badnumber == 9) {
                    int[] tmp = {8, 13, 14};
                    return tmp;
                }
                if (badnumber == 13) {
                    int[] tmp = {8, 9, 14};
                    return tmp;
                }
                if (badnumber == 14) {
                    int[] tmp = {8, 9, 13};
                    return tmp;
                }
                break;
        }
        Log.e("p", "NULL!");
        return null;
    }

    private double[] ReDistanceWithError(int chooseSubspace, int[] chooseB, double[] calbeacon) {
        // TODO Auto-generated method stub
        double[] distance = {0, 0, 0, 0};
        int c = 0;

        switch (chooseSubspace) {
            case 1://0156
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 0:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon1RSSI) / 10) / BeaconInfo.d1beacon1N);
                            break;
                        case 1:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon2RSSI) / 10) / BeaconInfo.d1beacon2N);
                            break;
                        case 5:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon9RSSI) / 10) / BeaconInfo.d1beacon9N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d1beacon11RSSI) / 10) / BeaconInfo.d1beacon11N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 2://1267
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 1:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon2RSSI) / 10) / BeaconInfo.d2beacon2N);
                            break;
                        case 2:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon4RSSI) / 10) / BeaconInfo.d2beacon4N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon11RSSI) / 10) / BeaconInfo.d2beacon11N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d2beacon12RSSI) / 10) / BeaconInfo.d2beacon12N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 3://2378
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 2:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon4RSSI) / 10) / BeaconInfo.d3beacon4N);
                            break;
                        case 3:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon5RSSI) / 10) / BeaconInfo.d3beacon5N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon12RSSI) / 10) / BeaconInfo.d3beacon12N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d3beacon13RSSI) / 10) / BeaconInfo.d3beacon13N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 4://3489
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 3:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon5RSSI) / 10) / BeaconInfo.d4beacon5N);
                            break;
                        case 4:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon6RSSI) / 10) / BeaconInfo.d4beacon6N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon13RSSI) / 10) / BeaconInfo.d4beacon13N);
                            break;
                        case 9:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d4beacon14RSSI) / 10) / BeaconInfo.d4beacon14N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 5://561011
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 5:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon9RSSI) / 10) / BeaconInfo.d5beacon9N);
                            break;
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon11RSSI) / 10) / BeaconInfo.d5beacon11N);
                            break;
                        case 10:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon15RSSI) / 10) / BeaconInfo.d5beacon15N);
                            break;
                        case 11:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d5beacon16RSSI) / 10) / BeaconInfo.d5beacon16N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 6://671112
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 6:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon11RSSI) / 10) / BeaconInfo.d6beacon11N);
                            break;
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon12RSSI) / 10) / BeaconInfo.d6beacon12N);
                            break;
                        case 11:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon16RSSI) / 10) / BeaconInfo.d6beacon16N);
                            break;
                        case 12:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d6beacon17RSSI) / 10) / BeaconInfo.d6beacon16N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 7://781213
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 7:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon12RSSI) / 10) / BeaconInfo.d7beacon12N);
                            break;
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon13RSSI) / 10) / BeaconInfo.d7beacon13N);
                            break;
                        case 12:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon17RSSI) / 10) / BeaconInfo.d7beacon17N);
                            break;
                        case 13:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d7beacon18RSSI) / 10) / BeaconInfo.d7beacon18N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
            case 8://891314
                for (int i = 0; i < chooseB.length; i++) {
                    switch (chooseB[i]) {
                        case 8:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon13RSSI) / 10) / BeaconInfo.d8beacon13N);
                            break;
                        case 9:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon14RSSI) / 10) / BeaconInfo.d8beacon14N);
                            break;
                        case 13:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon18RSSI) / 10) / BeaconInfo.d8beacon18N);
                            break;
                        case 14:
                            distance[c++] = Math.pow(10, (-(calbeacon[chooseB[i]] - BeaconInfo.d8beacon19RSSI) / 10) / BeaconInfo.d8beacon19N);
                            break;
                    }
                }
                Log.v("p", distance[0] + " " + distance[1] + " " + distance[2]);
                break;
        }

        return distance;
    }

    private int[] ErrorDetection(int chooseSubspace, int[] chooseB, double[] distance) {
        // TODO Auto-generated method stub
        int errornum = -1, getTwice = 0;
        for (int i = 0; i < distance.length; i++) {
            if (distance[i] > 3.2) {//Error distance
                errornum = chooseB[i];
                Log.v("p", "Error at:" + chooseB[i]);
                GO = false;
                getTwice++;
            }
        }

        if (getTwice > 1) {
            double max = 3.2;
            for (int i = 0; i < distance.length; i++) {
                if (distance[i] > max) {
                    max = distance[i];
                    errornum = chooseB[i];
                    Log.v("p", "TwiceError at:" + chooseB[i]);
                }
            }
        }

        if (!GO) {
            switch (chooseSubspace) {
                case 1://0156
                    if (errornum == 0) {
                        int[] tmp = {1, 5, 6, errornum};
                        return tmp;
                    }
                    if (errornum == 1) {
                        int[] tmp = {0, 5, 6, errornum};
                        return tmp;
                    }
                    if (errornum == 5) {
                        int[] tmp = {0, 1, 6, errornum};
                        return tmp;
                    }
                    if (errornum == 6) {
                        int[] tmp = {0, 1, 5, errornum};
                        return tmp;
                    }
                    break;
                case 2://1267
                    if (errornum == 1) {
                        int[] tmp = {2, 6, 7, errornum};
                        return tmp;
                    }
                    if (errornum == 2) {
                        int[] tmp = {1, 6, 7, errornum};
                        return tmp;
                    }
                    if (errornum == 6) {
                        int[] tmp = {1, 2, 7, errornum};
                        return tmp;
                    }
                    if (errornum == 7) {
                        int[] tmp = {1, 2, 6, errornum};
                        return tmp;
                    }
                    break;
                case 3://2378
                    if (errornum == 2) {
                        int[] tmp = {3, 7, 8, errornum};
                        return tmp;
                    }
                    if (errornum == 3) {
                        int[] tmp = {2, 7, 8, errornum};
                        return tmp;
                    }
                    if (errornum == 7) {
                        int[] tmp = {2, 3, 8, errornum};
                        return tmp;
                    }
                    if (errornum == 8) {
                        int[] tmp = {2, 3, 7, errornum};
                        return tmp;
                    }
                    break;
                case 4://3489
                    if (errornum == 3) {
                        int[] tmp = {4, 8, 9, errornum};
                        return tmp;
                    }
                    if (errornum == 4) {
                        int[] tmp = {3, 8, 9, errornum};
                        return tmp;
                    }
                    if (errornum == 8) {
                        int[] tmp = {3, 4, 9, errornum};
                        return tmp;
                    }
                    if (errornum == 9) {
                        int[] tmp = {3, 4, 8, errornum};
                        return tmp;
                    }
                    break;
                case 5://561011
                    if (errornum == 5) {
                        int[] tmp = {6, 10, 11, errornum};
                        return tmp;
                    }
                    if (errornum == 6) {
                        int[] tmp = {5, 10, 11, errornum};
                        return tmp;
                    }
                    if (errornum == 10) {
                        int[] tmp = {5, 6, 11, errornum};
                        return tmp;
                    }
                    if (errornum == 11) {
                        int[] tmp = {5, 6, 10, errornum};
                        return tmp;
                    }
                    break;
                case 6://671112
                    if (errornum == 6) {
                        int[] tmp = {7, 11, 12, errornum};
                        return tmp;
                    }
                    if (errornum == 7) {
                        int[] tmp = {6, 11, 12, errornum};
                        return tmp;
                    }
                    if (errornum == 11) {
                        int[] tmp = {6, 7, 12, errornum};
                        return tmp;
                    }
                    if (errornum == 12) {
                        int[] tmp = {6, 7, 11, errornum};
                        return tmp;
                    }
                    break;
                case 7://781213
                    if (errornum == 7) {
                        int[] tmp = {8, 12, 13, errornum};
                        return tmp;
                    }
                    if (errornum == 8) {
                        int[] tmp = {7, 12, 13, errornum};
                        return tmp;
                    }
                    if (errornum == 12) {
                        int[] tmp = {7, 8, 13, errornum};
                        return tmp;
                    }
                    if (errornum == 13) {
                        int[] tmp = {7, 8, 12, errornum};
                        return tmp;
                    }
                    break;
                case 8://891314
                    if (errornum == 8) {
                        int[] tmp = {9, 13, 14, errornum};
                        return tmp;
                    }
                    if (errornum == 9) {
                        int[] tmp = {8, 13, 14, errornum};
                        return tmp;
                    }
                    if (errornum == 13) {
                        int[] tmp = {8, 9, 14, errornum};
                        return tmp;
                    }
                    if (errornum == 14) {
                        int[] tmp = {8, 9, 13, errornum};
                        return tmp;
                    }
                    break;
            }
        } else {
            return chooseB;
        }

        return null;
    }

    private void Triangulation(int[] chooseB, double[] distance) {
        // TODO Auto-generated method stub
        double[] x = {0, 0, 0}, y = {0, 0, 0};
        double xd1, yd1, xd2, yd2, dec, fun1, fun2;

        for (int i = 0; i < chooseB.length; i++) {
            switch (chooseB[i]) {
                case 0:
                    x[i] = BeaconInfo.b1x;
                    y[i] = BeaconInfo.b1y;
                    break;
                case 1:
                    x[i] = BeaconInfo.b2x;
                    y[i] = BeaconInfo.b2y;
                    break;
                case 2:
                    x[i] = BeaconInfo.b4x;
                    y[i] = BeaconInfo.b4y;
                    break;
                case 3:
                    x[i] = BeaconInfo.b5x;
                    y[i] = BeaconInfo.b5y;
                    break;
                case 4:
                    x[i] = BeaconInfo.b6x;
                    y[i] = BeaconInfo.b6y;
                    break;
                case 5:
                    x[i] = BeaconInfo.b9x;
                    y[i] = BeaconInfo.b9y;
                    break;
                case 6:
                    x[i] = BeaconInfo.b11x;
                    y[i] = BeaconInfo.b11y;
                    break;
                case 7:
                    x[i] = BeaconInfo.b12x;
                    y[i] = BeaconInfo.b12y;
                    break;
                case 8:
                    x[i] = BeaconInfo.b13x;
                    y[i] = BeaconInfo.b13y;
                    break;
                case 9:
                    x[i] = BeaconInfo.b14x;
                    y[i] = BeaconInfo.b14y;
                    break;
                case 10:
                    x[i] = BeaconInfo.b15x;
                    y[i] = BeaconInfo.b15y;
                    break;
                case 11:
                    x[i] = BeaconInfo.b16x;
                    y[i] = BeaconInfo.b16y;
                    break;
                case 12:
                    x[i] = BeaconInfo.b17x;
                    y[i] = BeaconInfo.b17y;
                    break;
                case 13:
                    x[i] = BeaconInfo.b18x;
                    y[i] = BeaconInfo.b18y;
                    break;
                case 14:
                    x[i] = BeaconInfo.b19x;
                    y[i] = BeaconInfo.b19y;
                    break;
            }
        }

        xd1 = x[1] - x[0];
        yd1 = y[1] - y[0];
        xd2 = x[2] - x[0];
        yd2 = y[2] - y[0];
        dec = xd1 * yd2 - xd2 * yd1;
        fun1 = Math.pow(x[1], 2) + Math.pow(y[1], 2) - Math.pow(x[0], 2) - Math.pow(y[0], 2) + Math.pow(distance[0], 2) - Math.pow(distance[1], 2);
        fun2 = Math.pow(x[2], 2) + Math.pow(y[2], 2) - Math.pow(x[0], 2) - Math.pow(y[0], 2) + Math.pow(distance[0], 2) - Math.pow(distance[2], 2);
        X = (((yd2 * fun1 - yd1 * fun2) / dec) / 2);
        Y = (((xd1 * fun2 - xd2 * fun1) / dec) / 2);
    }

    public int[] chooseMax3(int chooseSubspace, double[] calbeacon) {
        switch (chooseSubspace) {
            case 1://0156
                if (calbeacon[0] < calbeacon[1] && calbeacon[0] < calbeacon[5] && calbeacon[0] < calbeacon[6]) {//no 0
                    int[] tmp = {1, 5, 6};
                    return tmp;
                }
                if (calbeacon[1] < calbeacon[0] && calbeacon[1] < calbeacon[5] && calbeacon[1] < calbeacon[6]) {//no 1
                    int[] tmp = {0, 5, 6};
                    return tmp;
                }
                if (calbeacon[5] < calbeacon[1] && calbeacon[5] < calbeacon[1] && calbeacon[5] < calbeacon[6]) {//no 5
                    int[] tmp = {0, 1, 6};
                    return tmp;
                }
                if (calbeacon[6] < calbeacon[0] && calbeacon[6] < calbeacon[1] && calbeacon[6] < calbeacon[5]) {//no 6
                    int[] tmp = {0, 1, 5};
                    return tmp;
                }
                break;
            case 2://1267
                if (calbeacon[1] < calbeacon[2] && calbeacon[1] < calbeacon[6] && calbeacon[1] < calbeacon[7]) {//no 1
                    int[] tmp = {2, 6, 7};
                    return tmp;
                }
                if (calbeacon[2] < calbeacon[1] && calbeacon[2] < calbeacon[6] && calbeacon[2] < calbeacon[7]) {//no 2
                    int[] tmp = {1, 6, 7};
                    return tmp;
                }
                if (calbeacon[6] < calbeacon[1] && calbeacon[6] < calbeacon[2] && calbeacon[6] < calbeacon[7]) {//no 6
                    int[] tmp = {1, 2, 7};
                    return tmp;
                }
                if (calbeacon[7] < calbeacon[1] && calbeacon[7] < calbeacon[2] && calbeacon[7] < calbeacon[6]) {//no 7
                    int[] tmp = {1, 2, 6};
                    return tmp;
                }
                break;
            case 3://2378
                if (calbeacon[2] < calbeacon[3] && calbeacon[2] < calbeacon[7] && calbeacon[2] < calbeacon[8]) {//no 2
                    int[] tmp = {3, 7, 8};
                    return tmp;
                }
                if (calbeacon[3] < calbeacon[2] && calbeacon[3] < calbeacon[7] && calbeacon[3] < calbeacon[8]) {//no 3
                    int[] tmp = {2, 7, 8};
                    return tmp;
                }
                if (calbeacon[7] < calbeacon[2] && calbeacon[7] < calbeacon[3] && calbeacon[7] < calbeacon[8]) {//no 7
                    int[] tmp = {2, 3, 8};
                    return tmp;
                }
                if (calbeacon[8] < calbeacon[2] && calbeacon[8] < calbeacon[3] && calbeacon[8] < calbeacon[7]) {//no 8
                    int[] tmp = {2, 3, 7};
                    return tmp;
                }
                break;
            case 4://3489
                if (calbeacon[3] < calbeacon[4] && calbeacon[3] < calbeacon[8] && calbeacon[3] < calbeacon[9]) {//no 3
                    int[] tmp = {4, 8, 9};
                    return tmp;
                }
                if (calbeacon[4] < calbeacon[3] && calbeacon[4] < calbeacon[8] && calbeacon[4] < calbeacon[9]) {//no 4
                    int[] tmp = {3, 8, 9};
                    return tmp;
                }
                if (calbeacon[8] < calbeacon[3] && calbeacon[8] < calbeacon[4] && calbeacon[8] < calbeacon[9]) {//no 8
                    int[] tmp = {3, 4, 9};
                    return tmp;
                }
                if (calbeacon[9] < calbeacon[3] && calbeacon[9] < calbeacon[4] && calbeacon[9] < calbeacon[8]) {//no 9
                    int[] tmp = {3, 4, 8};
                    return tmp;
                }
                break;
            case 5://561011
                if (calbeacon[5] < calbeacon[6] && calbeacon[5] < calbeacon[10] && calbeacon[5] < calbeacon[11]) {//no 5
                    int[] tmp = {6, 10, 11};
                    return tmp;
                }
                if (calbeacon[6] < calbeacon[5] && calbeacon[6] < calbeacon[10] && calbeacon[6] < calbeacon[11]) {//no 6
                    int[] tmp = {5, 10, 11};
                    return tmp;
                }
                if (calbeacon[10] < calbeacon[5] && calbeacon[10] < calbeacon[6] && calbeacon[10] < calbeacon[11]) {//no 10
                    int[] tmp = {5, 6, 11};
                    return tmp;
                }
                if (calbeacon[11] < calbeacon[5] && calbeacon[11] < calbeacon[6] && calbeacon[11] < calbeacon[10]) {//no 11
                    int[] tmp = {5, 6, 10};
                    return tmp;
                }
                break;
            case 6://671112
                if (calbeacon[6] < calbeacon[7] && calbeacon[6] < calbeacon[11] && calbeacon[6] < calbeacon[12]) {//no 6
                    int[] tmp = {7, 11, 12};
                    return tmp;
                }
                if (calbeacon[7] < calbeacon[6] && calbeacon[7] < calbeacon[11] && calbeacon[7] < calbeacon[12]) {//no 7
                    int[] tmp = {6, 11, 12};
                    return tmp;
                }
                if (calbeacon[11] < calbeacon[6] && calbeacon[11] < calbeacon[7] && calbeacon[11] < calbeacon[12]) {//no 11
                    int[] tmp = {6, 7, 12};
                    return tmp;
                }
                if (calbeacon[12] < calbeacon[6] && calbeacon[12] < calbeacon[7] && calbeacon[12] < calbeacon[11]) {//no 12
                    int[] tmp = {6, 7, 11};
                    return tmp;
                }
                break;
            case 7://781213
                if (calbeacon[7] < calbeacon[8] && calbeacon[7] < calbeacon[12] && calbeacon[7] < calbeacon[13]) {//no 7
                    int[] tmp = {8, 12, 13};
                    return tmp;
                }
                if (calbeacon[8] < calbeacon[7] && calbeacon[8] < calbeacon[12] && calbeacon[8] < calbeacon[13]) {//no 8
                    int[] tmp = {7, 12, 13};
                    return tmp;
                }
                if (calbeacon[12] < calbeacon[7] && calbeacon[12] < calbeacon[8] && calbeacon[12] < calbeacon[13]) {//no 12
                    int[] tmp = {7, 8, 13};
                    return tmp;
                }
                if (calbeacon[13] < calbeacon[7] && calbeacon[13] < calbeacon[8] && calbeacon[13] < calbeacon[12]) {//no 13
                    int[] tmp = {7, 8, 12};
                    return tmp;
                }
                break;
            case 8://891314
                if (calbeacon[8] < calbeacon[9] && calbeacon[8] < calbeacon[13] && calbeacon[8] < calbeacon[14]) {//no 8
                    int[] tmp = {9, 13, 14};
                    return tmp;
                }
                if (calbeacon[9] < calbeacon[8] && calbeacon[9] < calbeacon[13] && calbeacon[9] < calbeacon[14]) {//no 9
                    int[] tmp = {8, 13, 14};
                    return tmp;
                }
                if (calbeacon[13] < calbeacon[8] && calbeacon[13] < calbeacon[9] && calbeacon[13] < calbeacon[14]) {//no 13
                    int[] tmp = {8, 9, 14};
                    return tmp;
                }
                if (calbeacon[14] < calbeacon[8] && calbeacon[14] < calbeacon[9] && calbeacon[14] < calbeacon[13]) {//no 14
                    int[] tmp = {8, 9, 13};
                    return tmp;
                }
                break;
        }
        return null;
    }

    public int CustomSubspceFingerPrint(double[] calbeacon) {
        // TODO Auto-generated method stub
        ArrayList<Calculation> tmp = new ArrayList<Calculation>();
        Calculation c;
        DataBase db = new DataBase();
        double[][] testlist = db.getArray();
        double tep = 0;
        for (int i = 0; i < testlist.length; i++) {//和資料的點計算間距
            c = new Calculation();
            for (int j = 0; j < calbeacon.length; j++) {
                c.sum += Math.sqrt(Math.pow(testlist[i][j] - calbeacon[j], 2));
            }
            tmp.add(c);
            c = null;
        }

        double min = 100;
        int temp = 0;
        for (int j = 0; j < tmp.size(); j++) {//從最短距離計算最接近的點
            if (min > tmp.get(j).sum) {
                min = tmp.get(j).sum;
                temp = j;
            }
        }

        int x = temp / 5;//轉換成座標
        int y = temp % 5;
        Log.v("ptest", "FingerPrint(" + x + ", " + y + ")");

        FPX = x;
        FPY = y;

        switch (x) {
            case 0:
                if (y >= 3) {
                    return 5;
                } else {
                    return 1;
                }
            case 1:
                if (y >= 3) {
                    return 5;
                } else {
                    return 1;
                }
            case 2:
//			if(y >= 3){
//				return 6;
//			}else{
//				return 2;
//			}
                switch (y) {
                    case 0:
                        return 1;
                    case 1:
                        return 2;
                    case 2:
                        return 2;
                    case 3:
                        return 5;
                    case 4:
                        return 5;
                    case 5:
                        return 6;
                }
            case 3:
                if (y >= 3) {
                    return 6;
                } else {
                    return 2;
                }
            case 4:
//			if(y >= 3){
//				return 6;
//			}else{
//				return 2;
//			}
                switch (y) {
                    case 0:
                        return 3;
                    case 1:
                        return 2;
                    case 2:
                        return 2;
                    case 3:
                        return 6;
                    case 4:
                        return 7;
                    case 5:
                        return 6;
                }
            case 5:
                if (y >= 3) {
                    return 7;
                } else {
                    return 3;
                }
            case 6:
//			if(y >= 3){
//				return 8;
//			}else{
//				return 4;
//			}
                switch (y) {
                    case 0:
                        return 4;
                    case 1:
                        return 3;
                    case 2:
                        return 4;
                    case 3:
                        return 8;
                    case 4:
                        return 7;
                    case 5:
                        return 8;
                }
            case 7:
                if (y >= 3) {
                    return 8;
                } else {
                    return 4;
                }
            case 8:
                if (y >= 3) {
                    return 8;
                } else {
                    return 4;
                }
            default:
                return 0;
        }
    }

    public boolean calculateThreshold(int MAXbeaconNUM, double[] calbeacon) {
        // TODO Auto-generated method stub
        if (MAXbeaconNUM == 1 && calbeacon[0] > Threshold) {
            FPX = 0;
            FPY = 0;
            return true;
        } else if (MAXbeaconNUM == 2 && calbeacon[1] > Threshold) {
            FPX = 2;
            FPY = 0;
            return true;
        } else if (MAXbeaconNUM == 4 && calbeacon[2] > Threshold) {
            FPX = 4;
            FPY = 0;
            return true;
        } else if (MAXbeaconNUM == 5 && calbeacon[3] > Threshold) {
            FPX = 6;
            FPY = 0;
            return true;
        } else if (MAXbeaconNUM == 2 && calbeacon[4] > Threshold) {
            FPX = 8;
            FPY = 0;
            return true;
        } else if (MAXbeaconNUM == 15 && calbeacon[10] > Threshold) {
            FPX = 0;
            FPY = 5;
            return true;
        } else if (MAXbeaconNUM == 16 && calbeacon[11] > Threshold) {
            FPX = 2;
            FPY = 5;
            return true;
        } else if (MAXbeaconNUM == 17 && calbeacon[12] > Threshold) {
            FPX = 4;
            FPY = 5;
            return true;
        } else if (MAXbeaconNUM == 18 && calbeacon[13] > Threshold) {
            FPX = 6;
            FPY = 5;
            return true;
        } else if (MAXbeaconNUM == 19 && calbeacon[14] > Threshold) {
            FPX = 8;
            FPY = 5;
            return true;
        }

        switch (FPX) {
            case 0:
                if (FPY == 0 && calbeacon[0] > Threshold) {
                    return true;
                } else if (FPY == 5 && calbeacon[10] > Threshold) {
                    return true;
                } else {
                    return false;
                }
            case 2:
                if (FPY == 0 && calbeacon[1] > Threshold) {
                    return true;
                } else if (FPY == 5 && calbeacon[11] > Threshold) {
                    return true;
                } else {
                    return false;
                }
            case 4:
                if (FPY == 0 && calbeacon[2] > Threshold) {
                    return true;
                } else if (FPY == 5 && calbeacon[12] > Threshold) {
                    return true;
                } else {
                    return false;
                }
            case 6:
                if (FPY == 0 && calbeacon[3] > Threshold) {
                    return true;
                } else if (FPY == 5 && calbeacon[13] > Threshold) {
                    return true;
                } else {
                    return false;
                }
            case 8:
                if (FPY == 0 && calbeacon[4] > Threshold) {
                    return true;
                } else if (FPY == 5 && calbeacon[14] > Threshold) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    public void setFingerPrintPosition() {
        // TODO Auto-generated method stub
        X = FPX;
        Y = FPY;
    }
}

