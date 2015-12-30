package bia.sga;

public class DistanceFunction implements ContinuousFunction {

    @Override
    /**
     * Fitness Function Definition. Min distances from segments
     */
    public double f(double[] x) {
        double er = 0.0;
        for (int i = 0; i < SGA.data.length; i++) {
            double minDist = Double.MAX_VALUE;

            for (int j = 0; j < x.length/4-1; j++) {
                double dist = dist(x[4*j], x[4*j+1], x[4*j+2], x[4*j+3], SGA.data[i][0], SGA.data[i][1]);
                if(dist < minDist) {
                    minDist = dist;
                }
            }

            er += minDist;
        }
        return er;
    }

    public double dist(double x1, double y1, double x2, double y2, double px, double py) {
        double A = px - x1;
        double B = py - y1;
        double C = x2 - x1;
        double D = y2 - y1;

        double dot = A * C + B * D;
        double len_sq = C * C + D * D;
        double param = dot / len_sq;

        double xx, yy;

        if (param < 0 || (x1 == x2 && y1 == y2)) {
            xx = x1;
            yy = y1;
        } else if (param > 1) {
            xx = x2;
            yy = y2;
        } else {
            xx = x1 + param * C;
            yy = y1 + param * D;
        }

        double dx = px - xx;
        double dy = py - yy;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public int getDimension() {
        return 2; // wtf
    }
}
