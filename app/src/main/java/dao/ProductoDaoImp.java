package dao;

import java.util.ArrayList;
import java.util.List;

import dto.Producto;
import interfaces.ProductoDao;

public class ProductoDaoImp implements ProductoDao{
    private List<Producto> lista;

    public ProductoDaoImp() {
        lista = new ArrayList<>();
    }

    public List<Producto> getLista() {
        return lista;
    }

    @Override
    public void agregar(Producto p) {
        lista.add(p);
    }

    @Override
    public void delete(int id) {
        lista.remove(id);
    }

    @Override
    public void update(int id,Producto p) {
        lista.set(id,p);
    }


}
