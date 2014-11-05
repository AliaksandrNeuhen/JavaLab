package com.epam.xmltransforming.command;

import java.util.HashMap;
import java.util.Map;

public class CommandHelper {
	private static final CommandHelper instance = new CommandHelper();
	
	private Map<CommandName, ICommand> commands = new HashMap<CommandName, ICommand>();
	
	private CommandHelper(){
		commands.put(CommandName.NO_SUCH_COMMAND, new ErrorCommand());
		commands.put(CommandName.SHOW_CONTENT, new ShowContentCommand());
		commands.put(CommandName.SHOW_CATEGORIES, new ShowCategoriesCommand());
	}
	
	public static CommandHelper getInstance(){
		return instance;
	}
	
	public ICommand getCommand(String commandName){
		CommandName name = CommandName.valueOf(commandName.toUpperCase());
		ICommand command = null;
		
		try {
			if (null != name) {
				command = commands.get(name);
			} else {
				command = commands.get(CommandName.NO_SUCH_COMMAND);
			}
		} catch (IllegalArgumentException e) {
			command = commands.get(CommandName.NO_SUCH_COMMAND);
		}	
		System.out.println(command.toString());
		return command;
	}
}
