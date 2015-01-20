package org.mskcc.cbio.bigmech;

import org.apache.commons.cli.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.Model;
import org.mskcc.cbio.bigmech.converter.BioPAX2BELConverter;
import org.mskcc.cbio.bigmech.util.BELHeaderWrapper;
import org.openbel.bel.model.BELDocument;
import org.openbel.framework.common.bel.converters.BELDocumentConverter;
import org.openbel.framework.common.belscript.BELScriptExporter;
import org.openbel.framework.common.model.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BioPAX2BELMain {
    private static Log log = LogFactory.getLog(BioPAX2BELMain.class);
    private static final String helpText = BioPAX2BELMain.class.getSimpleName();

    public static void main(String[] args) {
        final CommandLineParser clParser = new GnuParser();
        Options gnuOptions = new Options();
        gnuOptions
            .addOption("i", "input", true, "BioPAX file [required]")
            .addOption("o", "output", true, "Output file (BEL file) [required]")
            .addOption("ha", "header-author", true, "BEL Header: Author name (default: null)")
            .addOption("hd", "header-description", true, "BEL Header: Description (default: 'Converted from BioPAX on `date`)")
            .addOption("hn", "header-name", true, "BEL Header: Name (default: 'Document #UUID'")
            .addOption("hv", "header-version", true, "BEL Header: Version (default: 1.0)")
        ;

        try {
            CommandLine commandLine = clParser.parse(gnuOptions, args);

            if(commandLine.hasOption("i") && commandLine.hasOption("o")) {
                String inputFile = commandLine.getOptionValue("i");
                String outputFile = commandLine.getOptionValue("o");

                log.info("Reading in the BioPAX file: " + inputFile);
                // Read the BioPAX file in
                SimpleIOHandler simpleIOHandler = new SimpleIOHandler();
                Model bpModel = simpleIOHandler.convertFromOWL(new FileInputStream(inputFile));

                log.info("Converting the BioPAX model into BEL...");
                // Convert the BioPAX file into a BEL model
                BioPAX2BELConverter converter = new BioPAX2BELConverter();
                BELHeaderWrapper belHeaderWrapper = converter.getBelHeaderWrapper();

                // update header if we have information
                if(commandLine.hasOption("ha")) {
                    belHeaderWrapper.setAuthors(commandLine.getOptionValue("ha"));
                }
                if(commandLine.hasOption("hd")) {
                    belHeaderWrapper.setDescription(commandLine.getOptionValue("hd"));
                }
                if(commandLine.hasOption("hn")) {
                    belHeaderWrapper.setName(commandLine.getOptionValue("hn"));
                }
                if(commandLine.hasOption("hv")) {
                    belHeaderWrapper.setVersion(commandLine.getOptionValue("hv"));
                }
                // Done setting the header values

                // Now the do the coversion
                BELDocument belDocument = converter.convert(bpModel);

                log.info("Conversion done, now writing the BEL model into a file:" + outputFile);
                BELScriptExporter belScriptExporter = new BELScriptExporter();
                FileOutputStream os = new FileOutputStream(outputFile);
                BELDocumentConverter belDocumentConverter = new BELDocumentConverter();
                Document convertedDoc = belDocumentConverter.convert(belDocument);
                belScriptExporter.export(convertedDoc, os);
                os.close();
                System.exit(0);
            } else {
                HelpFormatter helpFormatter = new HelpFormatter();
                helpFormatter.printHelp(helpText, gnuOptions);
                System.exit(-1);
            }

        } catch (ParseException e) {
            System.err.println(e.getMessage());
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(helpText, gnuOptions);
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
