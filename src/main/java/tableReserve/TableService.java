package tableReserve;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }


    public void initTable(int tableCount){
        List<DiningTable> diningTableList = tableRepository.findAll();
        if (diningTableList.size()!=0){
            throw new IllegalStateException("already init table");
        }
        for (int count=0;count<tableCount;count++){
            DiningTable diningTable = new DiningTable();
            diningTableList.add(diningTable);

        }


        tableRepository.saveAll(diningTableList);
    }

    public ResponseEntity<String> reserveTable(Reservation reservation){
        int neededTable = (int) Math.ceil((double) reservation.getPeopleCount()/4);
        Pageable topFreeTable =  PageRequest.of(0,neededTable);
        List<DiningTable> diningTableList= tableRepository.findByReservedFalseOrderById(topFreeTable);
        if (neededTable> diningTableList.size() ){
            return ResponseEntity.status(400).body("not enough available table");
        }
        StringBuilder message= new StringBuilder("Table number: ");

        //set name, reserved and person count for all table
        for (DiningTable table: diningTableList){
            table.setReserved(true);
            table.setName(reservation.getName());
            table.setPersonCount(4);
            message.append(table.getId()).append(", ");


        }

        // set last table people count
        diningTableList.get(diningTableList.size()-1)
                .setPersonCount(reservation.getPeopleCount()%4);

        tableRepository.saveAll(diningTableList);
        return ResponseEntity.ok(message.toString());
    }

    public ResponseEntity<String> cancelReservationOneTable(DiningTable diningTable){
        Optional<DiningTable> reservedTable = tableRepository.findById(diningTable.getId());
        if(reservedTable.isEmpty()){
            return ResponseEntity.status(400).body("can not find table");
        }
        reservedTable.get().setReserved(false);
        reservedTable.get().setPersonCount(0);
        reservedTable.get().setName("");
        tableRepository.save(reservedTable.get());
        return ResponseEntity.ok("cancel table number "+diningTable.getId()+" reservation");
    }

}
