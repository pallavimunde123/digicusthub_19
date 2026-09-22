package com.ibm.digicusthub.repository;

import com.ibm.digicusthub.entity.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity,Long> {

    public boolean existsByEmail(String email);

    public boolean existsByMobNo(String mobNo);

    public boolean existsByPanCardDetails(String panCard);

    public Optional<CustomerDetailsEntity> findByMobNo(String mobNo);

    public Optional<CustomerDetailsEntity> findByEmail(String email);

}