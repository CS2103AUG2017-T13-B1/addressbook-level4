package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.EnumMap;
import java.util.UUID;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.DateParser;
import seedu.address.model.person.ReadOnlyPerson;

//@@author OscarWang114
/**
 * Represents a life insurance in LISA.
 * Guarantees: details are present and not null.
 */
public class LifeInsurance implements ReadOnlyInsurance {

    /**
     * Represents the three person-roles inside an insurance in LISA.
     */
    enum Roles { OWNER, INSURED, BENEFICIARY }
    private ObjectProperty<UUID> id;
    private EnumMap<Roles, String> roleToPersonNameMap;
    private ObjectProperty<InsurancePerson> owner;
    private ObjectProperty<InsurancePerson> insured;
    private ObjectProperty<InsurancePerson> beneficiary;
    private DoubleProperty premium;
    private StringProperty premiumString;
    private StringProperty contractName;
    private StringProperty signingDateString;
    private StringProperty expiryDateString;

    //@@author Juxarius
    private StringProperty insuranceName;
    private LocalDate signingDate;
    private LocalDate expiryDate;
    //@@author

    //@author OscarWang114
    /**
     * Constructor for {@code XmlAdaptedLifeInsurance.toModelType()}
     */
    public LifeInsurance(String id, String insuranceName, String owner, String insured, String beneficiary,
                         Double premium, String contractName, String signingDateInput, String expiryDateInput)
            throws IllegalValueException {
        this.id = new SimpleObjectProperty<>(UUID.fromString(id));
        this.insuranceName = new SimpleStringProperty(insuranceName);
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, owner);
        this.roleToPersonNameMap.put(Roles.INSURED, insured);
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, beneficiary);
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(owner));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insured));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiary));
        this.premium = new SimpleDoubleProperty(premium);
        this.contractName = new SimpleStringProperty(contractName);
        this.signingDate = new DateParser().parse(signingDateInput);
        this.signingDateString = new SimpleStringProperty(this.signingDate.format(DateParser.DATE_FORMAT));
        this.expiryDate = new DateParser().parse(expiryDateInput);
        this.expiryDateString = new SimpleStringProperty(this.expiryDate.format(DateParser.DATE_FORMAT));
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
    }

    /**
     * Constructor for {@code AddLifeInsuranceCommand}
     */
    public LifeInsurance(String insuranceName, String owner, String insured, String beneficiary, Double premium,
                         String contractName, LocalDate signingDate, LocalDate expiryDate) {
        requireAllNonNull(owner, insured, beneficiary, premium, contractName);
        this.id = new SimpleObjectProperty<>(UUID.randomUUID());
        this.insuranceName = new SimpleStringProperty(insuranceName);
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, owner);
        this.roleToPersonNameMap.put(Roles.INSURED, insured);
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, beneficiary);
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(owner));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insured));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiary));
        this.premium = new SimpleDoubleProperty(premium);
        this.contractName = new SimpleStringProperty(contractName);
        this.signingDate = signingDate;
        this.signingDateString = new SimpleStringProperty(this.signingDate.format(DateParser.DATE_FORMAT));
        this.expiryDate = expiryDate;
        this.expiryDateString = new SimpleStringProperty(this.expiryDate.format(DateParser.DATE_FORMAT));
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
    }

    /**
     * Creates a copy of the given ReadOnlyInsurance.
     */
    public LifeInsurance(ReadOnlyInsurance source) {
        this.id = new SimpleObjectProperty<>(source.idProperty().get());
        this.insuranceName = new SimpleStringProperty(source.getInsuranceName());
        this.roleToPersonNameMap = new EnumMap<>(Roles.class);
        this.roleToPersonNameMap.put(Roles.OWNER, source.getOwner().getName());
        this.roleToPersonNameMap.put(Roles.INSURED, source.getInsured().getName());
        this.roleToPersonNameMap.put(Roles.BENEFICIARY, source.getBeneficiary().getName());
        this.owner = new SimpleObjectProperty<>(source.getOwner());
        this.insured = new SimpleObjectProperty<>(source.getInsured());
        this.beneficiary = new SimpleObjectProperty<>(source.getBeneficiary());
        this.premium = new SimpleDoubleProperty(source.getPremium());
        this.premiumString = new SimpleStringProperty(this.getPremiumString());
        this.contractName = new SimpleStringProperty(source.getContractName());
        this.signingDateString = new SimpleStringProperty(source.getSigningDateString());
        this.expiryDateString = new SimpleStringProperty(source.getExpiryDateString());
    }

    /**
     * Resets the value of the owner, insured, beneficiary of this insurance. Every new {@code InsurancePerson}
     * object is constructed with the {@code name} field assigned and the {@code person} field unassigned.
     */
    public void resetAllInsurancePerson() {
        String ownerName = this.roleToPersonNameMap.get(Roles.OWNER);
        String insuredName = this.roleToPersonNameMap.get(Roles.INSURED);
        String beneficiaryName = this.roleToPersonNameMap.get(Roles.BENEFICIARY);
        this.owner = new SimpleObjectProperty<>(new InsurancePerson(ownerName));
        this.insured = new SimpleObjectProperty<>(new InsurancePerson(insuredName));
        this.beneficiary = new SimpleObjectProperty<>(new InsurancePerson(beneficiaryName));

    }

    @Override
    public ObjectProperty<UUID> idProperty() {
        return id;
    }

    @Override
    public UUID getId() {
        return id.get();
    }

    @Override
    public EnumMap getRoleToPersonNameMap() {
        return roleToPersonNameMap;
    }

    public void setOwner(ReadOnlyPerson owner) {
        requireNonNull(owner);
        this.owner.get().setPerson(owner);
    }

    @Override
    public ObjectProperty<InsurancePerson> ownerProperty() {
        return owner;
    }

    public void setInsuranceName(String insuranceName) {
        this.insuranceName.set(requireNonNull(insuranceName));
    }

    @Override
    public InsurancePerson getOwner() {
        return owner.get();
    }

    @Override
    public String getOwnerName() {
        return owner.get().getName();
    }

    public void setInsured(ReadOnlyPerson insured) {
        requireNonNull(insured);
        this.insured.get().setPerson(insured);
    }

    @Override
    public ObjectProperty<InsurancePerson> insuredProperty() {
        return insured;
    }

    @Override
    public InsurancePerson getInsured() {
        return insured.get();
    }

    @Override
    public String getInsuredName() {
        return insured.get().getName();
    }

    public void setBeneficiary(ReadOnlyPerson beneficiary) {
        requireNonNull(beneficiary);
        this.beneficiary.get().setPerson(beneficiary);
    }

    @Override
    public ObjectProperty<InsurancePerson> beneficiaryProperty() {
        return beneficiary;
    }

    @Override
    public InsurancePerson getBeneficiary() {
        return beneficiary.get();
    }

    @Override
    public String getBeneficiaryName() {
        return beneficiary.get().getName();
    }

    public void setPremium(Double premium) {
        this.premium.set(requireNonNull(premium));
        this.premiumString.set(getPremiumString());
    }

    @Override
    public DoubleProperty premiumProperty() {
        return premium;
    }

    @Override
    public Double getPremium() {
        return premium.get();
    }

    //@author Juxarius
    @Override
    public StringProperty insuranceNameProperty() {
        return insuranceName;
    }

    @Override
    public String getInsuranceName() {
        return insuranceName.get();
    }

    @Override
    public StringProperty premiumStringProperty() {
        return premiumString;
    }

    @Override
    public String getPremiumString() {
        return "S$ " + String.format("%.2f", premium.get());
    }
    //@author

    //@@author OscarWang114
    @Override
    public StringProperty contractNameProperty() {
        return contractName;
    }

    @Override
    public String getContractName() {
        return contractName.get();
    }

    public void setSigningDateString(String signingDateString) throws IllegalValueException {
        this.signingDate = new DateParser().parse(signingDateString);
        this.signingDateString.set(requireNonNull(signingDateString));
    }

    @Override
    public StringProperty signingDateStringProperty() {
        return signingDateString;
    }

    @Override
    public String getSigningDateString() {
        return signingDateString.get();
    }

    public void setExpiryDateString(String expiryDateString) throws IllegalValueException {
        this.expiryDate = new DateParser().parse(expiryDateString);
        this.expiryDateString.set(requireNonNull(expiryDateString));
    }

    @Override
    public StringProperty expiryDateStringProperty() {
        return expiryDateString;
    }

    @Override
    public String getExpiryDateString() {
        return expiryDateString.get();
    }

    public void setContractName(String contractName) {
        this.contractName.set(requireNonNull(contractName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LifeInsurance // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyInsurance) other));
    }

    @Override
    public String toString() {
        return getAsText();
    }
}

