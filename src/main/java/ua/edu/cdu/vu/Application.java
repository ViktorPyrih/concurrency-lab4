package ua.edu.cdu.vu;

import ua.edu.cdu.vu.process.Process;
import ua.edu.cdu.vu.resource.Resource;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        // resources
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();
        Resource resource3 = new Resource();
        Resource resource4 = new Resource();
        Resource resource5 = new Resource();

        // processes
        Process process1 = new Process(resource1, resource2);
        Process process2 = new Process(resource2, resource3);
        Process process3 = new Process(resource3, resource4);
        Process process4 = new Process(resource4, resource5);
        Process process5 = new Process(resource5, resource1);

        // threads
        Thread thread1 = new Thread(process1);
        Thread thread2 = new Thread(process2);
        Thread thread3 = new Thread(process3);
        Thread thread4 = new Thread(process4);
        Thread thread5 = new Thread(process5);

        start(thread1, thread2, thread3, thread4, thread5);
        join(thread1, thread2, thread3, thread4, thread5);
    }

    private static void start(Thread ... threads) {
        Arrays.stream(threads).forEach(Thread::start);
    }

    private static void join(Thread ... threads) throws InterruptedException {
        for (var thread: threads) {
            thread.join();
        }
    }
}
