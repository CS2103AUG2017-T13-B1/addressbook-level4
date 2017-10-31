package seedu.address.ui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonNameClickedEvent;
import seedu.address.model.insurance.ReadOnlyInsurance;

//@@author OscarWang114
/**
 * The Profile Panel of the App.
 */
public class InsuranceProfile extends UiPart<Region> {

    private static final String FXML = "InsuranceProfile.fxml";
    private static final String PDFFOLDERPATH = "data/";

    private final Logger logger = LogsCenter.getLogger(this.getClass());


    private ReadOnlyInsurance insurance;

    @FXML
    private Label index;
    @FXML
    private Label owner;
    @FXML
    private Label insured;
    @FXML
    private Label beneficiary;
    @FXML
    private Label premium;
    @FXML
    private Label contractPath;
    @FXML
    private Label signingDate;
    @FXML
    private Label expiryDate;

    public InsuranceProfile(ReadOnlyInsurance insurance, int displayIndex) {
        super(FXML);
        this.insurance = insurance;
        index.setText(displayIndex + ".");
        owner.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getOwner())));
        insured.setOnMouseClicked(e -> raise(new PersonNameClickedEvent(insurance.getInsured())));
        beneficiary.setOnMouseClicked(e ->
                raise(new PersonNameClickedEvent(insurance.getBeneficiary())));
        contractPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    File file = new File(PDFFOLDERPATH + insurance.getContractPath());
                    Desktop.getDesktop().open(file);
                    // HostServices hostServices = getRoot().getHostservices();
                    // hostServices.showDocument(file.getAbsolutePath());
                } catch (IOException ee) {
                    logger.info("Cannot open file: " + PDFFOLDERPATH + insurance.getContractPath());
                }
            }
            });
        bindListeners(insurance);
        registerAsAnEventHandler(this);
    }

    /**
     * Binds the individual UI elements to observe their respective {@code ReadOnlyInsurance} properties
     * so that they will be notified of any changes.
     * @param insurance
     */
    private void bindListeners(ReadOnlyInsurance insurance) {
        owner.textProperty().bind(Bindings.convert(insurance.getOwner().nameProperty()));
        insured.textProperty().bind(Bindings.convert(insurance.getInsured().nameProperty()));
        beneficiary.textProperty().bind(Bindings.convert(insurance.getBeneficiary().nameProperty()));
        premium.textProperty().bind(Bindings.convert(insurance.premiumStringProperty()));
        contractPath.textProperty().bind(Bindings.convert(insurance.contractPathProperty()));
        signingDate.textProperty().bind(Bindings.convert(insurance.signingDateStringProperty()));
        expiryDate.textProperty().bind(Bindings.convert(insurance.expiryDateStringProperty()));
    }
}
