package edu.anadolu;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;

public interface Params {

    @Option(description = "The number of entities to display", shortName = "n", longName = "number", defaultValue = "10")
    int getNumber();

    @Option(description = "The name of entity", shortName = {"e"}, longName = {"entity"}, defaultValue = "hashtag")
    String searchFor();

    @Option(description = "Reverse the comparison", shortName = "r", longName = "reverse")
    boolean getReverse();


    @Option(description = "Fold upper case to lower case characters(collate #AnadoluÜniversitesi and #anadoluÜniversitesi)", shortName = "i", longName = "ignore-case")
    boolean getIgnoreCase();

    @Option(helpRequest = true, description = "display help", shortName = "h")
    boolean getHelp();

    @Unparsed
    String getFile();

}

