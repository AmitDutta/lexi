package command;

import java.util.*;

public class CommandManager {

	private volatile static CommandManager instance;
	private List<ICommand> commands = new ArrayList<ICommand>();
	private int current = -1;
	
	private CommandManager(){}
	
	public static CommandManager getInstance(){
		if (instance == null){
			synchronized (CommandManager.class) {
				if (instance == null){
					instance = new CommandManager();
				}
			}
		}
		
		return instance;
	}
	
	public Boolean execute(ICommand cmd){
		Boolean val = cmd.execute() && cmd.canUndo();
		if (val){
			this.commands.add(cmd);
			this.current++;
		}
		
		return val;
	}
	
	public void undo(){
		if (this.canUndo()){
			this.commands.get(current).unExecute();
			current--;
		}
	}
	
	public void redo(){
		if (this.canRedo()){
			current++;
			this.commands.get(current).execute();
		}
	}
	
	public Boolean canUndo(){
		return this.current > -1;
	}
	
	public Boolean canRedo(){
		return this.current < (this.commands.size() - 1);
	}
}
