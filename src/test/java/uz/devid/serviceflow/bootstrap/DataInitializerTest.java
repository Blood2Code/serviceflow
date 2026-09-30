package uz.devid.serviceflow.bootstrap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.devid.serviceflow.repository.RequestRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DataInitializerTest {

    @Mock
    private RequestRepository requestRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    public void run_ShouldInitializeData_WhenRepositoryIsEmpty() throws Exception {
        when(requestRepository.count()).thenReturn(0L);

        dataInitializer.run();

        verify(requestRepository, times(1)).saveAll(anyList());
    }

    @Test
    public void run_ShouldNotInitializeData_WhenRepositoryIsNotEmpty() throws Exception {
        when(requestRepository.count()).thenReturn(5L);

        dataInitializer.run();

        verify(requestRepository, never()).saveAll(anyList());
    }
}
