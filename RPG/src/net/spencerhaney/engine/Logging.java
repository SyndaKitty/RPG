package net.spencerhaney.engine;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging
{
    public static void fine(Object callingObject, String message)
    {
        Logger.getLogger(callingObject.getClass().getName()).log(Level.FINE, message);;
    }
}
