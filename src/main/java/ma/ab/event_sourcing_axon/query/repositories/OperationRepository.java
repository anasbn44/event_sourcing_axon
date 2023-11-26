package ma.ab.event_sourcing_axon.query.repositories;

import ma.ab.event_sourcing_axon.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
