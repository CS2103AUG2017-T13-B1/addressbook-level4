package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.UniqueLifeInsuranceList;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;

public class PrintCommand extends Command {

    public static final String[] COMMAND_WORDS = {"print"};
    public static final String COMMAND_WORD = "print";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves the addressbook into a .txt file named by you for your viewing.\n"
            + "Example: " + COMMAND_WORD + "addressbook";

    public static final String MESSAGE_SUCCESS = "Addressbook has been saved! " +
            "Find your addressbook in the .txt file named by you in the doc/books folder.";

    //private final String fileName = "lol.txt";
    private final String fileName;


    public PrintCommand(String filename) {
        requireNonNull(filename);

        this.fileName = filename;
    }


    @Override
    public CommandResult execute() {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        List<String> lines = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("dd/MM/YYYY" + " "+ "HH:mm:ss").format(new Date());
        lines.add("Addressbook was last updated on: " + timeStamp +"\n");

        int personIndex = 1;
        for (ReadOnlyPerson person: lastShownList) {
            String entry = personIndex + ". " + person.getAsText();
            lines.add(entry);

            UniqueLifeInsuranceList insurances = person.getLifeInsurances();
            for (ReadOnlyInsurance insurance: insurances) {
                lines.add("Insurance Policy: =========");
                String owner = insurance.getOwner().getName();
                String insured = insurance.getInsured().getName();
                String beneficiary = insurance.getBeneficiary().getName();
                String premium = insurance.getPremium().toString();
                String signingDate = insurance.getSigningDate();
                String expiryDate = insurance.getExpiryDate();
                lines.add("Owner: " + owner + "\n"
                        + "Insured: " + insured + "\n"
                        + "Beneficiary: " + beneficiary + "\n"
                        + "Premium: " + premium + "\n"
                        + "Signing Date: " + signingDate + "\n"
                        + "Expiry Date: " + expiryDate + "\n"
                );
                lines.add("============");
            }
            personIndex++;
        }

        Path file = Paths.get("docs/books/"+ fileName +".txt");
        //Path file = Paths.get("docs/books/lol.txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println("test");
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
