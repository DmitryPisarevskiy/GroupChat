package Calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Controller {
    @FXML
    public TextField tfEnter;
    @FXML
    public Button btn1;
    @FXML
    public Button btn2;
    @FXML
    public Button btn3;
    @FXML
    public Button btn4;
    @FXML
    public Button btn5;
    @FXML
    public Button btn6;
    @FXML
    public Button btn7;
    @FXML
    public Button btn8;
    @FXML
    public Button btn9;
    @FXML
    public Button btn0;
    @FXML
    public Button btnLeftBracket;
    @FXML
    public Button btnRightBracket;
    @FXML
    public Button btnClear;
    @FXML
    public Button btnEquals;
    @FXML
    public TextField tfAnswer;
    @FXML
    public Button btnDot;
    @FXML
    public Button btnMultiply;
    @FXML
    public Button btnDivide;
    @FXML
    public Button btnPlus;
    @FXML
    public Button btnMinus;
    @FXML
    public Button btnDelete;
    private Object IllegalArgumentException;
    private Stage stageMistake;

    public void btnNumberClick(ActionEvent actionEvent) {
        Button button=(Button) actionEvent.getSource();
        tfEnter.setText(tfEnter.getText() + button.getText());
        btnEquals.requestFocus();
    }

    public void btnClearClick(ActionEvent actionEvent) {
        tfEnter.clear();
    }

    public void btnEqualsClick(ActionEvent actionEvent) {
        if (!tfEnter.getText().equals("")) {
            try {
                tfAnswer.setText(String.valueOf(result(tfEnter.getText())));
            } catch (IllegalArgumentException | StringIndexOutOfBoundsException | NullPointerException e) {
                e.printStackTrace();
                System.out.println("Неверный аргумент");
                openMistakeWindow();
            }
        }
    }

    private float result(String text) throws IllegalArgumentException {
        if (text.charAt(0)=='(' && text.charAt(text.length()-1)==')' && checkedBrackets(text.substring(1,text.length()-1))) {
            return (result(text.substring(1,text.length()-1)));
        }
        if (text.charAt(0)=='+' || text.charAt(0)=='-') {
            return (result("0"+text));
        }
        if (text.charAt(text.length()-1)=='+' || text.charAt(text.length()-1)=='-') {
            return (result(text+"0"));
        }
        if (isFloat(text)) {
            return Float.parseFloat(text);
        }
        if (splitBySign(text,'+').length==2) {
            return (result(splitBySign(text,'+')[0]) + result(splitBySign(text,'+')[1]));
        }
        if (splitBySign(text,'-').length==2) {
            return (result(splitBySign(text,'-')[0]) - result(splitBySign(text,'-')[1]));
        }
        if (splitBySign(text,'*').length==2) {
            return (result(splitBySign(text,'*')[0]) * result(splitBySign(text,'*')[1]));
        }
        if (splitBySign(text,'/').length==2) {
            return (result(splitBySign(text,'/')[0]) / result(splitBySign(text,'/')[1]));
        }
        throw new IllegalArgumentException();
    }

    public static boolean isFloat(String value) {
        return value.matches("\\d+(\\.\\d+)?");
    }

    public void btnDelete(ActionEvent actionEvent) {
        if (tfEnter.getText().length()>0) {
            tfEnter.setText(tfEnter.getText().substring(0, tfEnter.getText().length()-1));
        }
    }

    public static String[] splitBySign(String text, char sign) {
        int i=0;
        int bracketsCounter=0;
        while (i<text.length()) {
            if (text.charAt(i)=='(') {
                bracketsCounter=1;
                while (bracketsCounter!=0) {
                    i++;
                    if (text.charAt(i)=='(') {
                        bracketsCounter+=1;
                    }
                    if (text.charAt(i)==')') {
                        bracketsCounter-=1;
                    }
                }
            }
            if (text.charAt(i)==sign) {
                return new String[]{text.substring(0,i),text.substring(i+1,text.length())};
            }
            i++;
        }
        return new String[]{text};
    }

    public static boolean checkedBrackets(String text) {
        int bracketsCounter=0;
        int i=0;
        while (i<text.length()) {
            if (text.charAt(i)=='(') {
                bracketsCounter+=1;
            }
            if (text.charAt(i)==')') {
                bracketsCounter-=1;
            }
            if (bracketsCounter<0) {
                return false;
            }
            i++;
        }
        return true;
    }

    public void openMistakeWindow() {
        if (stageMistake==null) {
            stageMistake=newMistake();
        }
        stageMistake.show();
    }

    private Stage newMistake(){
        Stage stage=null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MistakeWindow.fxml"));
            Parent root =fxmlLoader.load();
            stage=new Stage();
            stage.setTitle("MISTAKE!!!");
            stage.setScene(new Scene(root, 300, 50));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }
}
