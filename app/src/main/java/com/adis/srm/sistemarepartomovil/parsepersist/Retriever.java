package com.adis.srm.sistemarepartomovil.parsepersist;

import com.adis.srm.sistemarepartomovil.entity.Cliente;
import com.adis.srm.sistemarepartomovil.entity.NoEntrega;
import com.adis.srm.sistemarepartomovil.entity.Pedido;
import com.adis.srm.sistemarepartomovil.entity.Producto;
import com.adis.srm.sistemarepartomovil.models.FacturaListView;
import com.adis.srm.sistemarepartomovil.models.SubtotalesProducto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Equipo on 07/05/2016.
 */
public class Retriever {

    public static List<FacturaListView> retrieveFacturaList(){

        List<Pedido> pedidosList = Pedido.listAll(Pedido.class);
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
            }
            facturaList.add(facturaListView);
        }
        return facturaList;
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

    public static void entregarPedido(String idPedido){
        List<Pedido> pedidoList = Pedido.listAll(Pedido.class);
        for(Pedido pedido : pedidoList){
            if(idPedido.equalsIgnoreCase(String.valueOf(pedido.getNumeroPedido())));
                pedido.setEstado("entregado");
                pedido.save();
        }
    }

    public static HashMap<String, String> getTiposNoEntrega() {
        HashMap<String, String> tiposNoEntrega = new HashMap<String, String>();
        List<NoEntrega> noEntregaList = NoEntrega.listAll(NoEntrega.class);
        for(NoEntrega noEntrega : noEntregaList){
            tiposNoEntrega.put(noEntrega.getIdNoEntrega(), noEntrega.getTipo());
        }
        return tiposNoEntrega;
    }
}
