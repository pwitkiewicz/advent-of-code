package aoc.year2021.day17;

public class Day17 {
    private static int moveProbe(final Probe probe) {
        probe.position().setX(probe.position().getX() + probe.velocity().getX());
        probe.position().setY(probe.position().getY() + probe.velocity().getY());

        var currentXVelocity = probe.velocity().getX();
        if (currentXVelocity < 0) {
            probe.velocity().setX(currentXVelocity + 1);
        } else if (currentXVelocity > 0) {
            probe.velocity().setX(currentXVelocity - 1);
        }

        probe.velocity().setY(probe.velocity().getY() -1);

        return probe.position().getY();
    }

    private static boolean isInArea(final Probe probe, final Area area) {
        return probe.position().getX() >= area.x1() &&
                probe.position().getX() <= area.x2() &&
                probe.position().getY() <= area.y1() &&
                probe.position().getY() >= area.y2();
    }

    private static boolean hasPassedArea(final Probe probe, final Area area) {
        return probe.position().getX() > area.x2() ||
                probe.position().getY() < area.y2();
    }

    public static void main(String[] args) {
        Area area = new Area(248, 285, -56, -85);
        var max = 0;
        var count = 0;

        for (int i = 0; i < 1000; i++) {
            for (int j = -1000; j < 1000; j++) {
                Probe probe = new Probe(new Velocity(i, j), new Position(0,0));
                var currentMax = 0;
                while(!hasPassedArea(probe, area)) {
                    var height = moveProbe(probe);
                    if(height > currentMax) currentMax = height;
                    if(isInArea(probe, area)) {
                        if(currentMax > max) max = currentMax;
                        count++;
                        break;
                    }
                }
            }
        }

        System.out.println("Max Y: " + max);
        System.out.println("Number of combinations: " + count);
    }
}
