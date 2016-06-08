package com.adis.srm.sistemarepartomovil.parsepersist;

import com.adis.srm.sistemarepartomovil.constants.Constants;
import com.adis.srm.sistemarepartomovil.entity.Cliente;
import com.adis.srm.sistemarepartomovil.entity.TipoNoEntrega;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.entity.Repartidor;
import com.adis.srm.sistemarepartomovil.entity.ReporteNoEntrega;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Created by Equipo on 06/05/2016.
 */
public class JsonParserPersister {

    public static void parse(String response){
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject repartidorJson = jsonResponse.getJSONObject(Constants.JSON_REPARTIDOR);
            Repartidor repartidor = persistRepartidor(repartidorJson);
            JSONArray pedidosArray = jsonResponse.getJSONArray(Constants.JSON_PEDIDOS);
            for(int i = 0; i < pedidosArray.length(); i++){
                JSONObject pedidosObject = pedidosArray.getJSONObject(i);
                Pedido pedido = persistPedidos(pedidosObject);
                JSONObject clienteObject = pedidosObject.getJSONObject(Constants.JSON_CLIENTE);
                persistClient(clienteObject, pedido);
                JSONArray productosArray = pedidosObject.getJSONArray(Constants.JSON_PRODUCTOS);
                for(int j = 0; j < productosArray.length(); j++){
                    persistProducto(productosArray.getJSONObject(j), pedido);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static Repartidor persistRepartidor(JSONObject repartidorJson){
        Repartidor repartidor = null;
        try {
            Long idRepartidor = repartidorJson.getLong("id");
            String nombre = repartidorJson.getString("nombre");
            Long ruta = repartidorJson.getLong("ruta");
            Long almacen = repartidorJson.getLong("almacen");
            String fechaConsulta = repartidorJson.getString("fecha_consulta");
            repartidor = new Repartidor(idRepartidor, nombre, ruta, almacen, fechaConsulta);
            repartidor.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return repartidor;
    }

    public static Pedido persistPedidos(JSONObject pedidoJson){
        Pedido pedido = null;
        try{
            Long numeroPedido = pedidoJson.getLong("numero_pedido");
            String fechaSolicitud = pedidoJson.getString("fecha_solicitud");
            String numeroFactura = pedidoJson.getString("numero_factura");
            String estado = pedidoJson.getString("estado");
            Long correlativo = pedidoJson.getLong("correlativo");
            pedido = new Pedido(numeroPedido, fechaSolicitud, numeroFactura, estado, correlativo);
            pedido.save();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return pedido;
    }

    public static void persistClient(JSONObject clienteJson, Pedido pedido){
        try {
            Long idCliente = clienteJson.getLong("id_cliente");
            String name = clienteJson.getString("nombre");
            String direccion = clienteJson.getString("direccion");
            String telefono = clienteJson.getString("telefono");
            String dui = clienteJson.getString("DUI");
            String nrc = clienteJson.getString("NRC");
            String nit = clienteJson.getString("NIT");
            String latitud = clienteJson.getJSONObject(Constants.JSON_COORDENADAS).getString("latitud");
            String longitud = clienteJson.getJSONObject(Constants.JSON_COORDENADAS).getString("longitud");
            Cliente cliente = new Cliente(idCliente, name, direccion, telefono, dui, nrc, nit, latitud, longitud, pedido);
            cliente.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void persistProducto(JSONObject productoJson, Pedido pedido){
        try {
            Long id = productoJson.getLong("id");
            String nombre = productoJson.getString("nombre");
            Long cantidad = productoJson.getLong("cantidad");
            BigDecimal costoUnitario = BigDecimal.valueOf(productoJson.getDouble("costo_unitario"));
            BigDecimal descuento = BigDecimal.valueOf(productoJson.getDouble("descuento"));
            BigDecimal costoTotal = getCostoTotal(cantidad, costoUnitario);
            String descripcion = productoJson.getString("descripcion");
            Producto producto = new Producto(id, nombre, cantidad, costoUnitario, costoTotal, descuento, descripcion, pedido);
            producto.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static BigDecimal getCostoTotal(Long cantidad, BigDecimal costoUnitario){

        BigDecimal costoTotal;
        BigDecimal quantity = new BigDecimal(cantidad);
        costoTotal = quantity.multiply(costoUnitario).setScale(2, RoundingMode.CEILING);
        return costoTotal;

    }

    public static void parseNoEntrega(String response){
        try {
            bulkDatabase();
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray noEntregadoArray = jsonResponse.getJSONArray(Constants.JSON_NO_ENTREGADO);
            for(int i = 0; i < noEntregadoArray.length(); i++){
                JSONObject noEntregadoObject = noEntregadoArray.getJSONObject(i);
                persistNoEntregados(noEntregadoObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void persistNoEntregados(JSONObject noEntregadoObject) {
        try {
            String idNoEntregado = noEntregadoObject.getString("id");
            String tipo = noEntregadoObject.getString("tipo");
            TipoNoEntrega tipoNoEntrega = new TipoNoEntrega(idNoEntregado, tipo);
            tipoNoEntrega.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void bulkDatabase(){
        List<Producto> productos = Producto.listAll(Producto.class);
        Producto.deleteAll(Producto.class);
        List<Cliente> cliente = Cliente.listAll(Cliente.class);
        Cliente.deleteAll(Cliente.class);
        List<Pedido> pedido = Pedido.listAll(Pedido.class);
        Pedido.deleteAll(Pedido.class);
        List<TipoNoEntrega> tipoNoEntrega = TipoNoEntrega.listAll(TipoNoEntrega.class);
        TipoNoEntrega.deleteAll(TipoNoEntrega.class);
        List<Repartidor> repartidors = Repartidor.listAll(Repartidor.class);
        Repartidor.deleteAll(Repartidor.class);
        List<ReporteNoEntrega> reporteNoEntregas = ReporteNoEntrega.listAll(ReporteNoEntrega.class);
        ReporteNoEntrega.deleteAll(ReporteNoEntrega.class);
    }

}