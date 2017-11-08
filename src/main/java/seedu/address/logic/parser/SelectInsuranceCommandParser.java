package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.SelectInsuranceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

//@@author RSJunior37
/**
 * Parses input arguments and creates a new SelectInsuranceCommand object
 */
public class SelectInsuranceCommandParser implements Parser<SelectInsuranceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectInsuranceCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new SelectInsuranceCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectInsuranceCommand.MESSAGE_USAGE));
        }
    }
}
