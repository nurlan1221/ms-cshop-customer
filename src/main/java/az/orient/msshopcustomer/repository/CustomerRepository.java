package az.orient.msshopcustomer.repository;

import az.orient.msshopcustomer.entity.CustomerEntity;
import az.orient.msshopcustomer.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository  extends JpaRepository<CustomerEntity,Long> {
    Optional<CustomerEntity> findByEmail(String email);
    List<CustomerEntity> findByStatus(Status status);
    Optional<CustomerEntity> findByIdAndStatus(Long id, Status status);
    Optional<CustomerEntity> findById(Long id);


}
