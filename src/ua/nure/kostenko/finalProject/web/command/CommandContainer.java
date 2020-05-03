package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

/**
 * Holder for all commands.<br/>
 * 
 * @author A.Kostenko
 * 
 */
public class CommandContainer {
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("mainPage", new MainPageCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("updateSettings", new UpdateSettingsCommand());
		commands.put("noCommand", new NoCommand());


		// admin commands
		commands.put("changeStatus", new ChangeStatusCommand());
		commands.put("addUser", new AddUserCommand());
		commands.put("beginEditUser", new BeginEditUserCommand());
		commands.put("editUser", new EditUserCommand());
		commands.put("findUser", new FindUserCommand());
		commands.put("editUserCounts", new EditUserCountsCommand());
		commands.put("editUserCreditCard", new EditUserCreditCardCommand());

		// client commands
		commands.put("registration", new RegistrationCommand());
		commands.put("editPayments", new EditPaymentsCommand());
		commands.put("listCounts", new EditCountsByClientCommand());

		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}

	/**
	 * Returns command object with the given name.
	 * 
	 * @param commandName
	 *            Name of the command.
	 * @return Command object.
	 */
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}