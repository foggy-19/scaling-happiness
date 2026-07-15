package com.panonit.evently.repositories;

import com.panonit.evently.domain.QrCodeStatus;
import com.panonit.evently.domain.entities.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, UUID> {

    Optional<QrCode> findByTicketIdAndTicketPurchaserId(UUID ticketId, UUID ticketPurchaserId);

    Optional<QrCode> findByIdAndStatus(UUID id, QrCodeStatus status);
}
