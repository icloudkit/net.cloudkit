package net.cloudkit.experiment.infrastructure.support;

public interface Handler<T> {

    Object handle(CommandMessage<T> commandMessage, UnitOfWork unitOfWork);
}
