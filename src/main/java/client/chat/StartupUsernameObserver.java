package client.chat;

import client.viewModel.StartupViewModel;

import java.util.Observable;
import java.util.Observer;

public class StartupUsernameObserver implements Observer {

    private StartupViewModel viewModel;

    public StartupUsernameObserver(StartupViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        viewModel.setUsernameState((boolean)arg);
    }
}
