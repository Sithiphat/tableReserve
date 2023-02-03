package tableReserve;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("{tableCount}")
    public void initTable(@PathVariable("tableCount") Integer tableCount){
        tableService.initTable(tableCount);
    }

    @PutMapping("reservation")
    public ResponseEntity<String> reserveTable(@RequestBody Reservation reservation){
        return tableService.reserveTable(reservation);

    }

    @PutMapping("cancellation")
    public ResponseEntity<String> cancelOneTable(@RequestBody DiningTable diningTable){
        return tableService.cancelReservationOneTable(diningTable);
    }

    @PutMapping("cancellation/list")
    public ResponseEntity<String> cancelMultiTable(@RequestBody List<DiningTable> diningTableList){
        return tableService.cancelReservationMultiTable(diningTableList);
    }

}
