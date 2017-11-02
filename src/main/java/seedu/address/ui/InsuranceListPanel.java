package seedu.address.ui;

import java.util.logging.Logger;

import org.fxmisc.easybind.EasyBind;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.InsurancePanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SwitchToProfilePanelRequestEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author OscarWang114
/**
 * The Insurance Panel of the App.
 */
public class InsuranceListPanel extends UiPart<Region> {

    private static final String FXML = "InsuranceListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private ListView<InsuranceProfile> insuranceListView;

    public InsuranceListPanel() {
        super(FXML);
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    public InsuranceListPanel(ObservableList<ReadOnlyInsurance> insuranceList) {
        super(FXML);
        setConnections(insuranceList);
        registerAsAnEventHandler(this);
    }
    /**
     * Load default page with nothing
     */
    private void loadDefaultPage() {

    }

    /**
     * To be called everytime a new person is selected and bind the list of insurance of that person
     * @param insuranceList
     */
    public void setConnections(ObservableList<ReadOnlyInsurance> insuranceList) {
        ObservableList<InsuranceProfile> mappedList = EasyBind.map(
                insuranceList, (insurance) -> new InsuranceProfile(insurance, insuranceList.indexOf(insurance) + 1));
        insuranceListView.setItems(mappedList);
        insuranceListView.setCellFactory(listView -> new InsuranceListPanel.InsuranceListViewCell());
        setEventHandlerForSelectionChangeEvent();

    }

    //@@author RSJunior37
    private void setEventHandlerForSelectionChangeEvent() {
        insuranceListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in insurance list panel changed to : '" + newValue + "'");
                        raise(new InsurancePanelSelectionChangedEvent(newValue));
                    }
                });
    }
    //@@author

    //@@author Juxarius
    @Subscribe
    private void handleSwitchToProfilePanelRequestEvent(SwitchToProfilePanelRequestEvent event) {
        insuranceListView.getSelectionModel().clearSelection();
    }

    /*@Subscribe
    private void handleInsurancePanelSelectionChangedEvent(InsurancePanelSelectionChangedEvent event) {
        InsuranceProfile selected = insuranceListView.getItems().filtered(insuranceProfile -> {
            return insuranceProfile.getInsurance().equals(event.getInsurance());
        }).get(0);
        insuranceListView.getSelectionModel().select(selected);
    }*/
    //@@author

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InsuranceProfile}.
     */
    class InsuranceListViewCell extends ListCell<InsuranceProfile> {
        @Override
        protected void updateItem(InsuranceProfile insurance, boolean empty) {
            super.updateItem(insurance, empty);

            if (empty || insurance == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(insurance.getRoot());
            }
        }
    }
}
