package com.adis.srm.sistemarepartomovil.parsepersist;

import com.adis.srm.sistemarepartomovil.entity.Cliente;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.entity.Repartidor;
import com.adis.srm.sistemarepartomovil.entity.ReporteNoEntrega;
import com.adis.srm.sistemarepartomovil.entity.TipoNoEntrega;
import com.adis.srm.sistemarepartomovil.entity.TransaccionItem;
import com.adis.srm.sistemarepartomovil.models.FacturaListView;
import com.adis.srm.sistemarepartomovil.models.SubtotalesProducto;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Equipo on 07/05/2016.
 */
public class Retriever {

    public static List<FacturaListView> retrieveFacturaList(){


        List<Pedido> pedidosList = Select.from(Pedido.class)
                .where(Condition.prop("estado").eq("en proceso"))
                .orderBy("correlativo")
                .list();
        List<FacturaListView> facturaList = new ArrayList<FacturaListView>();

        for(Pedido pedido : pedidosList){
            FacturaListView facturaListView = new FacturaListView();
            facturaListView.setNumPedido(String.valueOf(pedido.getNumeroPedido()));
            facturaListView.setFactura(pedido.getNumeroFactura());
            List<Cliente> clientes = Cliente.find(Cliente.class, "pedido = ?", String.valueOf(pedido.getId()));
            for(Cliente cliente : clientes){
                facturaListView.setCliente(cliente.getName());
                facturaListView.setDireccion(cliente.getDireccion());
                facturaListView.setTelefono(cliente.getTelefono());
                facturaListView.setDui(cliente.getDui());
            }
            facturaList.add(facturaListView);
        }
        return facturaList;
    }

    public static FacturaListView getFacturaViewByNumFactura(String numFactura){
        List<Pedido> pedidoList = Pedido.listAll(Pedido.class);
        FacturaListView facturaListView = new FacturaListView();
        for(Pedido pedido : pedidoList){
            if(numFactura.equalsIgnoreCase(pedido.getNumeroFactura())){
                facturaListView.setNumPedido(String.valueOf(pedido.getNumeroPedido()));
                facturaListView.setFactura(pedido.getNumeroFactura());
                List<Cliente> clientes = Cliente.find(Cliente.class, "pedido = ?", String.valueOf(pedido.getId()));
                for(Cliente cliente : clientes){
                    facturaListView.setCliente(cliente.getName());
                    facturaListView.setDireccion(cliente.getDireccion());
                    facturaListView.setTelefono(cliente.getTelefono());
                }
                break;
            }
        }
        return facturaListView;
    }


    public static List<Producto> getProductoByInvoiceNumber(String numFactura) {
        List<Producto> productoList = new ArrayList<Producto>();
        List<Pedido> pedidoList = Pedido.listAll(Pedido.class);
        for(Pedido pedido : pedidoList){
            if(numFactura.equalsIgnoreCase(pedido.getNumeroFactura())){
                productoList = Producto.find(Producto.class, "pedido = ?", String.valueOf(pedido.getId()));
                break;
            }
        }
        return productoList;
    }

    public static Producto getProductoByIdProductoIdInvoice(String numFactura, String idProducto){
        List<Producto> productoList = getProductoByInvoiceNumber(numFactura);
        for(Producto producto : productoList){
            if(idProducto.equalsIgnoreCase(String.valueOf(producto.getIdProducto()))){
                return producto;
            }
        }
        return null;
    }

    public static SubtotalesProducto getSubtotales(List<Producto> productoList) {

        SubtotalesProducto subtotales = new SubtotalesProducto();
        for(Producto producto : productoList){
            subtotales.sumarSubTotal(producto.getCostoTotal());
            subtotales.sumarIVA(producto.getIva());
            subtotales.sumarDescuento(producto.getDescuento());
        }
        return subtotales;
    }

    public static FacturaListView getClientByNumFactura(String numFactura){
        FacturaListView facturaListView = new FacturaListView();
        List<Pedido> pedidoList = Pedido.listAll(Pedido.class);
        for(Pedido pedido : pedidoList){
            if(numFactura.equalsIgnoreCase(pedido.getNumeroFactura())){
                facturaListView.setFactura(pedido.getNumeroFactura());
                facturaListView.setNumPedido(String.valueOf(pedido.getNumeroPedido()));
                List<Cliente> clientes = Cliente.find(Cliente.class, "pedido = ?", String.valueOf(pedido.getId()));
                for(Cliente cliente : clientes){
                    facturaListView.setCliente(cliente.getName());
                    facturaListView.setDireccion(cliente.getDireccion());
                    facturaListView.setTelefono(cliente.getTelefono());
                }
                break;
            }
        }
        return facturaListView;
    }

    public static void procesarPedido(String numFactura, String estado){
        List<Pedido> pedidoList = Pedido.listAll(Pedido.class);
        for(Pedido pedido : pedidoList){
            if(numFactura.equalsIgnoreCase(String.valueOf(pedido.getNumeroFactura()))) {
                pedido.setEstado(estado);
                pedido.save();
            }
        }
    }

    public static HashMap<String, String> getTiposNoEntrega() {
        HashMap<String, String> tiposNoEntrega = new HashMap<String, String>();
        List<TipoNoEntrega> tipoNoEntregaList = TipoNoEntrega.listAll(TipoNoEntrega.class);
        for(TipoNoEntrega tipoNoEntrega : tipoNoEntregaList){
            tiposNoEntrega.put(tipoNoEntrega.getIdTipoNoEntrega(), tipoNoEntrega.getTipo());
        }
        return tiposNoEntrega;
    }

    public static Repartidor getRepartidor(){
        return Repartidor.listAll(Repartidor.class).iterator().next();
    }

    public static List<Pedido> getPedidos(){
        return Pedido.listAll(Pedido.class);
    }

    public static String getMotivoByPedido(Pedido pedido){
        String motivo = "";
        List<ReporteNoEntrega> reporteEntregaList = ReporteNoEntrega.find(ReporteNoEntrega.class, "pedido = ?", String.valueOf(pedido.getId()));
        if(reporteEntregaList.size() == 0){
            return motivo;
        }else{
            return reporteEntregaList.iterator().next().getMotivo();
        }
    }

    public static Cliente getClienteByIdPedido(Pedido pedido){
        return Cliente.find(Cliente.class, "pedido = ?", String.valueOf(pedido.getId())).iterator().next();
    }

    public static List<Producto> getProductosByIdPedido(String idPedido){
        return Producto.find(Producto.class, "pedido = ?", idPedido);
    }

    public static TransaccionItem getTransaccionByIdProducto(String idProducto){
        //List<TransaccionItem> transaccionItem = TransaccionItem.find(TransaccionItem.class, "idProducto = ?", idProducto);
        List<TransaccionItem> transaccionItem = Select.from(TransaccionItem.class)
                .where(Condition.prop("num_producto").eq(Long.valueOf(idProducto))).list();
        if(transaccionItem.size() == 0){
            return null;
        }else{
            return transaccionItem.iterator().next();
        }
    }

    public static Pedido getPedidoById(String numPedido) {
        return Select.from(Pedido.class).where(Condition.prop("numeroPedido").eq(numPedido)).first();

    }
}
