package command;

import controller.Retorno;
import view.View;

import java.security.NoSuchAlgorithmException;

public abstract class Command {
    public View view;

    Command(View view){
        this.view = view;
    }

    public abstract Retorno execute() throws NoSuchAlgorithmException;
}
