package com.bpj.optimization.optimization.lsn14;

/**
 * Created by Ray on 2020-2-19.
 */
public class ThreadTest {

    //产品
    static class ProductObject {
        //线程操作变量可见
        public volatile static String value;
    }

    //生产者线程
    static class Producer extends Thread {
        Object lock;

        public Producer(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            //不断生产产品
            while (true) {
                synchronized (lock) {//互斥锁
                    //产品还没有被消费，等待
                    if (ProductObject.value != null) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //产品已经消费完成，生产新的产品
                    ProductObject.value = "NO:" + System.currentTimeMillis();
                    System.out.print("生产产品：" + ProductObject.value);
                    lock.notify();
                }
            }
        }
    }

    //消费者线程
    static class Consumer extends Thread {
        Object lock;

        public  Consumer(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            while (true){
                synchronized (lock){
                    //没有产品可以消费
                    if(ProductObject.value == null){
                        //等待，阻塞
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.print("消费产品："+ProductObject.value);
                    ProductObject.value = null;
                    lock.notify();//消费完成，通知生产者继续生产
                }
            }
        }
    }

    public static void main(String[] args){
        Object lock = new Object();
        new Producer(lock).start();
        new Consumer(lock).start();
    }
}
