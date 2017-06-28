package cn.jiongjionger.neverlag.controler;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.EventExecutor;

import cn.jiongjionger.neverlag.NeverLag;
import cn.jiongjionger.neverlag.config.ConfigManager;
import cn.jiongjionger.neverlag.utils.Reflection;
import java.util.Collection;

public class ExplodeDisabler implements Listener {

	private final ConfigManager cm = ConfigManager.getInstance();
	final EventExecutor executor = new EventExecutor() {
		@Override
		public void execute(Listener listener, Event event) throws EventException {
			if (cm.isDisableExplode()) {
				((Collection<?>) Reflection.getMethod(event.getClass(), "blockList")
						.invoke(event)).clear();
			}
		}
	};

	@SuppressWarnings("unchecked")
	public ExplodeDisabler() {
		registerEvent(EntityExplodeEvent.class);
		Class<? extends Event> clazz;
		try {
			clazz = (Class<? extends Event>) Class.forName("org.bukkit.event.block.BlockExplodeEvent");
			registerEvent(clazz);
		} catch (ClassNotFoundException ex) {
			// ignore
		}
	}

	private void registerEvent(Class<? extends Event> clazz) {
		Bukkit.getPluginManager().registerEvent(clazz, this, EventPriority.NORMAL,
				executor, NeverLag.getInstance(), true);
	}
}