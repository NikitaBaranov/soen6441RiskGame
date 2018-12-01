package game.ui.view;

import game.model.IObservable;

/**
 * Interface for Observer
 */
public interface IPanelObserver {

    // Observer suffix due to Java has builtin notify method, so to have unified unique name.

    /**
     * Update method
     * @param iObservable
     */
    public void updateObserver(IObservable iObservable);
}
