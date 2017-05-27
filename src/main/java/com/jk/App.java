package com.jk;

import com.jk.job.HelloJop;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SchedulerException, InterruptedException {
        /**
         * 1.获取到Scheduler实例；
         * 2.创建具体的任务jobDetail；
         * 3.创建触发时间点 trigger；
         * 4.将jobDetail和trigger交由scheduler安排出发；
         * 5.睡眠20秒，关闭定是任务调度器；
         */

        //1.获取到Scheduler实例；
        System.out.println("scheduler.start.");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        System.out.println(scheduler.getSchedulerName());

        //2.创建具体的任务jobDetail；
        JobDetail jobDetail = JobBuilder.newJob(HelloJop.class).withIdentity("job1","group1").build();

        //触发时间点 5秒
        ScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever();
        //3.创建触发时间点 trigger；
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1","group1").startNow().withSchedule(schedBuilder).build();

        //4.将jobDetail和trigger交由scheduler安排出发；
        scheduler.scheduleJob(jobDetail,trigger);

        //5.睡眠20秒，关闭定是任务调度器；
        TimeUnit.SECONDS.sleep(20);
        scheduler.shutdown();
        System.out.println("scheduler.shutdown.");
    }
}
