package ua.edu.cdu.vu.process;

import ua.edu.cdu.vu.resource.Resource;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.TimeUnit.SECONDS;

public record Process(Resource resource1, Resource resource2) implements Runnable {

    @Override
    public void run() {
        System.out.printf("%d - process started!%n", Thread.currentThread().getId());
        while (!Thread.interrupted()) {
            if (resource1.acquire()) {
                if (resource2.acquire()) {
                    process();
                    resource2.release();
                    sleep();
                }
                resource1.release();
            }
        }
    }

    private void process() {
        System.out.printf("%d - processing started!%n", Thread.currentThread().getId());
        sleep();
        System.out.printf("%d - processing finished!%n", Thread.currentThread().getId());
    }

    private void sleep() {
        try {
            SECONDS.sleep(ThreadLocalRandom.current().nextInt(6));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
