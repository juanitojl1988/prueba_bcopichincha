package ec.bcopichincha.CoreCuentas.service;

import org.springframework.data.domain.Page;

public interface ServiceInterface<T, I> {

    /**
     * @param page
     * @param size
     * @return
     */
    Page<T> findAll(Integer page, Integer size);

   

    /**
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * @param entity
     * @return
     */
    T update(T entity);

    /**
     * @param id
     * @return
     */
    T findOne(I id);

    /**
     * @param id
     */
    void delete(I id);

}
