package tableReserve;


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
}
