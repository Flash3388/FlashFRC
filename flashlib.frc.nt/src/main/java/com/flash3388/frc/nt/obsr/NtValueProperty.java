package com.flash3388.frc.nt.obsr;

import com.beans.observables.ObservableBooleanValue;
import com.beans.observables.ObservableDoubleValue;
import com.beans.observables.ObservableIntValue;
import com.beans.observables.ObservableLongValue;
import com.beans.observables.ObservableValue;
import com.beans.observables.RegisteredListener;
import com.beans.observables.converted.ConvertedObservable;
import com.beans.observables.converted.ToBooleanConvertedObservable;
import com.beans.observables.converted.ToDoubleConvertedObservable;
import com.beans.observables.converted.ToIntConvertedObservable;
import com.beans.observables.converted.ToLongConvertedObservable;
import com.beans.observables.listeners.ChangeEvent;
import com.beans.observables.listeners.ChangeListener;
import com.beans.observables.properties.ObservableProperty;
import com.beans.util.function.OneWayConverter;
import com.beans.util.function.ToBooleanConverter;
import com.beans.util.function.ToDoubleConverter;
import com.beans.util.function.ToIntConverter;
import com.beans.util.function.ToLongConverter;
import com.flash3388.flashlib.net.obsr.Value;
import com.flash3388.flashlib.net.obsr.ValueProperty;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableEvent;

import java.util.EnumSet;

public class NtValueProperty implements ValueProperty {

    private final NetworkTableEntry mEntry;

    public NtValueProperty(NetworkTableEntry entry) {
        mEntry = entry;
    }

    @Override
    public Object getBean() {
        return mEntry;
    }

    @Override
    public void set(Value value) {
        mEntry.setValue(Helper.obsrValueToNt(value));
    }

    @Override
    public Value get() {
        return Helper.ntValueToObsr(mEntry.getValue());
    }

    @Override
    public RegisteredListener addChangeListener(ChangeListener<? super Value> changeListener) {
        int handle = mEntry.getInstance().addListener(
                mEntry,
                EnumSet.of(NetworkTableEvent.Kind.kValueAll),
                (event)-> {
                    changeListener.onChange(new ChangeEvent<>(
                            this,
                            null,
                            Helper.ntValueToObsr(event.valueData.value)
                    ));
                });

        return ()-> {
            mEntry.getInstance().removeListener(handle);
        };
    }

    @Override
    public void bind(ObservableValue<Value> observableValue) {
        throw new UnsupportedOperationException("binding not supported");
    }

    @Override
    public void bindBidirectional(ObservableProperty<Value> observableProperty) {
        throw new UnsupportedOperationException("binding not supported");
    }

    @Override
    public void unbind() {
        throw new UnsupportedOperationException("binding not supported");
    }

    @Override
    public <T2> ObservableValue<T2> as(OneWayConverter<Value, T2> converter) {
        return new ConvertedObservable<>(this, converter);
    }

    @Override
    public ObservableBooleanValue asBoolean(ToBooleanConverter<Value> converter) {
        return new ToBooleanConvertedObservable<>(this, converter);
    }

    @Override
    public ObservableIntValue asInt(ToIntConverter<Value> converter) {
        return new ToIntConvertedObservable<>(this, converter);
    }

    @Override
    public ObservableLongValue asLong(ToLongConverter<Value> converter) {
        return new ToLongConvertedObservable<>(this, converter);
    }

    @Override
    public ObservableDoubleValue asDouble(ToDoubleConverter<Value> converter) {
        return new ToDoubleConvertedObservable<>(this, converter);
    }
}
