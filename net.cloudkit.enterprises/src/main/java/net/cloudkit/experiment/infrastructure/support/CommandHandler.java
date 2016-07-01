package net.cloudkit.experiment.infrastructure.support;

public interface CommandHandler<C, R> extends Handler<C> {

    public R handle(C command);
}
