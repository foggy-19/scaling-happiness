package com.panonit.evently.services.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.panonit.evently.domain.QrCodeStatus;
import com.panonit.evently.domain.entities.QrCode;
import com.panonit.evently.domain.entities.Ticket;
import com.panonit.evently.exceptions.QrCodeGenerationException;
import com.panonit.evently.exceptions.QrCodeNotFoundException;
import com.panonit.evently.repositories.QrCodeRepository;
import com.panonit.evently.services.QrCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeServiceImpl implements QrCodeService {

    private static final int QR_HEIGHT = 300;
    private static final int QR_WIDTH = 300;

    private final QRCodeWriter qrCodeWriter;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public void generateQrCode(Ticket ticket) {
        log.info("Generating QR code for ticket {}", ticket);

        try {
            UUID uuid = UUID.randomUUID();
            String qrCodeImage = generateQrCodeImage(uuid);

            QrCode qrCode = new QrCode();
            qrCode.setId(uuid);
            qrCode.setValue(qrCodeImage);
            qrCode.setStatus(QrCodeStatus.ACTIVE);
            qrCode.setTicket(ticket);

            qrCodeRepository.saveAndFlush(qrCode);
        } catch (WriterException e) {
            throw new QrCodeGenerationException("Unable to generate QR code", e);
        } catch (IOException e) {
            throw new QrCodeGenerationException("Unable to generate QR code image", e);
        }
    }

    @Override
    public byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId) {
        log.info("Getting QR code for ticket {}", ticketId);

        QrCode qrCode = qrCodeRepository.findByTicketIdAndTicketPurchaserId(ticketId, userId)
                .orElseThrow(() -> new QrCodeNotFoundException(String.format("QR code with id %s not found", ticketId)));

        try {
            return Base64.getDecoder().decode(qrCode.getValue());
        } catch (IllegalArgumentException e) {
            log.error("Error decoding QR code for ticket {}", ticketId, e);

            throw new QrCodeNotFoundException("Invalid QR code format");
        }
    }

    private String generateQrCodeImage(UUID uuid) throws WriterException, IOException {
        BitMatrix matrix = qrCodeWriter.encode(uuid.toString(), BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
        BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "PNG", baos);
            byte[] bytes = baos.toByteArray();

            return Base64.getEncoder().encodeToString(bytes);
        }
    }
}
