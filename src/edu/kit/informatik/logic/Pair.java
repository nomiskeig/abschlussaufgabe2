package edu.kit.informatik.logic;

import java.util.Objects;

/**
 * This class models a generic pair. Contains methods to set the first and second element, as well as getting either of
 * them.
 *
 * @param <F> the type of the first element.
 * @param <S> the type of the second element.
 * @author Simon Giek
 * @version 1.0
 */
public class Pair<F, S> {
    /**
     * The first element
     */
    private F first;

    /**
     * The second element
     */
    private S second;

    /**
     * The constructor.
     *
     * @param first  the first element.
     * @param second the second element.
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Setter for the first element.
     *
     * @param first the element to set the first element to.
     */
    public void setFirst(F first) {
        this.first = first;
    }

    /**
     * Setter for the second element.
     *
     * @param second the element to set the second element to.
     */
    public void setSecond(S second) {
        this.second = second;
    }

    /**
     * Getter for the first element.
     *
     * @return the first element of the pair.
     */
    public F getFirst() {
        return first;
    }

    /**
     * Getter for the second element of the pair.
     *
     * @return the second element of the pair.
     */
    public S getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
