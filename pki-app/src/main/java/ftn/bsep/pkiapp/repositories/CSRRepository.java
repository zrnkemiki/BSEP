package ftn.bsep.pkiapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ftn.bsep.pkiapp.model.Csr;
import ftn.bsep.pkiapp.model.User;

public interface CSRRepository extends JpaRepository<Csr, Long> {

}
