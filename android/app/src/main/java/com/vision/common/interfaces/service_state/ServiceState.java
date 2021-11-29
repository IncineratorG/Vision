package com.vision.common.interfaces.service_state;

import com.vision.common.interfaces.stringifiable.Stringifiable;

public interface ServiceState extends Stringifiable {
    String stateId();
    ServiceState copy();
}
