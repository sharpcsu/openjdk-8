import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by @author 杨威 on 2021/3/15 0015 - 15:48
 */
public class current {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
