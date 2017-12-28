package threads;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class WatcherThread extends Thread {
    private String mainPath;
    private String fileName;
    private String resultPath;

    public WatcherThread(String mainPath, String file) {
        this.mainPath = mainPath;
        this.fileName = file;
        resultPath = file + "_notifier.txt";
    }

    @Override
    public void run() {
        try {
            Path path = Paths.get(mainPath);
            WatchService watchService = path.getFileSystem().newWatchService();

            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

            File file = new File(fileName);
            FileWriter fileWriter = new FileWriter(resultPath);

            int i = 0;

            while (true) {
                WatchKey take = watchService.take();
                List<WatchEvent<?>> watchEvents = take.pollEvents();

                for (WatchEvent watchEvent : watchEvents) {
                    Path changed = (Path) watchEvent.context();


                    if (changed.endsWith(fileName)) {
                        switch (watchEvent.kind().name()) {
                            case "ENTRY_DELETE":
                                System.out.println("ENTRY_DELETE " + fileName);
                                fileWriter.write("ENTRY_DELETE " + fileName + " ");
                                break;
                            case "ENTRY_CREATE":
                                System.out.println("ENTRY_CREATE " + fileName);
                                fileWriter.write("ENTRY_DELETE " + fileName + " ");
                                break;
                            case "ENTRY_MODIFY":
                                System.out.println("ENTRY_MODIFY " + fileName);
                                fileWriter.write("ENTRY_MODIFY " + fileName + " ");
                                break;
                        }
                        i++;
                    }
                }
                take.reset();
                if (i == 3) {
                    fileWriter.flush();
                    fileWriter.close();
                    break;
                }
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }


    }
}
