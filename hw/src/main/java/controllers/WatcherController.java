package controllers;

import threads.WatcherThread;

import java.util.Scanner;

public class WatcherController {
    public void startWatcher(){
        Scanner in = new Scanner(System.in);

        System.out.println("Enter path: ");
        String mainPath = in.nextLine();

        System.out.println("Enter fileName: ");
        String fileName = in.nextLine();

        WatcherThread watcherThread = new WatcherThread(mainPath, fileName);
        watcherThread.start();
    }
}
