package ua.edu.cdu.vu.resource;

import java.util.concurrent.locks.ReentrantLock;

public class Resource implements AutoCloseable {

    private final ReentrantLock lock = new ReentrantLock(true);

    public boolean acquire() {
        return lock.tryLock();
    }

    public void release() {
        lock.unlock();
    }

    @Override
    public void close() {
        release();
    }
}
