package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * View settings command.
 * 
 * @author A.Kostenko
 * 
 */
public class ViewSettingsCommand extends Command {
	
	private static final long serialVersionUID = -5041536593627692473L;
	
	private static final Logger LOG = Logger.getLogger(ViewSettingsCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {		
		LOG.debug("Command starts");

		LOG.debug("Command finished");
		return Path.PAGE_SETTINGS;
	}

}