package com.panonit.potato.services.impl;

import com.panonit.potato.services.BluePrinter;
import com.panonit.potato.services.ColourPrinter;
import com.panonit.potato.services.GreenPrinter;
import com.panonit.potato.services.RedPrinter;
import org.springframework.stereotype.Component;

@Component
public class ColourPrinterImpl implements ColourPrinter {

    private final RedPrinter redPrinter;
    private final GreenPrinter greenPrinter;
    private final BluePrinter bluePrinter;

    public ColourPrinterImpl(RedPrinter redPrinter, GreenPrinter greenPrinter, BluePrinter bluePrinter) {
        this.redPrinter = redPrinter;
        this.greenPrinter = greenPrinter;
        this.bluePrinter = bluePrinter;
    }

    @Override
    public String print() {
        return String.join(", ", redPrinter.print(), greenPrinter.print(), bluePrinter.print());
    }
}
