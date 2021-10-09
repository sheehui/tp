//package seedu.address.ui;
//
//import javafx.application.Application;
//import javafx.fxml.FXML;
//import javafx.stage.Stage;
//import org.junit.jupiter.api.*;
//import org.w3c.dom.Attr;
//import seedu.address.model.person.*;
//import seedu.address.ui.AttributePanel;
//import seedu.address.ui.TestUi.TestPanel;
//import seedu.address.ui.TestUi.TestPanelLauncher;
//
//import java.util.concurrent.atomic.AtomicReference;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class AttributePanelTest {
//
//    private Attribute name;
//    private Attribute phone;
//    private Attribute email;
//    private Attribute address;
//
//    @FXML
//    private AttributePanel namePanel;
//
//    @FXML
//    private AttributePanel phonePanel;
//
//    @FXML
//    private AttributePanel emailPanel;
//
//    @FXML
//    private AttributePanel addressPanel;
//
//    @BeforeAll
//    public static void startTestPanel() {
//        Application.launch(TestPanel.class);
//    }
//
//    @BeforeEach
//    public void setUp() {
//        this.name = new Name("Steve Yeo");
//        //Yes thats my real number. Please do not call me
//        this.phone = new Phone("97299502");
//        //Nope this is my dummy email. Syke hahaha
//        this.email = new Email("Bluntsord@gmail.com");
//        //Yes tembu is my home.
//        this.address = new Address("28 College Ave E, #B1-01, Singapore 138598");
//
//
//        phonePanel = new AttributePanel(name);
//        emailPanel = new AttributePanel(name);
//        addressPanel = new AttributePanel(name);
//    }
//
//    @Test
//    public void successAttributePanelEquals() {
//        AttributePanel attributePanel = new AttributePanel(name);
//        AttributePanel otherAttributePanel = new AttributePanel(name);
////        assertEquals(attributePanel, otherAttributePanel);
//    }
//
//    @Test
//    public void failureAttributePanelEquals() {
//        AttributePanel attributePanel = new AttributePanel(name);
//        AttributePanel otherAttributePanel = new AttributePanel(email);
//        boolean isNotEqual = !attributePanel.equals(otherAttributePanel);
////        assertTrue(isNotEqual);
//    }
//
//    @Test
//    public void successNameAttributePanel() {
////        AttributePanel attributePanel = new AttributePanel(name);
//    }
//
//    @AfterAll
//    public static void closeTestPanel() {
////        Application.
//    }
//}
