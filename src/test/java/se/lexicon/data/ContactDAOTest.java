package se.lexicon.data;

import org.junit.jupiter.api.Test;
import se.lexicon.exception.ContactStorageException;
import se.lexicon.exception.DuplicateContactException;
import se.lexicon.model.Contact;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactDAOTest {

    @Test
    void isAnInterface() {
        assertTrue(ContactDAO.class.isInterface());
    }

    @Test
    void findAllReturnsContactsAndDeclaresStorageException() throws NoSuchMethodException {
        Method method = ContactDAO.class.getMethod("findAll");

        assertEquals(List.class, method.getReturnType());
        assertArrayEquals(
                new Class<?>[]{ContactStorageException.class},
                method.getExceptionTypes()
        );
    }

    @Test
    void saveAcceptsContactAndDeclaresStorageAndDuplicateExceptions()
            throws NoSuchMethodException {
        Method method = ContactDAO.class.getMethod("save", Contact.class);

        assertEquals(void.class, method.getReturnType());
        assertArrayEquals(
                new Class<?>[]{ContactStorageException.class, DuplicateContactException.class},
                method.getExceptionTypes()
        );
    }

    @Test
    void findByNameReturnsContactAndDeclaresStorageException() throws NoSuchMethodException {
        Method method = ContactDAO.class.getMethod("findByName", String.class);

        assertEquals(Contact.class, method.getReturnType());
        assertArrayEquals(
                new Class<?>[]{ContactStorageException.class},
                method.getExceptionTypes()
        );
    }
}
