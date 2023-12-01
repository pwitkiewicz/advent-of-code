package aoc.year2021.day5;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Line {
    private static double RADIANS = Math.PI / 4;
    private static double EPSILON = 0.1;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public boolean checkPoint(final double x, final double y) {
        final double leftSide = (y - y1) * (x2 - x1);
        final double rightSide = (x - x1) * (y2 - y1);

        if (Math.abs(leftSide - rightSide) < EPSILON) {
            final double distance = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
            final double distance1 = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
            final double distance2 = Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2));
            return Math.abs(distance - distance1 - distance2) < EPSILON;
        }

        return false;
    }

    public boolean isVertical() {
        return x1 == x2;
    }

    public boolean isHorizontal() {
        return y1 == y2;
    }

    public boolean isDiagonal() {
        var slope = (y2 - y1) / (x2 - x1);
        return RADIANS - Math.abs(Math.atan(slope)) < EPSILON;
    }
}
