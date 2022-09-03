package com.bank.bank.repositories;

import com.bank.bank.models.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCheckingRepository extends JpaRepository<StudentChecking, String> {
}
