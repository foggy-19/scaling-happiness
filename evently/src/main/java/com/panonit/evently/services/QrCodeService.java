package com.panonit.evently.services;

import com.panonit.evently.domain.entities.QrCode;
import com.panonit.evently.domain.entities.Ticket;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);
}
