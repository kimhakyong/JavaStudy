package refactoring.java.r09_rtcwss;

public class Logger {
    private enum State {
        STOPPED {
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

        LOGGING {
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

    private State state;

    public Logger() {
        setState(State.STOPPED);
    }

    public void start() {
        state.start();
        setState(State.LOGGING);
    }

    public void stop() {
        state.stop();
        setState(State.STOPPED);
    }

    public void log(String info) {
        state.log(info);
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
