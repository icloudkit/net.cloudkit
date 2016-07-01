package net.cloudkit.experiment.infrastructure.support;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity<T> implements Entity<T>, Serializable {

    private static final long serialVersionUID = 1L;

}
