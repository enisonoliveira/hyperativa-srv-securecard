package com.hyperativa.payment.securecard.port.queue;

import org.springframework.web.multipart.MultipartFile;

public interface FileProcessingPort {
    void queueFileProcessing(MultipartFile file);
}