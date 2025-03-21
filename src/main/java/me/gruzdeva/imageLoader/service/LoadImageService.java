package me.gruzdeva.imageLoader.service;

import me.gruzdeva.Except4SupportDocumented;
import org.aspectj.weaver.ast.Call;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class LoadImageService {

    public void downloadImages(String directory, List<URL> imgUrls)
            throws Except4SupportDocumented{
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Future<Void>> futures = new ArrayList<>();
        for (URL imgUrl : imgUrls) {
            File destinationFile = prepareFile(imgUrl, directory);
            Future<Void> future = executorService.submit(new DownloadImageTask(imgUrl, destinationFile));
            futures.add(future);
        }

        try {
            for (Future<Void> future : futures) {
                future.get();
            }

            executorService.shutdown();
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (ExecutionException ex) {
            throw new Except4SupportDocumented("ErrFileDownload01",
                    "Can't download file",
                    "Error during file download: " + ex.getMessage());
        } catch (InterruptedException ignored) {}
    }

    private File prepareFile(URL imgUrl, String directory) {
        String[] urlParts = imgUrl.toString().split("/");
        StringBuilder destinationFilePath = new StringBuilder(directory);
        destinationFilePath.append(File.separator).append(urlParts[urlParts.length - 1]);
        File destinationFile = new File(destinationFilePath.toString());
        try {
            if (!destinationFile.exists() && !destinationFile.createNewFile()) {
                throw new Except4SupportDocumented("ErrFileCreation01",
                        "Can't create file ",
                        "File doesn't exist and can't be created: " + destinationFile.getAbsolutePath());
            }
        } catch (IOException ignored) {}
        return destinationFile;
    }

    private synchronized void downloadImage(URL imgUrl, File destinationFile) throws IOException {
        try (InputStream in = imgUrl.openStream();
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    private class DownloadImageTask implements Callable<Void> {
        private final URL imgUrl;
        private final File destinationFile;

        public DownloadImageTask(URL imgUrl, File destinationFile) {
            this.imgUrl = imgUrl;
            this.destinationFile = destinationFile;
        }

        @Override
        public Void call() throws IOException {
            downloadImage(imgUrl, destinationFile);
            return null;
        }
    }
}
