package pl.recompiled.demo.order;

public interface Publisher<T> {

    void subscribe(Subscriber<T> subscriber);

}
