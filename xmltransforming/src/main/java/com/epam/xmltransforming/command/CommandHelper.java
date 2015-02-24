package com.epam.xmltransforming.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for getting access to existing commands by it's name.
 * 
 */

public final class CommandHelper {
	private static final CommandHelper instance = new CommandHelper();
	
	// Map containing existing commands
	private Map<CommandName, ICommand> commands = new HashMap<CommandName, ICommand>();
	
	private CommandHelper(){
		commands.put(CommandName.NO_SUCH_COMMAND, new ErrorCommand());
		commands.put(CommandName.SHOW_CATEGORIES, new ShowCategoriesCommand());
		commands.put(CommandName.SHOW_SUBCATEGORIES, new ShowSubcategoriesCommand());
		commands.put(CommandName.SHOW_PRODUCTS, new ShowProductsCommand());
		commands.put(CommandName.BACK, new BackCommand());
		commands.put(CommandName.ADD_PRODUCT, new AddProductCommand());
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
			if (command == null) {
				command = commands.get(CommandName.NO_SUCH_COMMAND);
			}
		} catch (IllegalArgumentException e) {
			command = commands.get(CommandName.NO_SUCH_COMMAND);
		}	
		return command;
	}
}
