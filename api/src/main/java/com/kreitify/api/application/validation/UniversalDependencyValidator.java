package com.kreitify.api.application.validation;

import com.kreitify.api.domain.annotation.RestrictedBy;
import com.kreitify.api.domain.exception.EntityInUseException;
import com.kreitify.api.domain.validation.DeleteValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

@Component
public class UniversalDependencyValidator implements DeleteValidator<Object> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void validate(Object entity) {
        Class<?> entityClass = (entity instanceof HibernateProxy)
                ? ((HibernateProxy) entity).getHibernateLazyInitializer().getPersistentClass()
                : entity.getClass();

        if (!entityClass.isAnnotationPresent(RestrictedBy.class)) {
            return;
        }

        RestrictedBy restriction = entityClass.getAnnotation(RestrictedBy.class);
        Class<?> dependentClass = restriction.dependentEntity();
        String fieldName = restriction.fieldName();
        String displayName = restriction.displayName();

        try {
            Method getIdMethod = entityClass.getMethod("getId");
            Object idValue = getIdMethod.invoke(entity);

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<?> root = cq.from(dependentClass);

            cq.select(cb.count(root));
            cq.where(cb.equal(root.get(fieldName).<Object>get("id"), idValue));

            Long count = entityManager.createQuery(cq).getSingleResult();

            if (count > 0) {
                throw new EntityInUseException(
                        "No se puede eliminar el registro porque tiene " + displayName + " asociadas."
                );
            }

        } catch (EntityInUseException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al validar dependencias de eliminación para: " + entityClass.getSimpleName(), e);
        }
    }
}