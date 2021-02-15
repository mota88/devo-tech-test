package devo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Implements a delay in the execution of the current thread/flow. This serves the purpose
 * of our program, in which we just need to set a period of time after which the screen is
 * updated. In order to do that, unlike other possible solutions, it seems simpler to stop
 * the execution of the current thread, instead of creating new threads. 
 */
public class Delay {
    /** 
    *  
    * @param duration	Long that contains the duration in milliseconds
    */
   public void delay(final long durationInMillis) {
      delay(durationInMillis, TimeUnit.MILLISECONDS);
   }

   /** 
    * Delays the current thread execution and quits
    * immediately if the thread is interrupted
    * 
    * @param duration	Long that contains the time duration
    * @param unit		TimeUnit that contains the unit for time - always 
    * 					receives SECONDS, but can be modified  
    */
    public void delay(final long duration, final TimeUnit unit) {
        long currentTime = System.currentTimeMillis();
        long deadline = currentTime + unit.toMillis(duration);
        // thread synchronization mechanism
        ReentrantLock lock = new ReentrantLock();
        // provides the ability for the thread to wait for some condition to occur
        Condition waitCondition = lock.newCondition();
        // lock the thread for the given time and unlocks it after that  
        while ((deadline-currentTime)>0) {
            try {
                lock.lockInterruptibly();    
                waitCondition.await(deadline-currentTime, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                lock.unlock();
            }
            currentTime = System.currentTimeMillis();
        }
    }
}