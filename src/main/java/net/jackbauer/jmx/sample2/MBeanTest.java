package net.jackbauer.jmx.sample2;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class MBeanTest {
    public static void main(String args[]){
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName name;
        try{
            name = new ObjectName("hello:type=hello");
            Hello mbean = new Hello();
            server.registerMBean(mbean, name);
            System.out.println("Waiting forever");
            Thread.sleep(Long.MAX_VALUE);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
