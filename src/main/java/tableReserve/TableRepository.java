package tableReserve;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<DiningTable,Integer> {
    List<DiningTable> findByReservedFalseOrderById(Pageable pageable);
}
