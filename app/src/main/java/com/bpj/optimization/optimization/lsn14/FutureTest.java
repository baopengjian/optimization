package com.bpj.optimization.optimization.lsn14;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Ray on 2020-2-19.
 */
public class FutureTest {

    public static void main(String[] args) {
        Task work = new Task();
        FutureTask<Integer> future = new FutureTask<Integer>(work){
            @Override
            protected void done() {
                try {
                    System.out.println("done:"+get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //线程池（使用了预定义的配置）
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(future);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //取消异步任务
        future.cancel(true);

        try {
            //阻塞，等待异步任务执行完毕
            System.out.println(future.get()); //获取异步任务的返回值
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //异步任务
    static class Task implements Callable<Integer>{

        //返回异步任务的执行结果
        @Override
        public Integer call() {
            int i = 0;
            for(;i < 10;i++){
                System.out.println(Thread.currentThread().getName()+"_"+i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return i;
        }
    }
}
