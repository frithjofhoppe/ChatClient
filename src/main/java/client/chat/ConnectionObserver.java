package client.chat;

import client.view.StartupView;
import client.viewModel.StartupViewModel;

import java.util.Observable;
import java.util.Observer;

public class ConnectionObserver implements Observer {

    private StartupViewModel model;

    public ConnectionObserver(StartupViewModel model){
        this.model = model;
    }

    @Override
    public void update(Observable o, Object arg) {
        model.setConnectionState((boolean)arg);
    }
}
