package entertain_me.app.service;

import java.util.function.Consumer;

public class UtilsService {

    public static <T> Consumer<T> handleCheckedException(CheckedConsumer<T> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @FunctionalInterface
    public interface CheckedConsumer<T> {
        void accept(T t) throws Exception;
    }
}
