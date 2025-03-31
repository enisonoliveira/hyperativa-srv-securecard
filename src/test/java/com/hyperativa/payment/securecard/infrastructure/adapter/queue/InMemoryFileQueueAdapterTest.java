package com.hyperativa.payment.securecard.infrastructure.adapter.queue;

import com.hyperativa.payment.securecard.port.queue.AsyncFileProcessorPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryFileQueueAdapterTest {

    @Mock
    private AsyncFileProcessorPort asyncFileProcessor;

    @Mock
    private MultipartFile mockFile;

    @InjectMocks
    private InMemoryFileQueueAdapter inMemoryFileQueueAdapter;

    private BlockingQueue<MultipartFile> fileQueue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fileQueue = new LinkedBlockingQueue<>();
    }

    @Test
    void shouldProcessFileSuccessfully() {
        when(mockFile.isEmpty()).thenReturn(false);
        when(mockFile.getOriginalFilename()).thenReturn("valid-file.txt");

        assertDoesNotThrow(() -> inMemoryFileQueueAdapter.queueFileProcessing(mockFile));

        // Verify the async processor is called
        verify(asyncFileProcessor, times(1)).processQueue(any(BlockingQueue.class));
    }

    @Test
    void shouldThrowExceptionWhenFileIsEmpty() {
        when(mockFile.isEmpty()).thenReturn(true);

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> inMemoryFileQueueAdapter.queueFileProcessing(mockFile));

        assertNotNull(thrown);

        // Verify that async processor is NOT invoked
        verifyNoInteractions(asyncFileProcessor);
    }
}