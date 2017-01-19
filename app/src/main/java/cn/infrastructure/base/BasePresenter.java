package cn.infrastructure.base;

/**
 * Presenter层基类
 *
 * @author Frank 2016-7-21
 */
public interface BasePresenter<T> {
    void setView(T view);
    void cancelAllRequest();
}
