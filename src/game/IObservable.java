package game;

import game.ui.view.IPanelObserver;

/**
 * Observable interface
 */
public interface IObservable {
    /**
     * Attach observer
     * @param iPanelObserver
     */
    public void attachObserver(IPanelObserver iPanelObserver);

    /**
     * Detach observer
     * @param iPanelObserver
     */
    public void detachObserver(IPanelObserver iPanelObserver);

    // Observer suffix due to Java has builtin notify method, so to have unified unique name.

    /**
     * Notify method for observers
     */
    public void notifyObservers();
}
