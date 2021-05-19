//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.newland.research.serviceKeeper.listener;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.newland.research.serviceKeeper.common.Constant;
import com.newland.research.serviceKeeper.error.GlobaleException;
import com.newland.research.serviceKeeper.utils.HttpUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceListener implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger log = LoggerFactory.getLogger(ServiceListener.class);
    @Autowired
    HttpUtils httpUtils;
    private volatile Long endTime = 0L;
    private static final Object object=new Object();
    public ServiceListener() {
    }

    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        String usrHome = System.getProperty("user.home") + "/ceshi.txt";
        File file = new File(usrHome);
        String fileContent = txt2String(file);
        this.createNocaceTime(fileContent);
    }

    public static void main(String[] args) {

       /* ServiceListener serviceListener=new ServiceListener();
        serviceListener.createFixed("aaa");*/

    }

    private void createNocaceTime(String fileContent) {
        ThreadFactory scanerThreadFactory = (new ThreadFactoryBuilder()).setNameFormat("naiTaskFlowListener-pool-%d").build();
        Integer size = Integer.valueOf(Constant.nums[0]);
        ExecutorService scanerThreadPool = new ThreadPoolExecutor(size, size, 0L, TimeUnit.MILLISECONDS, new SynchronousQueue(), scanerThreadFactory, new DiscardPolicy());
        AtomicLong total = new AtomicLong();
        AtomicLong successCount = new AtomicLong();
        AtomicLong failCount = new AtomicLong();
        AtomicLong totalTimes = new AtomicLong();

        Long currentTime = Long.valueOf(Constant.nums[1]);
        long beginTime = System.currentTimeMillis() + currentTime * 1000L;
        long beginTimes = System.currentTimeMillis();
        this.endTime = beginTimes;
        String sysServiceUrl = Constant.nums[2];
        Map<Long, Long> longLongConcurrentHashMap = new ConcurrentHashMap<>();

        while (System.currentTimeMillis() < beginTime) {
            scanerThreadPool.execute(() -> {
                long beginSendTime = System.currentTimeMillis();
                try {
                    this.send(fileContent, sysServiceUrl);
                    successCount.incrementAndGet();

                } catch (GlobaleException var15) {
                    failCount.incrementAndGet();
                }
                synchronized (object) {
                    Long time = System.currentTimeMillis() - beginSendTime;
                    totalTimes.addAndGet(time);
                    longLongConcurrentHashMap.put(time, (longLongConcurrentHashMap.get(time) == null ? 0l : longLongConcurrentHashMap.get(time)) + 1);
                   total.incrementAndGet();
                  Long currentTimes = System.currentTimeMillis() - Long.valueOf(Constant.nums[7]) * 1000L;
                  if (this.endTime <= currentTimes) {
                    this.endTime = System.currentTimeMillis();
                    String totalNum = "总的请求次数：" + total.get();
                    String successlNum = "成功请求次数：" + successCount.get();
                    String faillNum = "失败请求次数：" + failCount.get();
                    Long endTimes = System.currentTimeMillis();
                    String totalTime = "总的请求时间：：" + (endTimes - beginTimes) / 1000L;
                    String per = "吞吐率：：" + successCount.get() / ((endTimes - beginTimes) / 1000L);
                    String allTimes="所有总的请求时间:"+totalTimes.get();
                    log.info(totalNum);
                    log.info(successlNum);
                    log.info(faillNum);
                    log.info(totalTime);
                    log.info(per);
                    log.info(allTimes);

                }
                }
            });
        }
        scanerThreadPool.shutdown();
        while (!scanerThreadPool.isTerminated()) {
        }
        log.info("线程执行完成：" + scanerThreadPool.isTerminated());
        List<String> lists = new ArrayList();
        String totalNum = "总的请求次数：" + total.get();
        String successlNum = "成功请求次数：" + successCount.get();
        String faillNum = "失败请求次数：" + failCount.get();
        Long endTimes = System.currentTimeMillis();
        String totalTime = "总的请求时间：：" + (endTimes - beginTimes) / 1000L;
        String allTimes="所有总的请求时间:"+totalTimes.get();
        String per = "吞吐率：：" + successCount.get() / ((endTimes - beginTimes) / 1000L);
        Long T95 = new Double(total.get() * 0.95).longValue();
        Long T90 = new Double(total.get() * 0.90).longValue();
        Long T99 = new Double(total.get() * 0.99).longValue();
        Map<Long, Long> testMap = new TreeMap<Long, Long>(longLongConcurrentHashMap);
        Boolean T95Flag = true;
        Boolean T90Flag = true;

        Boolean T99Flag = true;

        Long allCount = 0l;
        for (Long key : testMap.keySet()) {
            allCount = allCount + testMap.get(key) ;
            if (T90 <= allCount && T90Flag) {
                T90Flag = false;
                T90 = key;
            }
            if (T95 <= allCount && T95Flag) {
                T95Flag = false;
                T95 = key;
            }
            if (T99 <= allCount && T99Flag) {
                T99Flag = false;
                T99 = key;
            }
        }
        log.info("allCount:"+allCount);
        String strT90="T90:"+T90;
        String strT95="T90:"+T95;
        String strT99="T90:"+T99;

        log.info("T90：：" + T90);
        log.info("T95：：" + T95);
        log.info("T99：：" + T99);

        lists.add(totalNum);
        lists.add(successlNum);
        lists.add(faillNum);
        lists.add(totalTime);
        lists.add(per);
        lists.add(strT90);
        lists.add(strT95);
        lists.add(strT99);
        lists.add(strT99);
        lists.add(allTimes);


        log.info("executorService.isTerminated()：：" + scanerThreadPool.isTerminated());
        log.info(totalNum);
        log.info(successlNum);
        log.info(faillNum);
        log.info(totalTime);
        log.info(per);
        log.info(allTimes);

        String usrHome = System.getProperty("user.home") + "/result.txt";
        this.WriteStringToFile3(usrHome, lists);
        System.exit(1);
    }

    public void WriteStringToFile3(String filePath, List<String> lists) {
        try {
            File file = new File(filePath);
            this.delet(file);
            this.create(file);
            PrintWriter pw = new PrintWriter(new FileWriter(filePath));
            Iterator var5 = lists.iterator();

            while (var5.hasNext()) {
                String str = (String) var5.next();
                pw.println(str);
            }

            pw.close();
        } catch (IOException var7) {
            var7.printStackTrace();
        }

    }

    public void create(File file) throws IOException {
        if (!file.exists()) {
            boolean isCreate = file.createNewFile();
            if (isCreate) {
                System.out.println("创建成功!");
            }
        } else {
            System.out.println("该重名文件已存在!");
        }

    }

    public void delet(File file) {
        if (file.exists()) {
            boolean isDelete = file.delete();
            if (isDelete) {
                System.out.println("删除成功!");
            }
        } else {
            System.out.println("没找到你要删除的文件");
        }

    }

    private void createFixed(String fileContent) {
        final AtomicLong successCount = new AtomicLong();
        final AtomicLong failCount = new AtomicLong();
        final AtomicLong total = new AtomicLong();
        new Object();
        new Object();
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        long beginTimes = System.currentTimeMillis();
        log.info("executorService.isTerminated()：：" + System.currentTimeMillis());
        ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();

        for (int j = 0; j < 30000; ++j) {
            executorService.submit(new Runnable() {
                public void run() {
                    log.info(Thread.currentThread().getName());
                    total.incrementAndGet();
                  /*  try {*/
                        long beginSendTime = System.currentTimeMillis();

                        /*this.send(fileContent, null);*/

                      /*  Thread.sleep((int) (Math.random()*(100-1)+1));*/
                        synchronized (object) {
                            concurrentHashMap.put("sss", (concurrentHashMap.get("sss") == null ? 0 : concurrentHashMap.get("sss")) + 1);
                        }
                        successCount.incrementAndGet();
                 /*  } catch (GlobaleException var2) {
                        failCount.incrementAndGet();
                    }*/ /*catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/

                }


            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }
        System.out.println(concurrentHashMap.get("sss"));
        log.info("executorService.isTerminated()：：" + executorService.isTerminated());
        log.info("总的请求次数：" + total.get());
        log.info("成功请求次数：" + successCount.get());
        log.info("失败请求次数：" + failCount.get());
        Long endTimes = System.currentTimeMillis();
        log.info("总的请求时间：：" + (endTimes - beginTimes) / 1000L);
        log.info("吞吐率：：" + successCount.get() / ((endTimes - beginTimes) / 1000L));
        System.exit(1);
    }

    private static void createScheduledThreadPoolSum() {
        AtomicLong successCount = new AtomicLong();
        AtomicLong failCount = new AtomicLong();
        AtomicLong total = new AtomicLong();
        long beginTimes = System.currentTimeMillis();
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        for (int i = 0; i < 1000; ++i) {
            executorService.schedule(() -> {
                System.out.println("线程名字： " + Thread.currentThread().getName());
                total.incrementAndGet();
                boolean var3 = false;

                try {
                    int sleep = (new Random()).nextInt(100) + 1;
                    Thread.sleep((long) sleep);
                } catch (InterruptedException var5) {
                    var5.printStackTrace();
                }

                Integer newInteger = (new Random()).nextInt(10) + 1;
                if (newInteger / 2 == 0) {
                    successCount.incrementAndGet();
                } else {
                    failCount.incrementAndGet();
                }

            }, 3L, TimeUnit.SECONDS);
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }

        System.out.println("executorService.isTerminated()：：" + executorService.isTerminated());
        System.out.println("总的请求次数：" + total.get());
        System.out.println("成功请求次数：" + successCount.get());
        System.out.println("失败请求次数：" + failCount.get());
        Long endTimes = System.currentTimeMillis();
        System.out.println("总的请求时间：：" + (endTimes - beginTimes) / 1000L);
        System.out.println("吞吐率：：" + successCount.get() / ((endTimes - beginTimes) / 1000L));
    }

    private static void createScheduledThreadPool() {
        AtomicLong successCount = new AtomicLong();
        AtomicLong failCount = new AtomicLong();
        AtomicLong total = new AtomicLong();
        int currentTime = 60;
        long beginTime = System.currentTimeMillis() + (long) (currentTime * 1000);
        long beginTimes = System.currentTimeMillis();
        System.out.println("Thread:" + Thread.currentThread());

        while (System.currentTimeMillis() < beginTime) {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

            for (int i = 0; i < 1000; ++i) {
                executorService.schedule(() -> {
                    System.out.println("线程名字： " + Thread.currentThread().getName());
                    total.incrementAndGet();
                    boolean var3 = false;

                    try {
                        int sleep = (new Random()).nextInt(100) + 1;
                        Thread.sleep((long) sleep);
                    } catch (InterruptedException var5) {
                        var5.printStackTrace();
                    }

                    Integer newInteger = (new Random()).nextInt(10) + 1;
                    if (newInteger / 2 == 0) {
                        successCount.incrementAndGet();
                    } else {
                        failCount.incrementAndGet();
                    }

                }, 1L, TimeUnit.SECONDS);
            }

            executorService.shutdown();

            while (total.get() != successCount.get() + failCount.get()) {
            }

            System.out.println("executorService.isTerminated()：：" + executorService.isTerminated());
            System.out.println("总的请求次数：" + total.get());
            System.out.println("成功请求次数：" + successCount.get());
            System.out.println("失败请求次数：" + failCount.get());
            Long endTimes = System.currentTimeMillis();
            System.out.println("总的请求时间：：" + (endTimes - beginTimes) / 1000L);
            System.out.println("吞吐率：：" + successCount.get() / ((endTimes - beginTimes) / 1000L));
        }

    }

    public static String txt2String(File file) {
        String result = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;

            for (result = br.readLine(); (s = br.readLine()) != null; result = result + "\n" + s) {
            }

            br.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public String send(String fileContent, String sysServiceUrl) throws GlobaleException {
        return this.httpUtils.initsysService(fileContent, sysServiceUrl);
    }
}
