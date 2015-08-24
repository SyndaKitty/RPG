package net.spencerhaney.engine;

import java.io.PrintStream;
import java.sql.Timestamp;
import java.util.Date;

public class Logging
{
    private static final String OUTPUT = "[%s : %s] %s\n";

    private static PrintStream stream;
    private static PrintStream errorStream;
    private static Level level;

    private enum Level
    {
        ALL, FINEST, FINE, INFO, WARNING, SEVERE, NONE;
    }

    public static void init()
    {
        stream = System.out;
        errorStream = System.err;
        level = Level.FINEST;
    }

    public static void finest(String message)
    {
        if (level.compareTo(Level.FINEST) <= 0)
        {
            stream.printf(OUTPUT, Level.FINEST, getTimestamp(), message);
        }
    }

    public static void fine(String message)
    {
        if (level.compareTo(Level.FINE) <= 0)
        {
            stream.printf(OUTPUT, Level.FINE, getTimestamp(), message);
        }
    }

    public static void info(String message)
    {
        if (level.compareTo(Level.INFO) <= 0)
        {
            stream.printf(OUTPUT, Level.INFO, getTimestamp(), message);
        }
    }

    public static void warning(String message)
    {
        if (level.compareTo(Level.WARNING) <= 0)
        {
            stream.printf(OUTPUT, Level.WARNING, getTimestamp(), message);
        }
    }

    public static void severe(String message)
    {
        if (level.compareTo(Level.SEVERE) <= 0)
        {
            stream.printf(OUTPUT, Level.SEVERE, getTimestamp(), message);
        }
    }

    public static void warning(String message, Throwable t)
    {
        if (level.compareTo(Level.WARNING) <= 0)
        {
            String timestamp = getTimestamp();
            if (!"".equals(message))
            {
                errorStream.printf(OUTPUT, Level.WARNING, timestamp, message);
            }
            if (t.getMessage() != null)
            {
                errorStream.printf(OUTPUT, Level.WARNING, timestamp, t.getMessage());
            }
        }
    }

    public static void severe(String message, Throwable t)
    {
        if (level.compareTo(Level.SEVERE) <= 0)
        {
            String timestamp = getTimestamp();
            if (!"".equals(message))
            {
                errorStream.printf(OUTPUT, Level.SEVERE, timestamp, message);
            }
            if (t.getMessage() != null)
            {
                errorStream.printf(OUTPUT, Level.SEVERE, timestamp, t.getMessage());
            }
        }
    }

    public static void warning(Throwable t)
    {
        if (level.compareTo(Level.WARNING) <= 0)
        {
            if (t.getMessage() != null)
            {
                errorStream.printf(OUTPUT, Level.WARNING, getTimestamp(), t.getMessage());
            }
        }
    }

    public static void severe(Throwable t)
    {
        if (level.compareTo(Level.SEVERE) <= 0)
        {
            if (t.getMessage() != null)
            {
                errorStream.printf(OUTPUT, Level.SEVERE, getTimestamp(), t.getMessage());
            }
        }
    }
    
    private static String getTimestamp()
    {
        return new Timestamp(new Date().getTime()).toString();
    }
}
