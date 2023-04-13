package ua.edu.cdu.vu.process;

import ua.edu.cdu.vu.resource.Resource;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.SECONDS;

public record Process(Resource resource1, Resource resource2) implements Runnable {

    private static final Logger log = Logger.getLogger(Process.class.getName());

    @Override
    public void run() {
        log.info("%d - process started!%n".formatted(Thread.currentThread().getId()));
        while (!Thread.interrupted()) {
            if (resource1.acquire()) {
                log.info("%d - resource1 acquired!%n".formatted(Thread.currentThread().getId()));
                if (resource2.acquire()) {
                    log.info("%d - resource2 acquired!%n".formatted(Thread.currentThread().getId()));
                    process();
                    resource2.release();
                    sleep();
                }
                resource1.release();
            }
        }
    }

    private void process() {
        log.info("%d - processing started!%n".formatted(Thread.currentThread().getId()));
        sleep();
        log.info("%d - processing finished!%n".formatted(Thread.currentThread().getId()));
    }

    private void sleep() {
        try {
            SECONDS.sleep(ThreadLocalRandom.current().nextInt(6));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
