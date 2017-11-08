package seedu.address.model.insurance;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.insurance.exceptions.DuplicateInsuranceException;
import seedu.address.model.insurance.exceptions.InsuranceNotFoundException;

//@@author OscarWang114
/**
 * A map of life insurances that enforces uniqueness between its elements and does not allow nulls.
 *
 * Supports a minimal set of map operations.
 *
 * @see LifeInsurance#equals(Object)
 * @see CollectionUtil#elementsAreUnique(Collection)
 */
public class UniqueLifeInsuranceMap {

    private final ObservableMap<UUID, LifeInsurance> internalMap = FXCollections.observableHashMap();
    private final ObservableList<ReadOnlyInsurance> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the map contains an equivalent UUID as the given argument.
     */
    public boolean containsKey(UUID toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsKey(toCheck);
    }

    /**
     * Returns true if the map contains an equivalent insurance as the given argument.
     */
    public boolean containsValue(ReadOnlyInsurance toCheck) {
        requireNonNull(toCheck);
        return internalMap.containsValue(toCheck);
    }

    /**
     * Adds an life insurance to the map.
     *
     * @throws DuplicateInsuranceException if the life insurance to add is a duplicate of an
     * existing life insurance in the list.
     */
    public void put(UUID key, ReadOnlyInsurance toPut) throws DuplicateInsuranceException {
        requireNonNull(toPut);
        if (containsValue(toPut)) {
            throw new DuplicateInsuranceException();
        }
        internalMap.put(key, new LifeInsurance(toPut));
    }

    /**
     * Retrieves an life insurance from the map.
     *
     * @throws InsuranceNotFoundException if the life insurance to add is a duplicate of an
     * existing life insurance in the list.
     */
    public LifeInsurance get(UUID key) throws InsuranceNotFoundException {
        requireNonNull(key);
        if (!containsKey(key)) {
            throw new InsuranceNotFoundException();
        }
        return internalMap.get(key);
    }

    public void forEach(BiConsumer<UUID, LifeInsurance> action) {
        internalMap.forEach(action);
    }

    public void setInsurances(UniqueLifeInsuranceMap replacement) {
        this.internalMap.clear();
        this.internalMap.putAll(replacement.internalMap);
        syncMappedListWithInternalMap();
    }

    public void setInsurances(Map<UUID, ? extends ReadOnlyInsurance> insurances) throws DuplicateInsuranceException {
        final UniqueLifeInsuranceMap replacement = new UniqueLifeInsuranceMap();
        for (final Map.Entry<UUID, ? extends ReadOnlyInsurance> entry : insurances.entrySet()) {
            replacement.put(entry.getKey(), entry.getValue());
        }
        setInsurances(replacement);
    }

    /**
     * Ensures that every insurance in the internalList:
     * contains the same exact same collections of insurances as that of the insuranceMap.
     */
    public void syncMappedListWithInternalMap() {
        this.internalList.clear();
        this.internalList.setAll(this.internalMap.values());
    }
    //author

    //@@author RSJunior37
    /**
     * Accessor to insurance list
     * @return all existing insurances as ReadOnly, ObservableList
     */
    public ObservableList<ReadOnlyInsurance> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalMap.values());
        return FXCollections.unmodifiableObservableList(internalList);
    }
    //@@author

    /**
     * Returns the backing map as an unmodifiable {@code ObservableList}.
     */
    public Map<UUID, ReadOnlyInsurance> asMap() {
        assert CollectionUtil.elementsAreUnique(internalMap.values());
        return Collections.unmodifiableMap(internalMap);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueLifeInsuranceMap// instanceof handles nulls
                && this.internalMap.equals(((UniqueLifeInsuranceMap) other).internalMap));
    }

    @Override
    public int hashCode() {
        return internalMap.hashCode();
    }
}
