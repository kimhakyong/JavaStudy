package net.jackbauer.logging;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.log4j.Logger;

public class Log4jThread implements Runnable {
	private static final AtomicLong checkValue = new AtomicLong();
	private static final AtomicLong threadNumber = new AtomicLong();
	
	static final int LoopCount = 1000000;
	
	final int currentThreadCount;
	final Logger logger;
	
	public Log4jThread(int currentThreadCount, String loggerName) {
		this.logger = Logger.getLogger(loggerName);
		this.currentThreadCount = currentThreadCount;
	}
	
	@Override
	public void run() {
		String poem = "서시 죽는 날까지 하늘을 우러러 한점 부끄럼이 없기를, 잎새에 이는 바람에도 나는 괴로워했다. "
				+ "별을 노래하는 마음으로	모든 죽어가는 것을 사랑해야지 그리고 나한테 주어진 길을 걸어가야겠다."
				+ "오늘밤에도 별이 바람에 스치운다.";
		
		long start = System.currentTimeMillis();
		
		for (int i = 0; i < LoopCount; i++) {
			logger.info(String.format("increment : %d == current : %s", checkValue.incrementAndGet(), checkValue.get()));
			
			logger.info("info : " + poem);
			logger.debug("debug : " + poem);
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		long end = System.currentTimeMillis();
		long localThreadNumber = threadNumber.incrementAndGet();
		
		logger.info(String.format("LoggerName %s CurrentThreadCount %d ThreadNumber %d RunningTime %f", 
				logger.getName(), currentThreadCount, localThreadNumber, (end - start ) / 1000.0));
		
		System.out.println(String.format("LoggerName %s CurrentThreadCount %d ThreadNumber %d RunningTime %f", 
				logger.getName(), currentThreadCount, localThreadNumber, (end - start ) / 1000.0));
	}
}