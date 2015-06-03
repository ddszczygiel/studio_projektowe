package pl.edu.agh.umldiagrams.guiFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import pl.edu.agh.settings.Settings;
import pl.edu.agh.umldiagrams.config.Config;
import pl.edu.agh.umldiagrams.exceptions.ConfigException;
import pl.edu.agh.umldiagrams.exceptions.GenerateLogicException;
import pl.edu.agh.umldiagrams.exceptions.ParseException;
import pl.edu.agh.umldiagrams.model.ActivityDiagram;
import pl.edu.agh.umldiagrams.model.ModelDiagramNode;
import pl.edu.agh.umldiagrams.parser.Parser;
import pl.edu.agh.umldiagrams.patternmodel.PatternPart;
import pl.edu.agh.umldiagrams.patterns.PatternsFinder;
import pl.edu.agh.umldiagrams.temporallogic.TempLogicGenerator;

public class GuiFacade {
	private static String endConnection = "Closing...";
	private static String confirmConnection = "Connection established.";
	private static String confirmCommand = "Started.";
	private static String parseOK = "Model validated.";
	private static String patternOK = "Pattern found.";
	private static String success = "Success!";
	private static String cmdGenerateLogic = "GenerateLogic";
	private static String splitter = "#";
	private int	port = 5346;
	private ServerSocket listener;
	private Socket socket;
	private boolean noRep;
	private BufferedReader in;
	private PrintWriter out;
	
	public GuiFacade(){
		noRep = false;
	}
	public GuiFacade(boolean noRep){
		this.noRep = noRep;
	}
	public void startConnection() {
        try {
        	
        		
        	listener = new ServerSocket(port);
        	socket = listener.accept();
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out =
                    new PrintWriter(socket.getOutputStream(), true);
                String input;
                while(true){
                	input = in.readLine();
                	if(input != null)
                		break;
                }
              //Nic ciekawiego
                if (input == null || input.equals(".")) {
                	out.println(endConnection);
                    return;
                }
                out.println(confirmConnection);
              //Generacja logiki	
                if(input.contains(cmdGenerateLogic)){
                	generateLogic(input);
                }
                else{//Nieczytelny komunikat
                	out.println(endConnection);
                    return;
                }   
            } catch (IOException e) {
            	out.println("Error: Couldn't handle client.");
            	e.printStackTrace();
            }catch(ConfigException e){
            	out.println("Error: "+e.getMessage());
            	e.printStackTrace();
    		}finally {
                try {
                    socket.close();
                } catch (IOException e) {
                	out.println("Error: Couldn't close a socket.");
                	e.printStackTrace();
                }
            }
        } catch (IOException e) {
        	out.println("Error: Socket error");
        	e.printStackTrace();
        } catch(Exception e){
        	out.println("Error: ???");
        	e.printStackTrace();
        } finally {
        	try{
        		listener.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
    }
	
	private void generateLogic(String cmd) throws ConfigException{	
		String [] cmdArgs = cmd.split(splitter);
		if(cmdArgs.length<4){
			out.println("Error: Wrong command.");
			return;
		}
					
		out.println(confirmCommand);
		String inFile = cmdArgs[1];
		String ltlFile = cmdArgs[2];
		String outFile = cmdArgs[3];
		String configFile = cmdArgs[4];
		Settings.getInstance().setPrintDetails(false);
		Config config = new Config(configFile);
		Parser parser = new Parser(config);
		try {
			parser.parseFile(inFile);
			out.println(parseOK);
			for(ActivityDiagram diagram: parser.getParsedDiagramController().getDiagrams()) {
				PatternsFinder patternsFinder = new PatternsFinder();
				ModelDiagramNode node = patternsFinder.convertActivityDiagramToComposite(diagram, config);
				PatternPart patternPart = patternsFinder.getMainPattern();
				if(node != null && patternPart!=null) {
					out.println(patternOK);
					out.println("#PATTERN: "+patternPart.getPrintString());
				}else{
				//ZROBIC JAKIS WYJATEK WYPISUJACY CO POSZLO NIE TAK, NP GDZIE SIE ZATRZYMALO?
					out.println("Error: This diagram is not valid!");
					return;
				}
				TempLogicGenerator tempLogicGen = new TempLogicGenerator(ltlFile, config);
				tempLogicGen.generateLogic(patternPart);
				tempLogicGen.saveToFile(outFile);
				out.println(success);
				out.println(endConnection);
			}
		} catch (ParseException e) {
			out.println("Error: "+inFile+"! " + e.getMessage());
		} catch (GenerateLogicException e){
			out.println("Error: "+e.getMessage()); 
		}
	}
}
