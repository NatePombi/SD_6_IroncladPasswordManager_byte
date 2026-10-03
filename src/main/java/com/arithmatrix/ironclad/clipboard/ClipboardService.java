package com.arithmatrix.ironclad.clipboard;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClipboardService {

    private final ScheduledExecutorService schedular = Executors.newSingleThreadScheduledExecutor();

    public void copy(String password){

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        clipboard.setContents(
                new StringSelection(password),
                null
        );

        schedular.schedule(
                ()-> clearClipBoard(password),
                15,
                TimeUnit.SECONDS

        );
    }


    private void clearClipBoard(String password){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        try{
            String current = clipboard.getData(DataFlavor.stringFlavor).toString();

            if(current.equals(password)){
                clipboard.setContents(new StringSelection(""), null);
            }
        }

        catch (Exception e){

        }
    }

    public void shutDown(){
        schedular.shutdownNow();
    }
}
