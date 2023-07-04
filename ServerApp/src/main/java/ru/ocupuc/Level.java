package ru.ocupuc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private int sizeX;
    private int sizeY;
    private List<Coordinate> walls;
    private Coordinate pacman;
    private List<Pill> pills;
    private int[][] levelMatrix;

    public Level(String filename) {
        walls = new ArrayList<>();
        pills = new ArrayList<>();
        loadLevelFromFile(filename);
        fillEmptyCellsWithPills();
        createLevelMatrix();
    }

    private void createLevelMatrix() {
        levelMatrix = new int[sizeX][sizeY];
        for (Pill pill : pills) {
            levelMatrix[pill.getX()][pill.getY()] = 1;  // 1 таблетка
        }
        for (Coordinate wall : walls) {
            levelMatrix[wall.getX()][wall.getY()] = 2;  // 2 стена
        }
        levelMatrix[pacman.getX()][pacman.getY()] = 3;  // 3  pacman
    }

    private void loadLevelFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean readingWalls = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("// Size")) {
                    // Пропускаем следующую строку
                    line = reader.readLine();
                    String[] size = line.split(";");
                    sizeX = Integer.parseInt(size[0]);
                    sizeY = Integer.parseInt(size[1]);
                } else if (line.startsWith("// Walls")) {
                    readingWalls = true;
                    // Пропускаем следующую строку
                    line = reader.readLine();
                } else if (readingWalls && !line.isEmpty()) {
                    String[] coordinates = line.split(";");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    walls.add(new Coordinate(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void fillEmptyCellsWithPills() {
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                Coordinate cell = new Coordinate(x, y);
                if (!isWall(cell.getX(), cell.getY())) {
                    // Если пакман еще не был инициализирован, этот метод устанавливает текущую ячейку как координату пакмана
                    if (pacman == null) {
                        pacman = new Coordinate(1, 1);
                        continue;
                    }
                    // добавляет новую таблетку, если ячейка не занята
                    pills.add(new Pill(cell.getX(), cell.getY()));
                }
            }
        }
    }

    public void removePill(Pill pill) {
        pills.remove(pill);
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public List<Coordinate> getWalls() {
        return walls;
    }

    public List<Pill> getPills() {
        return pills;
    }

    public boolean isWall(int x, int y) {
        return walls.contains(new Coordinate(x, y));
    }

    public int[][] getLevelMatrix() {
        return levelMatrix;
    }

    public void printLevelMatrix() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                System.out.print(levelMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public Coordinate getPacman() {
        return pacman;
    }
}