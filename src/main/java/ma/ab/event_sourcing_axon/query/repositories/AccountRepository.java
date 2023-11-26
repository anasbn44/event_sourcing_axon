package ma.ab.event_sourcing_axon.query.repositories;

import ma.ab.event_sourcing_axon.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
