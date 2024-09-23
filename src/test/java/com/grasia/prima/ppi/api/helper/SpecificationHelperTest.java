package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.exception.BusinessException;
import com.grasia.prima.ppi.api.constant.GlobalMessage;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class SpecificationHelperTest {

    @Test
    void testInstanceSpecificationHelper() throws NoSuchMethodException {
        Constructor<SpecificationHelper> constructor = SpecificationHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        InvocationTargetException e = assertThrows(InvocationTargetException.class, constructor::newInstance);
        assertTrue(e.getCause() instanceof BusinessException);
        assertEquals(GlobalMessage.INTERNAL_SERVER_ERROR.message, e.getCause().getMessage());
    }

    @Test
    void testStringLike_NonEmptyValue() {
        String attribute = "name";
        String value = "John";

        Root root = Mockito.mock(Root.class);
        CriteriaQuery<?> query = Mockito.mock(CriteriaQuery.class);
        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);

        Mockito.when(root.get(attribute)).thenReturn(path);
        Mockito.when(cb.lower(path)).thenReturn(path);
        Mockito.when(cb.like(path, "%" + value.toLowerCase() + "%")).thenReturn(Mockito.mock(Predicate.class));

        Specification<Object> spec = SpecificationHelper.stringLike(attribute, value);
        Predicate predicate = spec.toPredicate(root, query, cb);

        assertNotNull(predicate);
        Mockito.verify(root).get(attribute);
        Mockito.verify(cb).lower(path);
        Mockito.verify(cb).like(path, "%" + value.toLowerCase() + "%");
    }

    @Test
    void testStringLike_EmptyValue() {
        String attribute = "name";
        String value = "";

        Specification<Object> spec = SpecificationHelper.stringLike(attribute, value);
        Predicate predicate = spec.toPredicate(null, null, null);

        assertNull(predicate);
    }

    @SuppressWarnings("unchecked")
    @Test
    void testObjectEquals_NonNullValue() {
        String attribute = "name";
        Object value = "John";

        Root root = Mockito.mock(Root.class);
        CriteriaQuery<?> query = Mockito.mock(CriteriaQuery.class);
        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Path path = Mockito.mock(Path.class);

        Mockito.when(root.get(attribute)).thenReturn(path);
        Mockito.when(cb.equal(path, value)).thenReturn(Mockito.mock(Predicate.class));

        Specification<Object> spec = SpecificationHelper.objectEquals(attribute, value);
        Predicate predicate = spec.toPredicate(root, query, cb);

        assertNotNull(predicate);
        Mockito.verify(root).get(attribute);
        Mockito.verify(cb).equal(path, value);
    }

    @Test
    void testObjectEquals_NullValue() {
        String attribute = "name";

        Specification<Object> spec = SpecificationHelper.objectEquals(attribute, null);
        Predicate predicate = spec.toPredicate(null, null, null);

        assertNull(predicate);
    }
}