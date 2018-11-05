package game.ui.view;

import game.model.IModelObservable;

/**
 * Interface for Observer
 */
public interface IPanelObserver {

    // Observer suffix due to Java has builtin notify method, so to have unified unique name.

    /**
     * Update method
     * @param iModelObservable
     */
    public void updateObserver(IModelObservable iModelObservable);
}
