package sample;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.beans.binding.When;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {
    //FXML annotation asocia las variables en la clase del controlador al objeto descrito en el archivo FXML
    @FXML
    private StackPane stackPane;
    @FXML
    private Text text2;
    private RotateTransition rotate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize crea y configura el RotateTransition y lo aplíca al stackPane
        rotate = new RotateTransition(Duration.millis(2500), stackPane);
        rotate.setToAngle(360);
        rotate.setFromAngle(0);
        rotate.setInterpolator(Interpolator.LINEAR);

        //Adiciono un change listener a la propiedad del estado de la transición
        rotate.statusProperty().addListener((ObservableValue, oldValue, newValue) -> {
            text2.setText("Was "+ oldValue + ", Now "+ newValue);
        });

        //Por último enlaza la expresion de text2 stroke property especificando
        // su color en función del estado de la transion de la rotación
        text2.strokeProperty().bind(new When(rotate.statusProperty()
                .isEqualTo(Animation.Status.RUNNING))
                .then(Color.GREEN).otherwise(Color.RED));

        text2.getStyleClass().add("mytext");
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        if (rotate.getStatus().equals(Animation.Status.RUNNING)) {
            rotate.pause();
        } else {
            rotate.play();
        }
    }
}
