/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

/**
 *
 * @author Johan
 */
public interface GenericDao<T>
{
    public List<T> findAll();
    public <U> T get(U id);
    public T update(T object);
    public void delete(T object);
    public void insert(T object);
    public <U> boolean exists(U id);
    
}
