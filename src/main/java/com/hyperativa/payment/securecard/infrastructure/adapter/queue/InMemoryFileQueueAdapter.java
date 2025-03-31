package com.hyperativa.payment.securecard.infrastructure.adapter.queue;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hyperativa.payment.securecard.port.queue.AsyncFileProcessorPort;
import com.hyperativa.payment.securecard.port.queue.FileProcessingPort;

@Component
public class InMemoryFileQueueAdapter implements FileProcessingPort {

    private static final Logger logger = LogManager.getLogger(InMemoryFileQueueAdapter.class);
    private final BlockingQueue<MultipartFile> fileQueue = new LinkedBlockingQueue<>();
    private final AsyncFileProcessorPort asyncFileProcessor;

    public InMemoryFileQueueAdapter( AsyncFileProcessorPort asyncFileProcessor) {
        this.asyncFileProcessor = asyncFileProcessor;
    }

    @Override
    public void queueFileProcessing(MultipartFile file) {
         if (file.isEmpty()) {
            logger.error("There aren't Card items to import in this file : {}", file);
            throw new RuntimeException(""); 
        }else{
            fileQueue.add(file);
            logger.info("File added to processing queue: {}", file.getOriginalFilename());
            asyncFileProcessor.processQueue(fileQueue); 
        }
    }
}