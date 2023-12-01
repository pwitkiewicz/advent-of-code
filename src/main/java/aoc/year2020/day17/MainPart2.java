package aoc.year2020.day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MainPart2 {
    private static class Cube {
        int x;
        int y;
        int z;
        int w;
        char state;

        public Cube(int _x, int _y, int _z, int _w, char _state) {
            x = _x;
            y = _y;
            z = _z;
            w = _w;
            state = _state;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cube cube = (Cube) o;
            return x == cube.x && y == cube.y && z == cube.z && w == cube.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, w);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            try {
                return super.clone();
            } catch (CloneNotSupportedException e) {
                return new Cube(this.x, this.y, this.z, this.w, this.state);
            }
        }

        public void cycle(HashMap<Cube, Cube> cubeMap) {
            int activeNeighbours = 0;

            for (int i = this.x - 1; i <= this.x + 1; i++) {
                for (int j = this.y - 1; j <= this.y + 1; j++) {
                    for (int k = this.z - 1; k <= this.z + 1; k++) {
                        for (int l = this.w - 1; l <= this.w + 1; l++) {
                            Cube temp = new Cube(i, j, k, l, '.');
                            if (this.equals(temp)) {
                                continue;
                            }

                            if (cubeMap.containsKey(temp)) {
                                if (cubeMap.get(temp).state == '#')
                                    activeNeighbours++;
                            }
                        }
                    }
                }
            }

            if (activeNeighbours == 3 && state == '.') {
                state = '#';
            }

            if (state == '#' && !(activeNeighbours == 2 || activeNeighbours == 3)) {
                state = '.';
            }
        }
    }

    private static Set<Cube> addNeighbours(Set<Cube> cubeSet) {
        Set<Cube> extendedCubeSet = new HashSet<>(cubeSet);

        for (Cube c : cubeSet) {
            for (int i = c.x - 1; i <= c.x + 1; i++) {
                for (int j = c.y - 1; j <= c.y + 1; j++) {
                    for (int k = c.z - 1; k <= c.z + 1; k++) {
                        for (int l = c.w - 1; l <= c.w + 1; l++) {
                            Cube temp = new Cube(i, j, k, l, '.');
                            if (c.equals(temp) || extendedCubeSet.contains(temp)) {
                                continue;
                            }

                            extendedCubeSet.add(temp);
                        }
                    }
                }
            }
        }
        return extendedCubeSet;
    }

    private static Set<Cube> readFile(String filename) {
        int z = 0;
        int x = 0;
        int y = 0;
        int w = 0;

        Set<Cube> cubeSet = new HashSet<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                for (char state : line.toCharArray()) {
                    cubeSet.add(new Cube(x, y++, z, w, state));
                }
                x++;
                y = 0;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cubeSet;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Set<Cube> input = readFile("input-day17.txt");

        for (int i = 0; i < 6; i++) {
            HashMap<Cube, Cube> initialState = new HashMap<>();

            for (Cube c : input) {
                initialState.put((Cube) c.clone(), (Cube) c.clone());
            }

            input = addNeighbours(input);

            for (Cube c : input) {
                c.cycle(initialState);
            }
        }

        int turnedOnCubes = 0;
        for (Cube c : input) {
            if (c.state == '#') {
                turnedOnCubes++;
            }
        }

        System.out.println(turnedOnCubes);
    }
}
