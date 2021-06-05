package refactoring.java.r09_rtcwss;

import java.sql.SQLOutput;

public enum State {
    STATE_STOPPED {
        @Override
        public void start() {
            System.out.println("** START LOGGING");
        }

        @Override
        public void stop() {
            /* empty */
        }

        @Override
        public void log(String info) {
            System.out.println("Ignoring: " + info);
        }
    },

    STATE_LOGGING {
        @Override
        public void start() {
            /* empty */
        }

        @Override
        public void stop() {
            System.out.println("** STOP LOGGING");
        }

        @Override
        public void log(String info) {
            System.out.println("Logging: " + info);
        }
    };

    public abstract void start();
    public abstract void stop();
    public abstract void log(String info);
}
