import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JFrame {
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;
    private static final int CELL_SIZE = 25;
    private static final int INITIAL_SNAKE_LENGTH = 3;
    private static final int TIMER_DELAY = 100;

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private ArrayList<Point> snake;
    private Point food;
    private Direction direction = Direction.RIGHT;
    private boolean gameRunning = true;
    private Timer timer;
    private int score = 0;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        snake = new ArrayList<>();
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            snake.add(new Point(BOARD_WIDTH / 2 - i, BOARD_HEIGHT / 2));
        }

        generateFood();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        if (direction != Direction.DOWN) direction = Direction.UP;
                        break;
                    case KeyEvent.VK_DOWN:
                        if (direction != Direction.UP) direction = Direction.DOWN;
                        break;
                    case KeyEvent.VK_LEFT:
                        if (direction != Direction.RIGHT) direction = Direction.LEFT;
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (direction != Direction.LEFT) direction = Direction.RIGHT;
                        break;
                }
            }
        });

        timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameRunning) {
                    moveSnake();
                    checkCollision();
                    repaint();
                }
            }
        });
        timer.start();
    }

    private void moveSnake() {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case UP -> newHead.translate(0, -1);
            case DOWN -> newHead.translate(0, 1);
            case LEFT -> newHead.translate(-1, 0);
            case RIGHT -> newHead.translate(1, 0);
        }

        snake.add(0, newHead);
        if (newHead.equals(food)) {
            score++;
            generateFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    private void checkCollision() {
        Point head = snake.get(0);

        // Check collision with bounds
        if (head.x < 0 || head.x >= BOARD_WIDTH || head.y < 0 || head.y >= BOARD_HEIGHT) {
            gameRunning = false;
        }

        // Check collision with itself
        for (int i = 1; i < snake.size(); i++) {
            if (head.equals(snake.get(i))) {
                gameRunning = false;
            }
        }
        
        if (!gameRunning) {
            timer.stop();
            int option = JOptionPane.showOptionDialog(this, "Game Over!\nScore: " + score + "\nRestart?",
                "Game Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                restartGame();
            }
        }
    }

    private void generateFood() {
        Random rand = new Random();
        do {
            food = new Point(rand.nextInt(BOARD_WIDTH), rand.nextInt(BOARD_HEIGHT));
        } while (snake.contains(food));
    }

    private void restartGame() {
        snake.clear();
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            snake.add(new Point(BOARD_WIDTH / 2 - i, BOARD_HEIGHT / 2));
        }
        direction = Direction.RIGHT;
        score = 0;
        gameRunning = true;
        generateFood();
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // Draw the background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, BOARD_WIDTH * CELL_SIZE, BOARD_HEIGHT * CELL_SIZE);

        // Draw the snake
        g.setColor(Color.GREEN);
        for (Point point : snake) {
            g.fillRect(point.x * CELL_SIZE, point.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Draw the food
        g.setColor(Color.RED);
        g.fillRect(food.x * CELL_SIZE, food.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        // Draw the score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 5, getHeight() - 5);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}