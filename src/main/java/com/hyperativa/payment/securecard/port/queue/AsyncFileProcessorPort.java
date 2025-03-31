package com.hyperativa.payment.securecard.port.queue;

import java.util.concurrent.BlockingQueue;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

public interface  AsyncFileProcessorPort {
    
    @Async
    public void processQueue(BlockingQueue<MultipartFile> fileQueue) ;
}
