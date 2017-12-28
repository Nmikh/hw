package tests;

import controllers.WatcherController;

public class Test {
    public static void main(String[] args) {
        WatcherController watcherController = new WatcherController();

        watcherController.startWatcher();
        watcherController.startWatcher();

    }
}
