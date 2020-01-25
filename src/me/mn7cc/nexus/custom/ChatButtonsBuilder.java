package me.mn7cc.nexus.custom;

import java.util.ArrayList;
import java.util.List;

import me.mn7cc.nexus.util.TextUtils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ChatButtonsBuilder {
	
	private List<TextComponent> chatButtons;
	
	public ChatButtonsBuilder() {
		chatButtons = new ArrayList<TextComponent>();
	}
	
	public ChatButtonsBuilder addChatCommandButton(String text, String command, String hover) {
		
		TextComponent chatButton = new TextComponent(TextUtils.color(text));
		chatButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
		chatButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtils.color(hover)).create()));
		chatButtons.add(chatButton);
		return this;
		
	}
	
	public ChatButtonsBuilder addChatURLButton(String text, String url, String hover) {
		
		TextComponent chatButton = new TextComponent(TextUtils.color(text));
		chatButton.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
		chatButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtils.color(hover)).create()));
		chatButtons.add(chatButton);
		return this;
		
	}
	
	public TextComponent getChatButtons(String spacer) {
		
		TextComponent result = new TextComponent();

		int c = 1;
		for(TextComponent chatButton : chatButtons) {
			result.addExtra(chatButton);
			if(c < chatButtons.size()) result.addExtra(new TextComponent(TextUtils.color(spacer)));
			c++;
		}
		
		return result;
		
	}
	
}
