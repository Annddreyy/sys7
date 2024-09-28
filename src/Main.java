import java.util.Objects;

class RabbitAndTurtle {
    public static AnimalThread rabbit;
    public static AnimalThread turtle;

    public static void main(String[] args) {
        rabbit = new AnimalThread("Rabbit", Thread.MAX_PRIORITY);
        turtle = new AnimalThread("Turtle", Thread.NORM_PRIORITY);

        rabbit.start();
        turtle.start();
    }
}

class AnimalThread extends Thread {
    final int TRACE_DISTANCE = 100;
    int distance = 0;
    String name;
    int priority;
    AnimalThread(String name, int priority) {
        this.name = name;
        this.priority = priority;
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
                if (Objects.equals(getName(), "Turtle") && distance < RabbitAndTurtle.rabbit.getDistance() - 10) {
                    setPriority(Thread.MAX_PRIORITY);
                    RabbitAndTurtle.rabbit.setPriority(Thread.NORM_PRIORITY);
                }
                else if (Objects.equals(getName(), "Rabbit") && distance < RabbitAndTurtle.turtle.getDistance() - 10) {
                    setPriority(Thread.MAX_PRIORITY);
                    RabbitAndTurtle.turtle.setPriority(Thread.NORM_PRIORITY);
                }


                distance += 1;

                String line = "";

                for (int i = 0; i < distance; i++) line += "_";
                if (Objects.equals(name, "Turtle")) line += "\uD80C\uDD89";
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
