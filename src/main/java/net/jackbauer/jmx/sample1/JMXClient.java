package net.jackbauer.jmx.sample1;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JMXClient {
    public static void main(String[] args) throws Exception {
        String user = "monitorRole";
        String password = "QED";

        String[] credentials = new String[]{user, password};
        Map<String, String[]> props = new HashMap<String, String[]>();
        props.put("jmx.remote.credentials", credentials);
        JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:8991/jmxrmi");
        JMXConnector connector = null;

        try {
            connector = JMXConnectorFactory.connect(address, props);
            MBeanServerConnection mbs = connector.getMBeanServerConnection();
            ObjectName name = null;
            name = new ObjectName("java.lang:type=ClassLoading");
            System.out.println("* " + name);
            MBeanInfo mBeanInfo = mbs.getMBeanInfo(name);
            MBeanAttributeInfo[] attrInfos = mBeanInfo.getAttributes();
            for (MBeanAttributeInfo attrInfo : attrInfos) {
                System.out.println(attrInfo.getName() + " = " + mbs.getAttribute(name, attrInfo.getName()));
            }
            name = new ObjectName("java.lang:type=MemoryPool,*");
            Set<ObjectName> pools = mbs.queryNames(null, name);
            for (ObjectName on : pools) {
                System.out.println("* " + on);
                mBeanInfo = mbs.getMBeanInfo(on);
                MemoryUsage usage = MemoryUsage.from((CompositeData) mbs.getAttribute(on, "Usage"));
                System.out.println(usage);
            }
        } finally {
            if (connector != null) try {
                connector.close();
            } catch (IOException ie) {
            }
        }
    }
}
