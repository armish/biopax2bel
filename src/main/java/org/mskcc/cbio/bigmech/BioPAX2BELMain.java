package org.mskcc.cbio.bigmech;

import org.apache.commons.cli.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biopax.paxtools.io.SimpleIOHandler;
import org.biopax.paxtools.model.Model;
import org.mskcc.cbio.bigmech.converter.BioPAX2BELConverter;
import org.openbel.bel.model.BELDocument;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BioPAX2BELMain {
    private static Log log = LogFactory.getLog(BioPAX2BELMain.class);
    private static final String helpText = BioPAX2BELMain.class.getSimpleName();

    public static void main(String[] args) {
        final CommandLineParser clParser = new GnuParser();
        Options gnuOptions = new Options();
        gnuOptions
                .addOption("i", "input", true, "BioPAX file [required]")
                .addOption("o", "output", true, "Output file (BEL file) [required]")
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
                BELDocument belDocument = converter.convert(bpModel);

                log.info("Conversion done, now writing the BEL model into a file:" + outputFile);
                // TODO: Serialize the model into the file
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
        }
    }
}
