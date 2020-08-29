package com.flash3388.frc.nt;

import com.flash3388.flashlib.scheduling.actions.ActionBase;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class ProcessVariableUpdateAction extends ActionBase {

    private final DoubleSupplier processVariableSupplier;
    private final DoubleConsumer processVariableConsumer;
    private double prevValue;

    public ProcessVariableUpdateAction(DoubleSupplier processVariableSupplier, DoubleConsumer processVariableConsumer) {//do I need to receive the scheduler from the user?
        this.processVariableSupplier = processVariableSupplier;
        this.processVariableConsumer = processVariableConsumer;
        prevValue = 0;
    }

    @Override
    public void execute() {
        double currentValue = processVariableSupplier.getAsDouble();

        if (currentValue != prevValue) {
            processVariableConsumer.accept(currentValue);
            prevValue = currentValue;
        }
    }
}
