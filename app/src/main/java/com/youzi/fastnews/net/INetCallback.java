package com.youzi.fastnews.net;

/**
 * Created by fish on 16-12-23.
 */

public interface INetCallback<T> {
    void Success(T t);
    void Failed(String msg);
}
