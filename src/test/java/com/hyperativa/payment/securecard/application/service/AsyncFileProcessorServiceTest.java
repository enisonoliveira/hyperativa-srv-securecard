package com.hyperativa.payment.securecard.application.service;

import com.hyperativa.payment.securecard.port.services.CardPortServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.mockito.Mockito.*;

class AsyncFileProcessorServiceTest {

    private AsyncFileProcessorService asyncFileProcessorService;
    private CardService cardService;

    @BeforeEach
    void setUp() {
        cardService = mock(CardService.class);
        asyncFileProcessorService = new AsyncFileProcessorService(cardService);
    }

    @Test
    void shouldProcessFileFromQueueSuccessfully() throws IOException {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        when(file1.getOriginalFilename()).thenReturn("file1.txt");
        when(file2.getOriginalFilename()).thenReturn("file2.txt");

        BlockingQueue<MultipartFile> fileQueue = new ArrayBlockingQueue<>(2);
        fileQueue.add(file1);
        fileQueue.add(file2);

        asyncFileProcessorService.processQueue(fileQueue);

        verify(cardService, times(1)).processFile(file1);
        verify(cardService, times(1)).processFile(file2);
    }

    @Test
    void shouldHandleIOExceptionGracefully() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getOriginalFilename()).thenReturn("errorFile.txt");

        BlockingQueue<MultipartFile> fileQueue = new ArrayBlockingQueue<>(1);
        fileQueue.add(file);

        doThrow(new IOException("Test Exception")).when(cardService).processFile(file);

        asyncFileProcessorService.processQueue(fileQueue);

        verify(cardService, times(1)).processFile(file);
    }
}
