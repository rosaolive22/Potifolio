package TREVO.api.repository;

import TREVO.api.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Page<Company> findAllByAtivoTrue(Pageable paginacao);
    Company getReferenceById(Long id);

}
