/*
 * O R A N G E   O B J E C T S
 *
 * copyright by Orange Objects
 * http://www.OrangeObjects.de
 *
 */

package com.orangeobjects.mavenizer.util;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Michael Hofmann <Michael.Hofmann@OrangeObjects.de>
 * 
 */
public class CommandLineArgs {

    static final Logger LOGGER = Logger.getLogger(CommandLineArgs.class.getName());

    private final String[] args;
    private final static Options OPTIONS = new Options();
    
    static {
        OPTIONS.addOption("h", "help", false, "show help");
        OPTIONS.addOption("c", "config", true, "path to configuration file");
    }

    /*  ***********************************************************************
     *  C o n s t r u c t o r
     **************************************************************************/
    
    public CommandLineArgs(String[] args) {
        this.args = args;
    }

    /*  ***********************************************************************
     *  G e t t e r  und  S e t t e r
     **************************************************************************/
    
    public void parse() {
        
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(OPTIONS, args);
            if (cmd.hasOption("h")) {
                help();
            }
            if (cmd.hasOption("c")) {
                String configFile = cmd.getOptionValue("c");
                if (StringUtils.isBlank(configFile)) {
                    help();
                } else {
                    LOGGER.log(Level.INFO, "using cli argument -c={0}", configFile);
                    System.setProperty(ApplicationConfig.PATH_TO_CONFIG, 
                            cmd.getOptionValue("c"));
                }
            } else {
                LOGGER.log(Level.SEVERE, "missing -c option");
                help();
            }
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, "Failed to parse comand line properties", e);
            help();
        }
    }

    private void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", OPTIONS);
    }
}
