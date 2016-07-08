package com.adis.srm.sistemarepartomovil.parsepersist;

import com.adis.srm.sistemarepartomovil.entity.Cliente;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.entity.Repartidor;
import com.adis.srm.sistemarepartomovil.entity.TransaccionItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Equipo on 01/06/2016.
 */
public class JsonConstructor {


    public static JSONObject constructSyncJson() throws JSONException{

        //Construir el siguiente json
        //Json Principal
        JSONObject mainJson = new JSONObject();
        //Json repartidor
        JSONObject repartidorJson = new JSONObject();
        //JsonArray pedidos
        JSONArray pedidosArray = new JSONArray();
        mainJson.put("repartidor", repartidorJson);
        mainJson.put("pedidos", pedidosArray);

        Repartidor repartidor = Retriever.getRepartidor();

        repartidorJson.put("idRepartidor", repartidor.getIdRepartidor());
        repartidorJson.put("nombre", repartidor.getNombre());
        repartidorJson.put("idRuta", repartidor.getRuta());
        repartidorJson.put("almacen", repartidor.getAlmacen());
        repartidorJson.put("fecha_consulta", repartidor.getFechaConsulta());


        //Iterar sobre la lista de pedidos para crear el objeto json contenedor "pedidos"
        List<Pedido> pedidoList = Retriever.getPedidos();
        for(Pedido pedido : pedidoList){
            JSONObject pedidoJson = new JSONObject();
            pedidoJson.put("idPedido", pedido.getNumeroPedido());
            pedidoJson.put("fecha_solicitud", pedido.getFechaSolicitud());
            pedidoJson.put("numero_factura", pedido.getNumeroFactura());
            pedidoJson.put("estado", pedido.getEstado());
            pedidoJson.put("correlativo",pedido.getCorrelativo());
            String motivo = Retriever.getMotivoByPedido(pedido);
            if(!"".equalsIgnoreCase(motivo)){
                pedidoJson.put("motivo", motivo);
            }
            pedidosArray.put(pedidoJson);
            JSONObject clienteJson = new JSONObject();
            JSONArray productoArray = new JSONArray();
            pedidoJson.put("cliente", clienteJson);
            pedidoJson.put("productos", productoArray);
            Cliente cliente = Retriever.getClienteByIdPedido(pedido);
            clienteJson.put("idCliente", cliente.getIdCliente());
            clienteJson.put("nombre", cliente.getName());
            clienteJson.put("direccion", cliente.getDireccion());
            clienteJson.put("DUI", cliente.getDui());
            clienteJson.put("NRC", cliente.getNrc());
            clienteJson.put("NIT", cliente.getNit());
            List<Producto> productoList = new ArrayList<Producto>();
            productoList = Retriever.getProductosByIdPedido(String.valueOf(pedido.getId()));
            for(Producto producto : productoList){
                JSONObject productoJson = new JSONObject();
                productoJson.put("idProducto", producto.getIdProducto());
                productoJson.put("nombre", producto.getNombre());
                productoJson.put("stock", producto.getCantidad());
                productoJson.put("precio", producto.getCostoUnitario());
                productoJson.put("descuento", producto.getDescuento());
                productoJson.put("descripcion", producto.getDescripcion());
                productoArray.put(productoJson);
                TransaccionItem transaccionItem = Retriever.getTransaccionByIdProducto(String.valueOf(producto.getIdProducto()));
                if(transaccionItem != null){
                    productoJson.put("cantidad_entregada", transaccionItem.getCantidad());
                }
            }
        }
        return mainJson;
    }
}
