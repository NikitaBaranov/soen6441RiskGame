package game.ui.view;

import game.model.IModelObservable;

public interface IPanelObserver {

    // Observer suffix due to Java has builtin notify method, so to have unified unique name.
    public void updateObserver(IModelObservable iModelObservable);
}
