package command;

import controller.RetornoValidaMsg;
import view.View;

import java.security.NoSuchAlgorithmException;

public abstract class Command {
    public View view;

    Command(View view){
        this.view = view;
    }

    public abstract RetornoValidaMsg execute() throws NoSuchAlgorithmException;
}
