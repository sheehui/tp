//@@author bharathcs-reused
//Reused from https://github.com/se-edu/addressbook-level4/ with minor modifications.
package donnafin.ui;

import static donnafin.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import donnafin.model.person.Person;
import donnafin.testutil.PersonBuilder;
import guitests.guihandles.PersonCardHandle;

public class PersonCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 1);
        uiPartExtension.setUiPart(personCard);
        assertCardDisplay(personCard, person, 1);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertEquals(personCard, copy);

        // same object -> returns true
        assertEquals(personCard, personCard);

        // null -> returns false
        assertNotEquals(null, personCard);

        // different types -> returns false
        assertNotEquals(0, personCard);

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertNotEquals(personCard, new PersonCard(differentPerson, 0));

        // same person, different index -> returns false
        assertNotEquals(personCard, new PersonCard(person, 1));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Person expectedPerson, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(expectedId + ". ", personCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysPerson(expectedPerson, personCardHandle);
    }
}

//@@author
