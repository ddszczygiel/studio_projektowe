package pl.edu.agh.umldiagrams;


import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import pl.edu.agh.settings.Settings;
import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.exceptions.ConfigException;
import pl.edu.agh.umldiagrams.exceptions.GenerateLogicException;
import pl.edu.agh.umldiagrams.exceptions.ParseException;
import pl.edu.agh.umldiagrams.guiFacade.GuiFacade;
import pl.edu.agh.umldiagrams.model.ActivityDiagram;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;
import pl.edu.agh.umldiagrams.parser.Parser;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart;
import pl.edu.agh.umldiagrams.patterns.PatternsFinder;
import pl.edu.agh.umldiagrams.temporallogic.TempLogicGenerator;

public class Main {


    public static void main(String[] args) {
    	CmdOptions opt = new CmdOptions();
        CmdLineParser optionsParser = new CmdLineParser(opt);
        
        try {
        	optionsParser.parseArgument(args);
        } catch( CmdLineException e ) {
            System.err.println(e.getMessage());
            System.err.println("java -jar uml2ltl.jar [options...] arguments...");
            optionsParser.printUsage(System.err);
            return;
        }
        
        if(opt.isGuiMode()){
        //interaktywnie z GUI	
        	GuiFacade guiFacade = new GuiFacade(opt.isNoRep());
        	guiFacade.startConnection();
        	return;
        }
        else if(opt.getIn() == null || opt.getConf() == null || opt.getOut() == null){
        	System.out.println("\nPlik modelu, plik opisu wzorcow oraz plik wyj�ciowy musza zostac podane aplikacji!\n"); 
        	optionsParser.printUsage(System.out);
        	return;
        }
        String file = opt.getIn().getAbsolutePath();
        String confFile = opt.getConf().getPath();
		Settings.getInstance().setPrintDetails(false);
		Config config = null;
		Parser parser = null;
		try {
			config = new Config("res/conf.xml");
			parser = new Parser(config);
			parser.parseFile(file);
			parser.parseFile(opt.getIn().getAbsolutePath());
			
			for(ActivityDiagram diagram: parser.getParsedDiagramController().getDiagrams()) {
				PatternsFinder patternsFinder = new PatternsFinder();
				ModelDiagramNode node = patternsFinder.convertActivityDiagramToComposite(diagram, config);
				PatternPart patternPart = patternsFinder.getMainPattern();
				TempLogicGenerator tempLogicGen = new TempLogicGenerator(confFile, config);
				tempLogicGen.generateLogic(patternPart);
				if(opt.getOut() != null)
					tempLogicGen.saveToFile(opt.getOut());
				else
					tempLogicGen.saveToFile(""+System.currentTimeMillis());
				if(node != null && patternPart!=null) {
					System.out.println(file + " - Success!");
					System.out.println(patternPart.getPrintString());
					System.out.println(node.getName());
				} else {
					System.out.println(file + " - Fail!");
//					fail("Cannot convert to composite");
				}
			}
		} catch (ParseException e) {
			System.out.println("Error: "+file+"! " + e.getMessage()); 
		} catch (GenerateLogicException e){
			System.out.println("Error: "+e.getMessage()); 
		} catch (ConfigException e) {
			System.out.println("Error: "+e.getMessage()); 
		}

    }
}