package interfaces;

import java.util.List;

import dto.Producto;

public interface ProductoDao{
    public void agregar(Producto p);
    public void delete(int id);
    public void update(int id,Producto p);
}
