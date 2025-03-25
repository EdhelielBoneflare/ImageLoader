package me.gruzdeva.imageLoader.service;

import me.gruzdeva.Except4Support;
import me.gruzdeva.imageLoader.conf.js.ConfJsAppImageLoader;
import me.gruzdeva.imageLoader.conf.js.ConfJsImageLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoadImageService {
    private static final Logger logger = LoggerFactory.getLogger(LoadImageService.class);

    private final ConfJsAppImageLoader config = ConfJsImageLoader.getInstance().getApp();
    private final AtomicInteger connectionErrorCounter = new AtomicInteger(0);
    private static final String CONNECTION_ERR_CODE = "DownloadErr01";

    public Map<Boolean, Integer> downloadImages(String directory, List<URL> imgUrls)
            throws Except4Support {
        ExecutorService executorService = Executors.newFixedThreadPool(config.getThreadPoolSize());

        Map<Boolean, Integer> result = new HashMap<>();
        result.put(true, 0);
        result.put(false, 0);

        List<Future<Boolean>> futures = new ArrayList<>();
        for (URL imgUrl : imgUrls) {
            Future<Boolean> future = executorService.submit(new DownloadImageTask(imgUrl, directory));
            futures.add(future);
        }

        try {
            for (Future<Boolean> future : futures) {
                result.put(future.get(), result.get(future.get()) + 1);
            }

            executorService.shutdown();
            if (!executorService.awaitTermination(config.getShutdownTimerMinutes(), TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (ExecutionException ex) {
            throw new Except4Support("UnknownFileDownloadErr",
                    "Can't download file",
                    "Error during file download: " + ex.getMessage());
        } catch (InterruptedException ignored) {}

        executorService.shutdown();


        return result;
    }

    private File prepareFile(URL imgUrl, String directory) {
        StringBuilder destinationFilePath;

        String[] urlParts = imgUrl.toString().split("/");
        destinationFilePath = new StringBuilder(directory);
        destinationFilePath.append(File.separator).append(urlParts[urlParts.length - 1]);
        File destinationFile = new File(destinationFilePath.toString());

        if (!destinationFile.exists()) {
            try {
                if (!destinationFile.createNewFile() || !destinationFile.canWrite())
                    throw new IOException();
            } catch (IOException ex) {
                throw new Except4Support("ErrFileCreation02",
                        "Can't create or use file ",
                        "File doesn't exist and can't be created:" + destinationFile.getAbsolutePath());
            }
        }
        return destinationFile;
    }

    private void downloadImage(URL imgUrl, File destinationFile) throws Except4Support {
        try (InputStream in = imgUrl.openStream()) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            try {
                while ((bytesRead = in.read(buffer)) != -1) {
                    try (OutputStream out = new FileOutputStream(destinationFile)) {
                        out.write(buffer, 0, bytesRead);
                    } catch (IOException ex) {
                        throw new Except4Support("DownloadErr02", "Couldn't write into file",
                                "Error downloading image: " + imgUrl + " into file: " + destinationFile);
                    }
                }
            } catch (IOException ex) {
                throw new Except4Support("DownloadErr03", "Corrupted file",
                        "Could not download image: " + imgUrl + " due to stream corruption");
            }
        } catch (IOException ex) {
            throw new Except4Support(CONNECTION_ERR_CODE, "Connection error", "Connection error, " +
                    "could not download image: " + imgUrl + " into file: " + destinationFile);
        }
    }

    private class DownloadImageTask implements Callable<Boolean> {
        private final URL imgUrl;
        private final String directory;
        private boolean isCompletedOk = false;

        public DownloadImageTask(URL imgUrl, String directory) {
            this.imgUrl = imgUrl;
            this.directory = directory;
        }

        @Override
        public Boolean call() {
            try {
                File destinationFile = prepareFile(imgUrl, directory);
                downloadImage(imgUrl, destinationFile);
                isCompletedOk = true;
                connectionErrorCounter.set(0);
            } catch (Except4Support ex) {
                isCompletedOk = false;
                if (CONNECTION_ERR_CODE.equals(ex.getErrorCode())) {
                    if (connectionErrorCounter.incrementAndGet() >= config.getConnectionErrorThreshold()) {
                        logger.error("Connection error limit reached. Problems with the Internet connection");
                    }
                }
            }
            return isCompletedOk;
        }
    }
}
