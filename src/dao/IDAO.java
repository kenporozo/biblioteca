/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public interface IDAO {
    
    public abstract ArrayList<Object> getList();
    
    public abstract boolean insertar(Object obj);
    
    public abstract boolean modificar(int id, Object obj);
    
    public abstract boolean eliminar(int id);
    
    public abstract Object buscar(int id);
}
