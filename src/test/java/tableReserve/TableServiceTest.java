package tableReserve;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TableServiceTest {
    @Mock
    private TableRepository tableRepository;
    private TableService underTest;
    @Captor
    private ArgumentCaptor<List<DiningTable>> captor;

    @BeforeEach
    void setUp() {
        underTest = new TableService(tableRepository);
    }

    @Test
    void canInitTable() {
        //given
        int tableCount= 25;
        List<DiningTable> diningTableList = new ArrayList<>() ;
        when(tableRepository.findAll()).thenReturn(diningTableList);
        // when
        underTest.initTable(tableCount);
        //then
        verify(tableRepository).findAll();
        verify(tableRepository).saveAll(captor.capture());
        List<DiningTable> capturedDiningTableList = captor.getValue();
        assertThat(capturedDiningTableList.size()).isEqualTo(tableCount);
    }

    @Test
    void willThrowWhenInitTableAgain() {
        //given
        List<DiningTable> diningTableList = new ArrayList<>() ;
        diningTableList.add(new DiningTable());
        when(tableRepository.findAll()).thenReturn(diningTableList);
        // when
        //then
        assertThrows(IllegalStateException.class,() -> underTest.initTable(3));
        verify(tableRepository).findAll();
        verify(tableRepository,times(0)).saveAll(diningTableList);
    }

    @Test
    void shouldReserveTable() {
        //given
        int peopleCount = 15;
        String name = "Jame";
        int tableCount =  (int) Math.ceil((double) peopleCount/4);
        Reservation reservation= new Reservation(peopleCount,name);
        List<DiningTable> diningTableList = new ArrayList<>() ;
        for (int count=0;count<tableCount;count++){
            DiningTable diningTable = new DiningTable();
            diningTableList.add(diningTable);
        }
        Pageable topFreeTable =  PageRequest.of(0,tableCount);
        when(tableRepository.findByReservedFalseOrderById(topFreeTable)).thenReturn(diningTableList);
        //when
        ResponseEntity<String> response= underTest.reserveTable(reservation);
        //then
        verify(tableRepository).saveAll(captor.capture());
        List<DiningTable> capturedList = captor.getValue();
        assertThat(capturedList.size()).isEqualTo( tableCount);
        assertThat(capturedList.get(0).getName()).isEqualTo(name);
        assertThat(response.getStatusCode().toString()).isEqualTo("200 OK");
    }

    @Test
    void willNotReserveIfNotEnoughTable() {
        //given
        int peopleCount = 15;
        String name = "Jame";
        int tableCount =  (int) Math.ceil((double) peopleCount/4);
        Reservation reservation= new Reservation(peopleCount,name);
        List<DiningTable> diningTableList = new ArrayList<>() ;
        for (int count=0;count<tableCount-1;count++){
            DiningTable diningTable = new DiningTable();
            diningTableList.add(diningTable);
        }
        Pageable topFreeTable =  PageRequest.of(0,tableCount);
        when(tableRepository.findByReservedFalseOrderById(topFreeTable)).thenReturn(diningTableList);
        //when
        ResponseEntity<String> response= underTest.reserveTable(reservation);
        //then
        verify(tableRepository,times(0)).saveAll(captor.capture());
        assertThat(response.getStatusCode().toString()).isEqualTo("400 BAD_REQUEST");
        assertThat(response.getBody()).isEqualTo("not enough available table");
    }
}