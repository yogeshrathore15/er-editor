package ru.soultakov.remotecontrol.core.impl.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import ru.soultakov.remotecontrol.core.IJob;
import ru.soultakov.remotecontrol.core.IJobProvider;

public class ConsoleJobProvider implements IJobProvider {

    private final List<ConsoleJob> jobs = new ArrayList<ConsoleJob>();
    private boolean started;

    @Override
    public List<? extends IJob> getJobs() {
        synchronized (jobs) {
            startConsole();
            final ArrayList<ConsoleJob> jobsToReturn = new ArrayList<ConsoleJob>(jobs);
            jobs.clear();
            return jobsToReturn;
        }
    }

    private void startConsole() {
        if (!started) {
            final BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            final Thread inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            System.out.println("<Enter the next command> ");
                            final String readLine = userIn.readLine();
                            jobs.add(new ConsoleJob(readLine));
                        } catch (final IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "Console input thread");
            inputThread.setDaemon(true);
            inputThread.start();
            started = true;
        }
    }

}
