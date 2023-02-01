package tableReserve;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


    public void initTable(int tableCount){
        List<diningTable> diningTableList = tableRepository.findAll();
        if (diningTableList.size()!=0){
            throw new IllegalStateException("already init table");
        }
        for (int count=0;count<tableCount;count++){
            diningTable diningTable = new diningTable();
            diningTableList.add(diningTable);

        }


        tableRepository.saveAll(diningTableList);
    }
}
