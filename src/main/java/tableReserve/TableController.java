package tableReserve;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
