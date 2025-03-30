package com.hyperativa.payment.securecard.application.service;

import java.io.IOException;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.hyperativa.payment.securecard.port.queue.AsyncFileProcessorPort;
import com.hyperativa.payment.securecard.port.services.CardPortServices;

import java.util.concurrent.BlockingQueue;

@Service
public class AsyncFileProcessorService implements AsyncFileProcessorPort {

    private static final Logger logger = LogManager.getLogger(AsyncFileProcessorService.class);
    private final CardPortServices cardService;

    public AsyncFileProcessorService(CardService cardService) {
        this.cardService = cardService;
    }

    @Async
    @Override
    public void processQueue(BlockingQueue<MultipartFile> fileQueue) {
        while (!fileQueue.isEmpty()) {
            MultipartFile file = fileQueue.poll();
            if (file != null) {
                try {
                    logger.info("Starting processing for file: {}", file.getOriginalFilename());
                    cardService.processFile(file);
                    logger.info("Successfully processed file: {}", file.getOriginalFilename());
                } catch (IOException e) {
                    logger.error("Error processing file: {}", file.getOriginalFilename(), e);
                }
            }
        }
    }
}