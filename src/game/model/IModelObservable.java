package game.model;

import game.ui.view.IPanelObserver;

public interface IModelObservable {

    public void attachObserver(IPanelObserver iPanelObserver);

    public void detachObserver(IPanelObserver iPanelObserver);

    // Observer suffix due to Java has builtin notify method, so to have unified unique name.
    public void notifyObservers();
}
