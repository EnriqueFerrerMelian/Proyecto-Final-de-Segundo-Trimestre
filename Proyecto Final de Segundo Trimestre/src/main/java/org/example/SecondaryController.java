package org.example;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.*;
import org.example.clases.DbHelper;
import org.example.clases.Factura;
import org.example.clases.Producto;

public class SecondaryController {
    PrimaryController primaryController;
    @FXML
    ListView<Producto> listaPedido;
    String[] imagenes = {"mesas/mesa2ocupada.png", "mesas/mesa2ocupada.png", "mesas/mesa3ocupada.png", "mesas/mesa4ocupada.png"};
    private static List<Factura> listaDeFacturas = new ArrayList<>();
    private DbHelper db;
    private Factura factura;
    private int numMesa = PrimaryController.getNumMesa();
    @FXML
    private Button cocamola;
    @FXML
    private Button fantastica;
    @FXML
    private Button spirite;
    @FXML
    private Button pepsu;
    @FXML
    private Button drdr;
    @FXML
    private Button bluebull;
    @FXML
    private Label labelTotal;
    private double total;

    @FXML
    private void initialize() throws IOException {
        primaryController = new PrimaryController();
        //activa la base de datos
        try {
            db = new DbHelper();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //seta el total a 0
        total = 0.0;

        //compruebo si la mesa seleccionada ya tiene una cuenta abierta:
        //(si una factura está en la listaDeFacturas se considera abierta).
        if (!listaDeFacturas.isEmpty()) {
            boolean esta = false;
            //se busca en la lista de facturas y se carga la que corresponde a la mesa
            for (int i = 0; i < listaDeFacturas.size(); i++) {
                if (listaDeFacturas.get(i).getMesa() == numMesa) {
                    esta = true;
                    factura = listaDeFacturas.get(i);
                }
            }
            //si la factura no se encuentra en la lista se crea una nueva
            if (!esta) {
                factura = new Factura(numMesa + "-" + LocalDateTime.now(), numMesa, true);
                db.addFactura(factura);
                listaDeFacturas.add(factura);
            }
        } else {//se crea una nueva factura si la lista esta vacía
            factura = new Factura(numMesa + "-" + LocalDateTime.now(), numMesa, true);
            db.addFactura(factura);//se crea la factura en la bd
            listaDeFacturas.add(factura);
        }
        listaPedido.setItems(factura.getPedidosData());//se añade el contenido de la factura en el listView
        listaPedido.setCellFactory(
                new Callback<ListView<Producto>, ListCell<Producto>>() {
                    @Override
                    public ListCell<Producto> call(ListView<Producto> param) {
                        return new ListCell<Producto>() {
                            @Override
                            protected void updateItem(Producto item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item.getNombre() + "      x" + item.getCantidad() +
                                            "-" + item.getPrecio() + "€");
                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });
    }
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void addBebida(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();
        if (sourceButton.equals(cocamola)) {
            addUpdatePedido(2.00, 1, "Cocamola");
        } else if (sourceButton.equals(fantastica)) {
            addUpdatePedido(1.65, 2, "Fantastica");
        } else if (sourceButton.equals(spirite)) {
            addUpdatePedido(1.95, 3, "Spirite");
        } else if (sourceButton.equals(bluebull)) {
            addUpdatePedido(2.40, 4, "BlueBull");
        } else if (sourceButton.equals(pepsu)) {
            addUpdatePedido(0.95, 5, "Pepsu");
        } else if (sourceButton.equals(drdr)) {
            addUpdatePedido(1.30, 6, "Dr. Dr");
        }
    }

    @FXML
    public void confirmarPago() throws IOException {
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.CONFIRMATION);
        a.setHeaderText("Confirmar");
        a.setContentText("¿Confirmar pago?");
        Optional<ButtonType> okCancel = a.showAndWait();
        if (okCancel.get() != ButtonType.CANCEL) {
            //si el contador del producto es mayor de 1
            if(factura.getPedidosData().size()>0){
               pagar();
            }else{
                PrimaryController.despacharMesa(numMesa);
                switchToPrimary();
                listaDeFacturas.remove(factura);
            }
        }
    }
    @FXML
    public void eliminar() {
        if (listaPedido.getSelectionModel().getSelectedItem() != null) {
            Producto middleman = new Producto();
            Producto producto2 = copyProduct(listaPedido.getSelectionModel().getSelectedItem(), middleman);
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.CONFIRMATION);
            a.setHeaderText("Confirmar");
            a.setContentText("¿Eliminar 1 producto?");
            Optional<ButtonType> okCancel = a.showAndWait();
            if (okCancel.get() != ButtonType.CANCEL) {
                //si el contador del producto es mayor de 1
                if(factura.getContadores()[producto2.getId()-1]>1){
                    factura.getContadores()[producto2.getId()-1]--;//se resta uno a la cantidad en la factura
                    producto2.setCantidad(producto2.getCantidad()-1); //se resta uno en el contador
                    factura.getPedidosData().remove(listaPedido.getSelectionModel().getSelectedItem());//se elimina el producto seleccionado
                    factura.getPedidosData().add(producto2);//se añade la copia actualizada
                    //se actualiza la base de datos
                    db.updatePedido(factura.getContadores()[producto2.getId()-1],//cantidad nueva
                            producto2.getId(),factura.getId());//idpedido e idfactura
                }else{
                    //si solo queda 1 producto en la lista

                    db.eliminarPedido(listaPedido.getSelectionModel().getSelectedItem());
                    factura.getContadores()[listaPedido.getSelectionModel().getSelectedItem().getId()-1]=0;
                    factura.getPedidosData().remove(listaPedido.getSelectionModel().getSelectedItem());
                }
                total-=producto2.getPrecio();
            }
        }else{
            System.out.println("Es null");
            Alert b = new Alert(Alert.AlertType.ERROR);
            b.setAlertType(Alert.AlertType.ERROR);
            b.setHeaderText("Error");
            b.setContentText("Debes seleccionar un producto primero.");
            b.show();
        }
    }
    /**
     * Actualiza o añade un producto al pedido
     * @param precio
     * @param idProducto
     * @param nombreProducto
     */
    public void addUpdatePedido(double precio, int idProducto, String nombreProducto) {
        factura.getContadores()[idProducto - 1] += 1;//añado +1 a su contador
        total += precio;//lo sumo al total de la cuenta
        labelTotal.setText(String.valueOf((double) Math.round(total * 100d) / 100d));//cargo el total en la lable
        if (factura.getContadores()[idProducto - 1] > 1) {//si el contador es mayor que 1, actualizo el registro
            db.updatePedido(factura.getContadores()[idProducto - 1], idProducto, factura.getId());
            for (int i = 0; i < factura.getPedidosData().size(); i++) {
                if (idProducto == factura.getPedidosData().get(i).getId()) {
                    factura.getPedidosData().remove(i);
                }
            }
            Producto producto = new Producto(idProducto, nombreProducto, precio, factura.getContadores()[idProducto - 1]);
            factura.getPedidosData().add(producto);
        } else {//si no, añado el registro
            Producto producto = new Producto(idProducto, nombreProducto, precio, factura.getContadores()[idProducto - 1]);
            db.addPedido(numMesa, factura.getId(), idProducto, factura.getContadores()[idProducto - 1]);
            factura.getPedidosData().add(producto);
        }
        listaPedido.setItems(factura.getPedidosData());
    }

    /**
     * Copia un objeto a otro objeto sin hacer referencia al mismo puntero
     * @param original
     * @param copia
     * @return
     */
    public static Producto copyProduct(Producto original, Producto copia){
        copia.setId(original.getId());copia.setCantidad(original.getCantidad());
        copia.setNombre(original.getNombre());copia.setPrecio(original.getPrecio());
        return copia;
    }
    public void pagar() throws IOException {
        PrimaryController.despacharMesa(numMesa);
        switchToPrimary();
        listaDeFacturas.remove(factura);
        imprimirFactura(factura.getId(), Double.parseDouble(labelTotal.getText()));
    }
    @FXML
    private void imprimirFactura(String factura, Double total) throws IOException {
        //App.setRoot("secondary");
        InputStream reportFile = getClass().getResourceAsStream("/Coffee_Table_Based.jrxml");

        try {
            DbHelper dbHelper = new DbHelper();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportFile);
            Map<String, Object> parametro = new HashMap<>();
            parametro.put("TOTAL", total);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, dbHelper.getC());
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | SQLException e) {
            throw new RuntimeException(e);
        }
    }





}