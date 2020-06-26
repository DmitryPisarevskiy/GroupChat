package GroupChat;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public TextArea lConversation;
    @FXML
    public TextField tfMessage;
    @FXML
    public Button btnSend;

    private Stage stageAbout;

    public void sendMessage(ActionEvent actionEvent) {
        if (!tfMessage.getText().equals("")) {
            lConversation.setText(lConversation.getText() + "ME: " + tfMessage.getText()+"\n");
            tfMessage.clear();
            tfMessage.requestFocus();
        }
    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) tfMessage.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                btnSend.requestFocus();
            }
        });
    }

    public void openAbout() {
        if (stageAbout==null) {
            stageAbout=newAbout();
        }
        stageAbout.show();
    }

    private Stage newAbout(){
        Stage stage=null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("About.fxml"));
            Parent root =fxmlLoader.load();
            stage=new Stage();
            stage.setTitle("About");
            stage.setScene(new Scene(root, 300, 50));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }
}
