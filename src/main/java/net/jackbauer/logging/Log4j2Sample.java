package net.jackbauer.logging;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Sample {
	private static final int[] threadCount = {2, 4, 8, 16, 32};
	private static final AtomicLong checkValue = new AtomicLong();
	private static final AtomicLong threadNumber = new AtomicLong();
		
	private static int currentThreadCount = 0;
	
	public static void main(String[] args) {
		for (int i = 0; i < threadCount.length; i++) {
			Thread[] workingThread = new Thread[threadCount[i]];
			currentThreadCount = threadCount[i];
			threadNumber.set(0L);
			
			for (int j = 0; j < threadCount[i]; j++) {		
				workingThread[j] = new Thread() {
					final Logger logger = LogManager.getLogger(Log4j2Sample.class); 
					
					@Override
					public void run() {
						String poem = "서시 죽는 날까지 하늘을 우러러 한점 부끄럼이 없기를, 잎새에 이는 바람에도 나는 괴로워했다. "
								+ "별을 노래하는 마음으로	모든 죽어가는 것을 사랑해야지 그리고 나한테 주어진 길을 걸어가야겠다."
								+ "오늘밤에도 별이 바람에 스치운다.";
						
						long start = System.currentTimeMillis();
						
						for (int i = 0; i < 10000; i++) {
							logger.info(String.format("increment : %d == current : %s", checkValue.incrementAndGet(), checkValue.get()));
							logger.info(poem);
						}
						
						long end = System.currentTimeMillis();
						long localThreadNumber = threadNumber.incrementAndGet();
						
						logger.info(String.format("CurrentThreadCount %d ThreadNumber %d RunningTime %f", 
								currentThreadCount, localThreadNumber, (end - start ) / 1000.0));
						
						System.out.println(String.format("CurrentThreadCount %d ThreadNumber %d RunningTime %f", 
								currentThreadCount, localThreadNumber, (end - start ) / 1000.0));
					}
				};
				
				workingThread[j].start();
			}
			
			try {
				for (int j = 0; j < threadCount[i]; j++) {
					workingThread[j].join();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}