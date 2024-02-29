package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import org.example.clases.DbHelper;
import org.example.clases.Factura;

public class PrimaryController {
    DbHelper db;
    private static int[] estaOcupado = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static int numMesa;
    @FXML
    Button mesa1;
    @FXML
    Button mesa2;
    @FXML
    Button mesa3;
    @FXML
    Button mesa4;
    @FXML
    Button mesa5;
    @FXML
    Button mesa6;
    @FXML
    Button mesa7;
    @FXML
    Button mesa8;
    @FXML
    Button mesa9;
    @FXML
    Button mesa10;
    @FXML
    ImageView mesa1Img;
    @FXML
    ImageView mesa2Img;
    @FXML
    ImageView mesa3Img;
    @FXML
    ImageView mesa4Img;
    @FXML
    ImageView mesa5Img;
    @FXML
    ImageView mesa6Img;
    @FXML
    ImageView mesa7Img;
    @FXML
    ImageView mesa8Img;
    @FXML
    ImageView mesa9Img;
    @FXML
    ImageView mesa10Img;

    @FXML
    private void initialize() {
        try {
            db = new DbHelper();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        numMesa = 0;
        for (int i = 0; i < estaOcupado.length; i++) {
            if (estaOcupado[i] == 1) {
                cambiarEstado(i+1);
            }
        }
    }

    @FXML
    private void mesa() throws IOException {
        App.setRoot("secondary");
    }


    public static int getNumMesa() {
        return numMesa;
    }


    @FXML
    public void printButton(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();
        if (sourceButton.equals(mesa1)) {
            numMesa = 1;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa2)) {
            numMesa = 2;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa3)) {
            numMesa = 3;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa4)) {
            numMesa = 4;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa5)) {
            numMesa = 5;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa6)) {
            numMesa = 6;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa7)) {
            numMesa = 7;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa8)) {
            numMesa = 8;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa9)) {
            numMesa = 9;
            mesa();
            estaOcupado[numMesa-1] = 1;
        } else if (sourceButton.equals(mesa10)) {
            numMesa = 10;
            mesa();
            estaOcupado[numMesa-1] = 1;
        }
    }


    public void cambiarEstado(int numMesa) {
        switch (numMesa) {
            case 1:
                mesa1Img.setVisible(true);
                break;
            case 2:
                mesa2Img.setVisible(true);
                break;
            case 3:
                mesa3Img.setVisible(true);
                break;
            case 4:
                mesa4Img.setVisible(true);
                break;
            case 5:
                mesa5Img.setVisible(true);
                break;
            case 6:
                mesa6Img.setVisible(true);
                break;
            case 7:
                mesa7Img.setVisible(true);
                break;
            case 8:
                mesa8Img.setVisible(true);
                break;
            case 9:
                mesa9Img.setVisible(true);
                break;
            case 10:
                mesa10Img.setVisible(true);
                break;
        }
    }
    public static void despacharMesa(int numMesa){
        estaOcupado[numMesa-1] = 0;
    }


    public void imprimirHistorial(ActionEvent actionEvent) {
        InputStream reportFile = getClass().getResourceAsStream("/Coffee_Landscape.jrxml");
        try {
            DbHelper dbHelper = new DbHelper();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dbHelper.getC());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
