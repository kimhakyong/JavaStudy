package net.jackbauer.logging;

public class Log4jDiff {
	private static final int[] threadCount = {2, 4, 8, 16, 32};

	public static void main(String[] args) throws Exception {
		String loggerName;
		int currentThreadCount = 0;

		for (int i = 0; i < threadCount.length; i++) {
			Thread[] workingThread = new Thread[threadCount[i]];
			currentThreadCount = threadCount[i];

			for (int j = 0; j < threadCount[i]; j++) {
				loggerName = (j % 2 == 0) ? "Sync" : "Async";

				workingThread[j] = new Thread(new Log4jThread(currentThreadCount, loggerName));
				workingThread[j].start();
			}

			for (int j = 0; j < threadCount[i]; j++) {
				workingThread[j].join();
			}
		}
		
		System.out.println("git!!!");
	}
}