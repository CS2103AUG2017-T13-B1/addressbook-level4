package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author RSJunior37
/**
 * Indicates a request to jump to the list of insurances
 */
public class JumpToInsuranceRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToInsuranceRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
