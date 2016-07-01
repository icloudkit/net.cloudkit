package net.cloudkit.experiment.infrastructure.support;

import java.io.Serializable;
import java.util.Map;

public interface Message<T> extends Serializable {

    String getIdentifier();

    MetaData getMetaData();

    T getPayload();

    Class getPayloadType();

    Message<T> withMetaData(Map<String, ?> var1);

    Message<T> andMetaData(Map<String, ?> var1);
}
