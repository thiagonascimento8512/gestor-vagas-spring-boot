package com.thiagonascimento.gestordevagas.modules.candidate.repositories;

import com.thiagonascimento.gestordevagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
