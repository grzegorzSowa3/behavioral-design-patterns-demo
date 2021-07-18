package pl.recompiled.demo.order;

public interface Subscriber<T> {

    void onNext(T item);

}
