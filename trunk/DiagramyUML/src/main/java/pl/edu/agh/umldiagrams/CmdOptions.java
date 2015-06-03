package pl.edu.agh.umldiagrams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class CmdOptions {   
	@Option(name="--gui",usage="Aplikacja kontrolowana przez GUI\nTryb interaktywny - nie uzywac w cmd")
	private boolean guiMode;
	
//	@Option(name="--noRep",usage="Usuwa powtarzajace sie formuly")
	private boolean noRep;
	
	@Option(name="-o",usage="Plik wynikowy zawierajacy wygenerowane formuly LTL")
	private File out;

	@Option(name="-i",usage="Plik wejsciowy modelu UML zapisanego w XML")
	private File in;
	
	@Option(name="-c",usage="Plik wejsciowy zawierajacy opis wzorcow w LTL")
	private File conf;
	
	@Argument
	private List<String> arguments = new ArrayList<String>();
	
	public boolean isGuiMode() {
		return guiMode;
	}

	public void setGuiMode(boolean guiMode) {
		this.guiMode = guiMode;
	}

	public boolean isNoRep() {
		return noRep;
	}

	public void setNoRep(boolean noRep) {
		this.noRep = noRep;
	}

	public File getOut() {
		return out;
	}

	public void setOut(File out) {
		this.out = out;
	}

	public File getIn() {
		return in;
	}

	public void setIn(File in) {
		this.in = in;
	}
	
	
	public File getConf() {
		return conf;
	}

	public void setConf(File conf) {
		this.conf = conf;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}
}
