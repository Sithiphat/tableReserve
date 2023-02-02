package tableReserve;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TableServiceTest {
    @Mock
    private TableRepository tableRepository;
    private TableService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TableService(tableRepository);
    }

    @Test
    void initTable() {
        // when
        underTest.initTable(3);
        //then
        verify(tableRepository).findAll();
    }

    @Test
    void willThrowWhenInitTableAgain() {

        //given(tableRepository.findAll()).willReturn(new ArrayList<>());
        List<DiningTable> diningTableList = new ArrayList<>() ;
        diningTableList.add(new DiningTable());

        when(tableRepository.findAll()).thenReturn(diningTableList);

        // when

        //then
        assertThrows(IllegalStateException.class,() -> underTest.initTable(3));
        verify(tableRepository).findAll();

    }

    @Test
    void reserveTable() {
    }
}