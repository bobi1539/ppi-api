package com.grasia.prima.ppi.api.helper;

import com.grasia.prima.ppi.api.constant.GlobalMessage;
import com.grasia.prima.ppi.api.exception.BusinessException;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpecificationHelperTest {

    @Mock
    private Root root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder cb;

    @Mock
    private Path path;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

        when(root.get(attribute)).thenReturn(path);
        when(cb.lower(path)).thenReturn(path);
        when(cb.like(path, "%" + value.toLowerCase() + "%")).thenReturn(Mockito.mock(Predicate.class));

        Specification<Object> spec = SpecificationHelper.stringLike(attribute, value);
        Predicate predicate = spec.toPredicate(root, query, cb);

        assertNotNull(predicate);
        verify(root).get(attribute);
        verify(cb).lower(path);
        verify(cb).like(path, "%" + value.toLowerCase() + "%");
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

        when(root.get(attribute)).thenReturn(path);
        when(cb.equal(path, value)).thenReturn(Mockito.mock(Predicate.class));

        Specification<Object> spec = SpecificationHelper.objectEquals(attribute, value);
        Predicate predicate = spec.toPredicate(root, query, cb);

        assertNotNull(predicate);
        verify(root).get(attribute);
        verify(cb).equal(path, value);
    }

    @Test
    void testObjectEquals_NullValue() {
        String attribute = "name";

        Specification<Object> spec = SpecificationHelper.objectEquals(attribute, null);
        Predicate predicate = spec.toPredicate(null, null, null);

        assertNull(predicate);
    }

    @Test
    void testEntityIdEquals_NonNullValue() {
        String attribute = "systemParameter";
        Long value = 1L;

        when(root.get(attribute)).thenReturn(path);
        when(root.get(attribute).get("id")).thenReturn(path);
        when(cb.equal(path, value)).thenReturn(Mockito.mock(Predicate.class));

        Specification<Object> spec = SpecificationHelper.entityIdEquals(attribute, value);
        Predicate predicate = spec.toPredicate(root, query, cb);

        assertNotNull(predicate);
        verify(root, times(2)).get(attribute);
        verify(cb).equal(path, value);
    }

    @Test
    void testEntityIdEquals_NullValue() {
        String attribute = "systemParameter";

        Specification<Object> spec = SpecificationHelper.entityIdEquals(attribute, null);
        Predicate predicate = spec.toPredicate(null, null, null);

        assertNull(predicate);
    }

    @Test
    void testEntityIdEqualsTwoAttribute_NonNullValue() {
        String attribute1 = "division";
        String attribute2 = "period";
        Long value = 1L;

        when(root.get(attribute1)).thenReturn(path);
        when(root.get(attribute1).get(attribute2)).thenReturn(path);
        when(root.get(attribute1).get(attribute2).get("id")).thenReturn(path);
        when(cb.equal(path, value)).thenReturn(mock(Predicate.class));

        Specification<Object> spec = SpecificationHelper.entityIdEquals(attribute1, attribute2, value);
        Predicate predicate = spec.toPredicate(root, query, cb);

        assertNotNull(predicate);
        verify(root, times(3)).get(anyString());
        verify(cb).equal(path, value);
    }

    @Test
    void testEntityIdEqualsTwoAttribute_NullValue() {
        String attribute1 = "division";
        String attribute2 = "period";

        Specification<Object> spec = SpecificationHelper.entityIdEquals(attribute1, attribute2, null);
        Predicate predicate = spec.toPredicate(null, null, null);

        assertNull(predicate);
    }
}