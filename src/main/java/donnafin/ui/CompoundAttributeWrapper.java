package donnafin.ui;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class CompoundAttributeWrapper<T> {

    abstract List<String> getColumns();
    abstract BiFunction<Double, T, Double> getAggregateFunction();

    static <S> CompoundAttributeWrapper<S> of(
            S attribute, Function<S, Double> toValue, List<String> headings) {
        BiFunction<Double, S, Double> aggregator = (partialResult, next) -> partialResult + toValue.apply(next);
        return new CompoundAttributeWrapper<>() {
            @Override
            public List<String> getColumns() {
                return headings;
            }

            @Override
            public BiFunction<Double, S, Double> getAggregateFunction() {
                return aggregator;
            }
        };
    }
}

