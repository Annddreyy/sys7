import java.util.Objects;

class RabbitAndTurtle {
    public static AnimalThread rabbit;
    public static AnimalThread turtle;
    public static boolean first = true;

    public static void main(String[] args) {
        rabbit = new AnimalThread("Rabbit", Thread.MAX_PRIORITY);
        turtle = new AnimalThread("Turtle", Thread.NORM_PRIORITY);

        rabbit.start();
        turtle.start();
    }
}

class AnimalThread extends Thread {
    final int TRACE_DISTANCE = 100;
    private int distance = 0;
    boolean b = true;

    AnimalThread(String name, int priority) {
        setName(name);
        setPriority(priority);
    }

    @Override
    public void run() {
        while (distance < TRACE_DISTANCE) {

            try {
                // "Поток" засыпает на время, в зависимости от приоритета
                Thread.sleep((int)(1000L / getPriority()));

                // Проверка на увелчение расстояния между черепашкой и зайцем
                // (если расстояние слшком болшое, то меняем приоритет)
                if (Math.abs(RabbitAndTurtle.turtle.getDistance() - RabbitAndTurtle.rabbit.getDistance()) >= 5 && b) {
                    int prior = RabbitAndTurtle.turtle.getPriority();
                    RabbitAndTurtle.turtle.setPriority(RabbitAndTurtle.rabbit.getPriority());
                    RabbitAndTurtle.rabbit.setPriority(prior);
                }

                distance += 1;

                b = !b;

                String line = "";

                for (int i = 0; i < distance; i++) line += "_";
                if (Objects.equals(getName(), "Turtle")) line += "\uD80C\uDD89";
                else line += "\uD83D\uDC30";
                for (int i = distance; i < TRACE_DISTANCE; i++) line += "_";

                System.out.println(line);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getDistance() {
        return distance;
    }
}
